package fr.tartur.torchlines.commands;

import fr.tartur.torchlines.TorchLines;
import fr.tartur.torchlines.commands.common.TorchStage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.*;

public class TorchLoad implements CommandExecutor {

    private final File schemeFile;
    private final TorchSet set;

    public TorchLoad(File schemeFile, TorchSet set) {
        this.schemeFile = schemeFile;
        this.set = set;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0) {
            TorchLines.logger.error("Syntaxe de la commande invalide.\n§aUsage correct: §e/torchload");
            return true;
        }

        if (!schemeFile.exists()) {
            TorchLines.logger.error(sender, "Le fichier supposé contenir le schéma de placement des torches est inexistant !\n" +
                    "Vérifiez la console ou les logs pour voir la cause de l'absence du fichier.");
            return true;
        }

        TorchStage[] stages = new TorchStage[4];

        try (BufferedReader reader = new BufferedReader(new FileReader(this.schemeFile))) {
            String line = reader.readLine();

            for (int i = 0; i < 4; i++) {
                TorchStage stage = new TorchStage();

                for (int j = 0; j < 64; j++, line = reader.readLine()) {
                    if (line == null) {
                        TorchLines.logger.error(sender, "Le fichier de schéma n'est pas correctement rempli. Des lignes sont manquantes.");
                        return false;
                    }

                    if (!stage.newLine(line)) {
                        TorchLines.logger.error(sender, "Une erreur est survenue lors de la lecture du fichier. " +
                                "Veillez à ce qu'il ne contienne que des 0 et des 1 et aucun autre caractère spécial, " +
                                "et que chaque ligne fasse 38 caractères (sans compter le retour à la ligne).");
                        return false;
                    }
                }

                if (!stage.isFull()) {
                    TorchLines.logger.error(sender, "L'étage n'a pas été entièrement rempli, des lignes sont manquantes.");
                    return false;
                }

                stages[i] = stage;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.set.setStages(stages);
        TorchLines.logger.log(sender, "Le fichier a été chargé avec succès !");

        return true;
    }
}
