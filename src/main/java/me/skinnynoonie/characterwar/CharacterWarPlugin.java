package me.skinnynoonie.characterwar;

import me.skinnynoonie.characterwar.characters.Characters;
import me.skinnynoonie.characterwar.command.GiveCharacterCommand;
import me.skinnynoonie.characterwar.item.CustomItemKey;
import me.skinnynoonie.characterwar.listener.DamageListener;
import me.skinnynoonie.characterwar.repository.CustomCharacterRepository;
import me.skinnynoonie.characterwar.repository.CustomCharacterRepositoryImpl;
import me.skinnynoonie.characterwar.repository.CustomItemRepository;
import me.skinnynoonie.characterwar.repository.CustomItemRepositoryImpl;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CharacterWarPlugin extends JavaPlugin {

    private static CharacterWarPlugin plugin;

    private CustomItemRepository itemRepository;
    private CustomCharacterRepository characterRepository;

    @Override
    public void onEnable() {
        CharacterWarPlugin.plugin = this;
        CustomItemKey.setKey(new NamespacedKey(this, "referenceName"));
        this.registerRepositoriesAndCharacters();
        this.registerEventListeners();
        this.registerCommands();
    }

    @Override
    public void onDisable() {
    }

    public CustomItemRepository getItemRepository() {
        return this.itemRepository;
    }

    public CustomCharacterRepository getCharacterRepository() {
        return this.characterRepository;
    }

    public static CharacterWarPlugin getInstance() {
        return CharacterWarPlugin.plugin;
    }

    private void registerRepositoriesAndCharacters() {
        this.itemRepository = new CustomItemRepositoryImpl(this);
        this.characterRepository = new CustomCharacterRepositoryImpl(this.itemRepository);
        this.characterRepository.register(Characters.createNoonie());
    }

    private void registerEventListeners() {
        PluginManager pluginManager = super.getServer().getPluginManager();
        pluginManager.registerEvents(new DamageListener(this.itemRepository), this);
    }

    private void registerCommands() {
        super.getCommand("givecharacter").setExecutor(new GiveCharacterCommand(this.characterRepository));
    }

}
