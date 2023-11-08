package me.skinnynoonie.characterwar.listener;

import me.skinnynoonie.characterwar.event.CustomItemHeldPlayerInteractEvent;
import me.skinnynoonie.characterwar.item.CustomItemKey;
import me.skinnynoonie.characterwar.repository.CustomItemRepository;
import me.skinnynoonie.characterwar.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PlayerInteractListener implements Listener {

    private final CustomItemRepository customItemRepository;

    public PlayerInteractListener(@NotNull CustomItemRepository customItemRepository) {
        Objects.requireNonNull(customItemRepository, "Parameter customItemRepository is null.");
        this.customItemRepository = customItemRepository;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        String customItemReferenceName = ItemUtils.getStringFromPDC(event.getItem(), CustomItemKey.key());
        if (customItemReferenceName != null) {
            Bukkit.getPluginManager().callEvent(new CustomItemHeldPlayerInteractEvent(event, customItemReferenceName));
        }
    }

}
