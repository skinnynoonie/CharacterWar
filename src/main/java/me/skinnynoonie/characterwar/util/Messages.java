package me.skinnynoonie.characterwar.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Messages {

    public static Component text(String text) {
        // I fricking hate how long this method is
        return MiniMessage.miniMessage().deserialize(text);
    }

}
