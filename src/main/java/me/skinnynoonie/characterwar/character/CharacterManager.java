package me.skinnynoonie.characterwar.character;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CharacterManager {

    private final Plugin plugin;
    private final Map<String, Character> characters = new HashMap<>();

    public CharacterManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void registerCharacter(Character character) {
        try {
            Listener characterAsListener = (Listener) character;
            Bukkit.getServer().getPluginManager().registerEvents(characterAsListener, plugin);
            characters.put(character.getReferenceName(), character);
        } catch (ClassCastException exception) {
            throw new IllegalStateException("Character implementation must extend Listener. Failed implementation: " + character.getClass().getName());
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
