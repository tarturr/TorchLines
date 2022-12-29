package fr.tartur.torchlines.commands;

import fr.tartur.torchlines.TorchLines;
import fr.tartur.torchlines.commands.common.HeadDirection;
import fr.tartur.torchlines.commands.common.TorchStage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class TorchSet implements CommandExecutor {

    private final FileConfiguration config;
    private TorchStage[] stages;

    public TorchSet(FileConfiguration config) {
        this.config = config;
        this.stages = new TorchStage[4];
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            TorchLines.logger.error("La console ne peut pas effectuer cette commande.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 0) {
            TorchLines.logger.error(player, "Syntaxe de la commande invalide.\n§aUsage correct: §e/torchset");
            return true;
        }

        Location location = player.getLocation();
        HeadDirection direction = HeadDirection.getCorrespondingDirection((int) location.getYaw());

        Location start = new Location(player.getWorld(),
                location.getBlockX() + direction.getX(),
                location.getBlockY() - 1,
                location.getBlockZ() + direction.getZ()
        );

        if (start.getBlock().getType() == Material.AIR) {
            TorchLines.logger.error(player, "Vous ne pouvez pas vous placer dans le vide pour exécuter cette commande !");
            return true;
        }

        int xSize = this.config.getInt("torch_spacing.x_size");
        int ySize = this.config.getInt("torch_spacing.y_size");
        int zSize = this.config.getInt("torch_spacing.z_size");

        for (int i = 0; i < 4; i++) {
            TorchLines.logger.log("Round n°" + i + ": FIGHT!");
            int[][] content = this.stages[i].getContent();

            for (int j = 0; j < 64; j++) {
                for (int k = 0; k < 38; k++) {
                    if (content[j][k] == 1) {
                        System.out.println("Put at X: " + start.getX() + " Y: " + start.getY() + " Z: " + start.getZ());
                        start.getBlock().setType(Material.REDSTONE_TORCH);
                    }
                    start.setX(start.getX() + xSize);
                }
                start.setZ(start.getZ() + zSize);
            }
            start.setY(start.getY() + ySize);
        }

        TorchLines.logger.log(player, "Torches placées !");
        return true;
    }

    public void setStages(TorchStage[] stages) {
        this.stages = stages;
    }

}
