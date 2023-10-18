package me.skinnynoonie.characterwar.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArmorSetBuilder {

    private final ItemStack[] armorSet = {
            new ItemStack(Material.AIR),
            new ItemStack(Material.AIR),
            new ItemStack(Material.AIR),
            new ItemStack(Material.AIR)
    };

    public ArmorSetBuilder setHelmet(ItemStack helmet) {
        armorSet[3] = helmet;
        return this;
    }

    public ArmorSetBuilder setChestplate(ItemStack chestplate) {
        armorSet[2] = chestplate;
        return this;
    }

    public ArmorSetBuilder setLeggings(ItemStack leggings) {
        armorSet[1] = leggings;
        return this;
    }

    public ArmorSetBuilder setBoots(ItemStack boots) {
        armorSet[0] = boots;
        return this;
    }

    public ItemStack[] build() {
        return armorSet;
    }

}
