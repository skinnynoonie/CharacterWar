package me.skinnynoonie.characterwar;

import me.skinnynoonie.characterwar.character.Characters;
import me.skinnynoonie.characterwar.command.GiveCharacterCommand;
import me.skinnynoonie.characterwar.item.CustomItemKey;
import me.skinnynoonie.characterwar.repository.CustomCharacterRepository;
import me.skinnynoonie.characterwar.repository.CustomCharacterRepositoryImpl;
import me.skinnynoonie.characterwar.repository.CustomItemRepository;
import me.skinnynoonie.characterwar.repository.CustomItemRepositoryImpl;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class CharacterWarPlugin extends JavaPlugin {

    private static CharacterWarPlugin plugin;

    private CustomItemRepository itemRepository;
    private CustomCharacterRepository characterRepository;

    @Override
    public void onEnable() {
        CharacterWarPlugin.plugin = this;
        CustomItemKey.setKey(new NamespacedKey(this, "referenceName"));

        this.itemRepository = new CustomItemRepositoryImpl(this);
        this.characterRepository = new CustomCharacterRepositoryImpl(itemRepository);
        this.characterRepository.register(Characters.createNoonie());

        getCommand("givecharacter").setExecutor(new GiveCharacterCommand(this.characterRepository));
    }

    @Override
    public void onDisable() {
    }

    public CustomCharacterRepository getCharacterRepository() {
        return this.characterRepository;
    }

    public static CharacterWarPlugin getInstance() {
        return CharacterWarPlugin.plugin;
    }

}
