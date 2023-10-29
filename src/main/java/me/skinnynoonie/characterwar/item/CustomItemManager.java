package me.skinnynoonie.characterwar.item;

import me.skinnynoonie.characterwar.item.events.CustomEntityDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class CustomItemManager implements Listener {

    private final NamespacedKey key;
    private final Map<String, CustomItemImpl> customItems = new HashMap<>();

    public CustomItemManager(Plugin plugin, NamespacedKey key) {
        this.key = key;

        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new CustomEntityDamageEvent(key, customItems), plugin);
    }

    public void registerItem(@Nullable CustomItemImpl item) {
        if (item == null) {
            return;
        }

        String itemIdValue = item.getItem().getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (itemIdValue == null) {
            throw new IllegalStateException("Parameter item does not have a valid getItem() method. Missing: PDC Key \"" + key +"\"");
        }
        customItems.put(itemIdValue, item);
    }

}
