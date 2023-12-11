package me.skinnynoonie.characterwar.command;

import me.skinnynoonie.characterwar.character.CustomCharacter;
import me.skinnynoonie.characterwar.item.armor.ArmorEquipment;
import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.repository.CustomCharacterRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class GiveCharacterCommand implements CommandExecutor {

    private final CustomCharacterRepository customCharacterRepository;

    public GiveCharacterCommand(@NotNull CustomCharacterRepository customCharacterRepository) {
        Objects.requireNonNull(customCharacterRepository, "Parameter customCharacterRepository is null.");
        this.customCharacterRepository = customCharacterRepository;
    }

    // just a test cmd for now nothing special
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }
        if (args.length == 0) {
            return true;
        }
        CustomCharacter customCharacter = customCharacterRepository.fromReferenceName(args[0]);
        if (customCharacter == null) {
            return true;
        }

        player.getInventory().addItem(
                customCharacter.getItems()
                        .stream()
                        .map(CustomItem::getItem)
                        .toArray(ItemStack[]::new)
        );
        ArmorEquipment<CustomItem> customArmor = customCharacter.getArmor();
        player.getInventory().addItem(
                customArmor.getHelmet().getItem(),
                customArmor.getChestplate().getItem(),
                customArmor.getLeggings().getItem(),
                customArmor.getBoots().getItem()
        );

        return true;
    }

}
