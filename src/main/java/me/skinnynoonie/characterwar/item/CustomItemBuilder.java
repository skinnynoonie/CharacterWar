package me.skinnynoonie.characterwar.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class CustomItemBuilder extends ItemBuilder {

    public CustomItemBuilder(@NotNull ItemStack itemToBuild, String key) {
        super(itemToBuild);
        super.setUnbreakable(true);
        super.setPDCValue(CustomItemKey.key(), PersistentDataType.STRING, key);
    }

    public CustomItemBuilder(@NotNull Material itemToBuild, String key) {
        this(new ItemStack(itemToBuild), key);
    }
}
