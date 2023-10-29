package me.skinnynoonie.characterwar.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface CustomItemImpl extends ItemListenerAdapter {

    @NotNull ItemStack getItem();

}
