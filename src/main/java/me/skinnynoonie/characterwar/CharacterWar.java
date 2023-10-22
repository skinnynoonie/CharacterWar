package me.skinnynoonie.characterwar;

import me.skinnynoonie.characterwar.character.CharacterManager;
import me.skinnynoonie.characterwar.character.impls.BatmanCharacter;
import me.skinnynoonie.characterwar.character.impls.SupermanCharacter;
import me.skinnynoonie.characterwar.commands.GiveCharacterCMD;
import me.skinnynoonie.characterwar.listeners.PlayerConnectionListener;
import me.skinnynoonie.characterwar.util.Commands;
import org.bukkit.plugin.java.JavaPlugin;

public final class CharacterWar extends JavaPlugin {

    @Override
    public void onEnable() {
        CharacterManager characterManager = new CharacterManager(this);
        characterManager.registerCharacter(new SupermanCharacter(this));
        characterManager.registerCharacter(new BatmanCharacter());

        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), this);

        Commands.register(this, "givecharacter", new GiveCharacterCMD(characterManager));
    }

    @Override
    public void onDisable() {
    }

    public static CharacterWar getInstance() {
        return JavaPlugin.getPlugin(CharacterWar.class);
    }

}
