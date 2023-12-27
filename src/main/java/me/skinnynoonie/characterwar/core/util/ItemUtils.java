package me.skinnynoonie.characterwar.core.util;

import me.skinnynoonie.characterwar.core.config.CustomItemConfig;
import me.skinnynoonie.characterwar.core.item.ItemBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class ItemUtils {

    public static @Nullable String getStringFromPDC(@Nullable ItemStack item, @NotNull NamespacedKey namespacedKey) {
        Objects.requireNonNull(namespacedKey, "Parameter namespacedKey is null.");

        if (item == null) {
            return null;
        }

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return null;
        }

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.get(namespacedKey, PersistentDataType.STRING);
    }

    public static ItemStack buildFromConfig(CustomItemConfig config) {
        return new ItemBuilder(config.getMaterial())
                .setName(config.getName())
                .setLore(config.getLore().toArray(String[]::new))
                .setUnbreakable(config.isUnbreakable())
                .build();

    }

    private ItemUtils() {
        throw new UnsupportedOperationException("Cannot instantiate utility class.");
    }


}
