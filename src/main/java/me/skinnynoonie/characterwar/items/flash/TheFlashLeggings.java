package me.skinnynoonie.characterwar.items.flash;

import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.item.CustomItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TheFlashLeggings implements CustomItem {
    @Override
    public @NotNull ItemStack getItem() {
        return new CustomItemBuilder(Material.LEATHER_LEGGINGS, "the_flash_leggings")
                .setColor(Color.RED)
                .setName("<red>The Flash's Leggings")
                .build();
    }
}
