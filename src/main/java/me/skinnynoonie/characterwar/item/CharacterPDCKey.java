package me.skinnynoonie.characterwar.item;

import org.bukkit.NamespacedKey;

public class CharacterPDCKey {

    private static NamespacedKey key;

    public static void setKey(NamespacedKey key) {
        if (CharacterPDCKey.key != null) {
            throw new IllegalStateException("The Character PDC Key has already been set.");
        }
        CharacterPDCKey.key = key;
    }

    public static NamespacedKey getKey() {
        return key;
    }

}
