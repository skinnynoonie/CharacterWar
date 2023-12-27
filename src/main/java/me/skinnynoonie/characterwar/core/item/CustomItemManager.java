package me.skinnynoonie.characterwar.core.item;

import com.google.common.base.Preconditions;
import me.skinnynoonie.characterwar.core.config.ConfigService;
import me.skinnynoonie.characterwar.core.config.CustomItemConfig;
import me.skinnynoonie.characterwar.core.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomItemManager {

    private final Plugin plugin;
    private final ConfigService<CustomItemConfig> itemConfigService;
    private final Map<String, Class<? extends CustomItemConfig>> customItemConfigMap;
    private final Map<String, CustomItemListener> customItemListenerMap;
    private final NamespacedKey referenceNameKey;

    public CustomItemManager(@NotNull Plugin plugin,
                             @NotNull ConfigService<CustomItemConfig> itemConfigService) {
        this.plugin = plugin;
        this.itemConfigService = itemConfigService;
        this.customItemConfigMap = new HashMap<>();
        this.customItemListenerMap = new HashMap<>();
        this.referenceNameKey = new NamespacedKey(plugin, "referenceName");
    }

    public void registerItemConfig(@NotNull String referenceName, @NotNull CustomItemConfig defaultItemConfig) throws IOException {
        Preconditions.checkNotNull(referenceName, "referenceName is null.");
        Preconditions.checkNotNull(defaultItemConfig, "defaultItemConfig is null.");
        Preconditions.checkState(defaultItemConfig.isValid(), "defaultItemConfig is not valid. (Illegal values?)");
        this.itemConfigService.loadAndRegister(defaultItemConfig);
        this.customItemConfigMap.put(referenceName, defaultItemConfig.getClass());
    }

    public void registerItemListener(@NotNull String referenceName, @NotNull CustomItemListener itemListener) {
        Preconditions.checkNotNull(referenceName, "referenceName is null.");
        Preconditions.checkNotNull(itemListener, "itemListener is null.");
        this.customItemListenerMap.put(referenceName, itemListener);
        Bukkit.getPluginManager().registerEvents(itemListener, this.plugin);
    }

    @NotNull
    public Optional<CustomItemListener> getItemListener(String referenceName) {
        return Optional.ofNullable(this.customItemListenerMap.get(referenceName));
    }

    @NotNull
    public Optional<ItemStack> getItem(String referenceName) {
        if (!this.customItemConfigMap.containsKey(referenceName)) {
            return Optional.empty();
        }
        Class<? extends CustomItemConfig> itemConfigClass = this.customItemConfigMap.get(referenceName);
        return Optional.ofNullable(
                this.itemConfigService.get(itemConfigClass).buildItem(this.referenceNameKey, referenceName)
        );
    }

    @NotNull
    public Plugin getPlugin() {
        return this.plugin;
    }

    @NotNull
    public ConfigService<CustomItemConfig> getItemConfigService() {
        return this.itemConfigService;
    }

    @NotNull
    public NamespacedKey getReferenceNameKey() {
        return this.referenceNameKey;
    }

}
