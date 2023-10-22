package me.skinnynoonie.characterwar.character;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface Character {

    @NotNull String getReferenceName();

    @NotNull String getDisplayName();

    @NotNull String getDescription();

    @NotNull CharacterVerse getVerse();

    @NotNull ItemStack[] getArmor();

    @NotNull ItemStack[] getCustomItems();

}
