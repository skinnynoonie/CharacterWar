package me.skinnynoonie.characterwar.item;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public final class CustomItemKey {

    private static NamespacedKey key;

    public static void setKey(@NotNull NamespacedKey key) {
        if (CustomItemKey.key != null) {
            throw new IllegalStateException("Cannot set the key when the key is already set.");
        }
        CustomItemKey.key = key;
    }

    @NotNull
    public static NamespacedKey key() {
        if (CustomItemKey.key == null) {
            throw new IllegalStateException("The key has not been set yet.");
        }
        return CustomItemKey.key;
    }

    private CustomItemKey() {
    }

}
