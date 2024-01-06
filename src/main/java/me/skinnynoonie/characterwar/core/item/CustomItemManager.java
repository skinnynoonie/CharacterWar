package me.skinnynoonie.characterwar.core.item;

import com.google.common.base.Preconditions;
import me.skinnynoonie.characterwar.core.config.CustomItemConfig;
import me.skinnynoonie.noonieconfigs.service.ConfigService;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomItemManager {

    private final NamespacedKey referenceNameKey;
    private final ConfigService<CustomItemConfig> configService;
    private final Map<String, Class<? extends CustomItemConfig>> nameToClass;
    private final Map<Class<? extends CustomItemConfig>, CustomItemConfig> configCache;
    private final Map<Class<? extends CustomItemConfig>, CustomItemListener> listeners;

    public CustomItemManager(@NotNull Plugin plugin,
                             @NotNull ConfigService<CustomItemConfig> configService) {
        Preconditions.checkNotNull(plugin, "Parameter plugin is null.");
        Preconditions.checkNotNull(configService, "Parameter configService is null.");

        this.referenceNameKey = new NamespacedKey(plugin, "referenceName");
        this.configService = configService;
        this.nameToClass = new HashMap<>();
        this.configCache = new HashMap<>();
        this.listeners = new HashMap<>();
    }

    public void initialize() {
        try {
            this.configService.initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @throws IOException If an I/O exception occurs
     * @throws me.skinnynoonie.noonieconfigs.exception.MalformedBodyException If the config saved in the storage is not valid.
     * @throws IllegalStateException If the itemConfig is not annotated with @Config or the config is not valid.
     */
    public void registerCustomItem(@NotNull String referenceName,
                                   @NotNull CustomItemConfig itemConfig,
                                   @Nullable CustomItemListener listener) throws IOException {
        Preconditions.checkNotNull(referenceName, "Parameter referenceName is null.");
        Preconditions.checkNotNull(itemConfig, "Parameter itemConfig is null.");
        Preconditions.checkNotNull(listener, "Parameter listener is null.");

        Class<? extends CustomItemConfig> configClass = itemConfig.getClass();
        CustomItemConfig customItemConfig = itemConfig;
        if (this.configService.isSaved(configClass)) {
            customItemConfig = this.configService.loadWithFallback(itemConfig);
        }

        if (!customItemConfig.isValid()) {
            throw new IllegalStateException( "Config is not valid (missing values?) %s".formatted(configClass.getName()) );
        }

        this.configService.save(customItemConfig);
        this.nameToClass.put(referenceName, configClass);
        this.configCache.put(configClass, customItemConfig);
        this.listeners.put(configClass, listener);
    }

    @NotNull
    public Optional<ItemStack> getCustomItem(String referenceName) {
        ItemStack customItem = this.nameToClass.containsKey(referenceName)
                ? new ItemBuilder(this.configCache.get(this.nameToClass.get(referenceName)).getItem())
                        .setPDCValue(this.referenceNameKey, PersistentDataType.STRING, referenceName)
                        .build()
                : null;
        return Optional.ofNullable(customItem);
    }

    @NotNull
    public Optional<CustomItemConfig> getConfig(String referenceName) {
        return Optional.ofNullable(
                this.nameToClass.containsKey(referenceName) ?
                        this.configCache.get(this.nameToClass.get(referenceName)) :
                        null
        );
    }

    @NotNull
    public Optional<CustomItemListener> getListener(String referenceName) {
        return Optional.ofNullable(
                this.nameToClass.containsKey(referenceName) ?
                        this.listeners.get(this.nameToClass.get(referenceName)) :
                        null
        );
    }

    @NotNull
    public NamespacedKey getReferenceNameKey() {
        return this.referenceNameKey;
    }

}
