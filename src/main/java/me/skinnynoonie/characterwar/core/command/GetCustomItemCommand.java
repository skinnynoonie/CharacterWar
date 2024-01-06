package me.skinnynoonie.characterwar.core.command;

import me.skinnynoonie.characterwar.core.CharacterWarCorePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GetCustomItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CharacterWarCorePlugin.getInstance().getCustomItemManager().getCustomItem(args[0]).ifPresent(
                item -> {
                    if (sender instanceof Player p)
                        p.getInventory().addItem(item);
                }
        );
        return true;
    }

}
