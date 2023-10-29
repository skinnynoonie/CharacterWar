package me.skinnynoonie.characterwar.character;

import me.skinnynoonie.characterwar.item.CustomItemImpl;
import me.skinnynoonie.characterwar.item.CustomItemManager;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CharacterManager {

    private final Plugin plugin;
    private final CustomItemManager customItemManager;
    private final Map<String, Character> characters = new HashMap<>();

    public CharacterManager(Plugin plugin, CustomItemManager customItemManager) {
        this.plugin = plugin;
        this.customItemManager = customItemManager;
    }

    public void registerCharacter(Character character) {
        characters.put(character.getReferenceName(), character);

        customItemManager.registerItem(character.getHelmetImpl());
        customItemManager.registerItem(character.getChestplateImpl());
        customItemManager.registerItem(character.getLeggingImpl());
        customItemManager.registerItem(character.getBootsImpl());
        for (CustomItemImpl customItemImpl : character.getItemImpls()) {
            customItemManager.registerItem(customItemImpl);
        }
    }

    public Character getCharacterByReferenceName(String referenceName) {
        return characters.get(referenceName);
    }

    public Set<String> getReferenceNames() {
        return characters.keySet();
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Collection<Character> getCharacters() {
        return characters.values();
    }

}
