package me.skinnynoonie.characterwar.repository;

import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.item.CustomItemKey;
import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CustomItemRepositoryImpl implements CustomItemRepository {

    private final Plugin plugin;
    private final Map<String, CustomItem> referenceNameToCustomItem;

    public CustomItemRepositoryImpl(@NotNull Plugin plugin) {
        Objects.requireNonNull(plugin, "Parameter plugin is null.");
        this.plugin = plugin;
        this.referenceNameToCustomItem = new HashMap<>();
    }

    @Override
    public void register(@NotNull CustomItem customItem) {
        Objects.requireNonNull(customItem, "Parameter customItem is null.");
        if (customItem.isAir()) {
            return;
        }
        ItemMeta itemMeta = customItem.getItem().getItemMeta();
        if (itemMeta == null) {
            throw new IllegalStateException("ItemMeta for a custom item is null.");
        }
        String referenceName = itemMeta.getPersistentDataContainer().get(CustomItemKey.key(), PersistentDataType.STRING);
        if (referenceName == null) {
            throw new IllegalStateException("Reference name for custom item is null.");
        }
        Bukkit.getServer().getPluginManager().registerEvents(customItem, this.plugin);
        this.referenceNameToCustomItem.put(referenceName, customItem);
    }

    public @Nullable CustomItem fromReferenceName(@NotNull String referenceName) {
        Objects.requireNonNull(referenceName, "Parameter referenceName is null!");
        return this.referenceNameToCustomItem.get(referenceName);
    }

    @Override
    public @NotNull List<@NotNull CustomItem> getAllRegistered() {
        return new ArrayList<>(this.referenceNameToCustomItem.values());
    }

}
