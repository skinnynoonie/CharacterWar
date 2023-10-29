package me.skinnynoonie.characterwar;

import me.skinnynoonie.characterwar.character.CharacterManager;
import me.skinnynoonie.characterwar.character.impls.SupermanCharacter;
import me.skinnynoonie.characterwar.commands.GiveCharacterCMD;
import me.skinnynoonie.characterwar.item.CharacterPDCKey;
import me.skinnynoonie.characterwar.item.CustomItemManager;
import me.skinnynoonie.characterwar.listeners.PlayerConnectionListener;
import me.skinnynoonie.characterwar.util.Commands;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class CharacterWar extends JavaPlugin {

    private static CharacterWar INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        CharacterPDCKey.setKey(new NamespacedKey(this, "custom_item"));
        NamespacedKey key = CharacterPDCKey.getKey();

        CharacterManager characterManager = new CharacterManager(this, new CustomItemManager(this, key));
        characterManager.registerCharacter(new SupermanCharacter());

        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), this);

        Commands.register(this, "givecharacter", new GiveCharacterCMD(characterManager));
    }

    @Override
    public void onDisable() {
    }

    public static CharacterWar getInstance() {
        return INSTANCE;
    }

}
