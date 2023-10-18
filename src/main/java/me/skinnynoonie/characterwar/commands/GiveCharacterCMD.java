package me.skinnynoonie.characterwar.commands;

import me.skinnynoonie.characterwar.character.Character;
import me.skinnynoonie.characterwar.character.CharacterManager;
import me.skinnynoonie.characterwar.constants.MessageConstants;
import me.skinnynoonie.characterwar.util.Messages;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GiveCharacterCMD implements CommandExecutor, TabCompleter {

    private final static Component USAGE = Messages.text("<gray>/givecharacter <character> [player]");
    private final static Component CHARACTER_DOES_NOT_EXIST = Messages.text("<red>This character does not exist!");
    private final static Component SUCCESSFULLY_RECEIVED = Messages.text("<green>You have successfully received the kit.");

    private final CharacterManager characterManager;

    public GiveCharacterCMD(CharacterManager characterManager) {
        this.characterManager = characterManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MessageConstants.ONLY_PLAYERS_CAN_EXECUTE_CMD);
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(USAGE);
            return true;
        }
        if (!characterManager.getReferenceNames().contains(args[0])) {
            sender.sendMessage(CHARACTER_DOES_NOT_EXIST);
            return true;
        }

        Character character = characterManager.getCharacterByReferenceName(args[0]);
        player.getInventory().addItem(character.getCustomItems());
        player.getInventory().addItem(character.getArmor());

        player.sendMessage(SUCCESSFULLY_RECEIVED);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 1) {
            return null;
        }
        return StringUtil.copyPartialMatches(args[0], characterManager.getReferenceNames(), new ArrayList<>());
    }

}
