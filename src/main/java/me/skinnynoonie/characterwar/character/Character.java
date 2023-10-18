package me.skinnynoonie.characterwar.character;

import org.bukkit.inventory.ItemStack;

public interface Character {

    String getReferenceName();

    String getDisplayName();

    String getDescription();

    CharacterVerse getVerse();

    ItemStack[] getArmor();

    ItemStack[] getCustomItems();

}
