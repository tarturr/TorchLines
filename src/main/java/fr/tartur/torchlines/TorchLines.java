package fr.tartur.torchlines;

import fr.tartur.torchlines.commands.TorchLoad;
import fr.tartur.torchlines.commands.TorchSet;
import fr.tartur.torchlines.commands.TorchSpace;
import fr.tartur.torchlines.lib.console.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class TorchLines extends JavaPlugin {

    public static final PluginLogger logger = new PluginLogger("TorchLines");

    private final File schemeFile;

    public TorchLines() {
        this.schemeFile = new File(getDataFolder(), "schema.txt");
    }

    @Override
    public void onEnable() {
        createSchemeFile();
        saveDefaultConfig();

        TorchSet set = new TorchSet(getConfig());
        getCommand("torchset").setExecutor(set);
        getCommand("torchspace").setExecutor(new TorchSpace(this));
        getCommand("torchload").setExecutor(new TorchLoad(this.schemeFile, set));

        logger.log("Hello JAZ2231 ! Amuse-toi bien pendant que tu placeras des torches ! :)");
    }

    @Override
    public void onDisable() {
        logger.log("Bye JAZ2231 ! On se reverra !");
    }

    private void createSchemeFile() {
        if (!this.schemeFile.exists()) {
            try {
                if (schemeFile.createNewFile()) {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(schemeFile));
                    writer.write("C'est ici que doit être stocké ton schéma en 64x64, JAZ2231 !");
                    writer.close();

                    logger.log("Le fichier \"schema.txt\" a été créé avec succès !");
                } else {
                    logger.error("La création du fichier texte \"schema.txt\" n'a pas pu être réalisée par manque de droits.");
                }
            } catch (IOException e) {
                logger.error("La création du fichier texte \"schema.txt\" n'a pas pu être réalisée.\n" +
                        "Pour plus d'informations: " + e.getMessage());
            }
        }
    }
}
