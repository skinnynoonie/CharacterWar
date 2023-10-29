package me.skinnynoonie.characterwar.util;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class Commands {

    public static void register(JavaPlugin plugin, String commandName, CommandExecutor executor) {
        plugin.getCommand(commandName).setExecutor(executor);
        if (executor instanceof TabCompleter tabCompleter) {
            plugin.getCommand(commandName).setTabCompleter(tabCompleter);
        }
    }

}
