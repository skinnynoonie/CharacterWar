package me.skinnynoonie.characterwar.repository;

import me.skinnynoonie.characterwar.item.CustomItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public interface CustomItemRepository {

    /**
     * Registers a custom item.
     * @param customItem The custom item which will be registered.
     *                   This method will check if the custom item has the key {@link me.skinnynoonie.characterwar.item.CustomItemKey#key()}
     *                   in its PDC. This custom item will also be registered
     *                   using {@link org.bukkit.plugin.PluginManager#registerEvents(org.bukkit.event.Listener, org.bukkit.plugin.Plugin)}.
     * @apiNote If this is used to register {@link CustomItem#AIR}, then this method should fail silently.
     */
    void register(@NotNull CustomItem customItem);

    @Nullable CustomItem fromReferenceName(@NotNull String referenceName);

    @SuppressWarnings("ConstantConditions")
    default void registerAll(@NotNull List<@NotNull CustomItem> customItems) {
        Objects.requireNonNull(customItems, "Parameter customItems is null.");
        if (customItems.stream().anyMatch(Objects::isNull)) {
            throw new IllegalStateException("Parameter customItems has a null element.");
        }
        customItems.forEach(this::register);
    }

}
