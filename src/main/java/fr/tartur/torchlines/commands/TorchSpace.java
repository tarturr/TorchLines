package fr.tartur.torchlines.commands;

import fr.tartur.torchlines.TorchLines;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TorchSpace implements CommandExecutor {

    private final TorchLines main;

    public TorchSpace(TorchLines main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            TorchLines.logger.error("The console cannot execute this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 2) {
            if (args.length != 0) {
                TorchLines.logger.error(player, "Syntaxe de la commande invalide.");
            }

            player.sendMessage("§aUsage correct: §e/torchspace <x_size/y_size/z_size> <value>");
            return true;
        }

        if (args[0].equalsIgnoreCase("x_size") || args[0].equalsIgnoreCase("y_size") || args[0].equalsIgnoreCase("z_size")) {
            try {
                int size = Integer.parseInt(args[1]);
                this.main.getConfig().set("torch_spacing." + args[0].toLowerCase(), size);
                this.main.saveConfig();

                TorchLines.logger.log(player, "La nouvelle valeur de §b" + args[0].toLowerCase() + "§a a été sauvegardée avec succès !");
            } catch (NumberFormatException exception) {
                TorchLines.logger.error(player, "L'argument \"" + args[1] + "\" n'est pas un nombre.");
            }
        } else {
            TorchLines.logger.error(player, "Paramètre inconnu. Les paramètres existants sont \"x_size\", \"y_size\" et \"z_size\" " +
                    "(paramètres dont le rôle est expliqué dans le fichier de configuration du plugin si besoin).");
        }

        return true;
    }
}
