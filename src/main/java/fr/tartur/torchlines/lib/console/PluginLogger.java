package fr.tartur.torchlines.lib.console;

import org.bukkit.command.CommandSender;

public class PluginLogger {

    private final String name;

    public PluginLogger(String name) {
        this.name = name;
    }

    public void say(CommandSender sender, String message) {
        sender.sendMessage("§6[§e" + this.name + "§6] §f" + message);
    }
    
    public void log(String message) {
        System.out.println("[" + this.name + "] " + message);
    }

    public void log(CommandSender sender, String message) {
        sender.sendMessage("§e[§2" + this.name + "§e] §a" + message);
    }

    public void warn(String message) {
        System.err.println("[" + this.name + "] " + message);
    }

    public void warn(CommandSender sender, String message) {
        sender.sendMessage("§e[§c" + this.name + "§e] §c" + message);
    }
    
    public void error(String message) {
        System.err.println("[" + this.name + "] Erreur: " + message);
    }

    public void error(CommandSender sender, String message) {
        sender.sendMessage("§e[§4" + this.name + "§e] §cErreur: " + message);
    }
    
}
