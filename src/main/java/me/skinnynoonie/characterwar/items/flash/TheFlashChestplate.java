package me.skinnynoonie.characterwar.items.flash;

import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.item.CustomItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TheFlashChestplate implements CustomItem {
    @Override
    public @NotNull ItemStack getItem() {
        return new CustomItemBuilder(Material.LEATHER_CHESTPLATE, "the_flash_chestplate")
                .setColor(Color.RED)
                .setName("<red>The Flash's ChestPlate")
                .build();
    }
}
