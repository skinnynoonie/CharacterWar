package me.skinnynoonie.characterwar.core.config;

import org.bukkit.inventory.ItemStack;

public abstract class CustomItemConfig {

    protected final ItemStack item;

    protected CustomItemConfig(ItemStack item) {
        this.item = item;
    }

    public boolean isValid() {
        return this.item != null &&
                !this.item.getType().isAir();
    }

    public ItemStack getItem() {
        return this.item;
    }

}
