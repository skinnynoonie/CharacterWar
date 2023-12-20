package me.skinnynoonie.characterwar.listener;

import me.skinnynoonie.characterwar.cooldown.CooldownManager;
import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.repository.CustomItemRepository;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class PlayerConnectionListener implements Listener {

    private final CustomItemRepository customItemRepository;

    public PlayerConnectionListener(@NotNull CustomItemRepository customItemRepository) {
        Objects.requireNonNull(customItemRepository, "Parameter customItemRepository cannot be null.");
        this.customItemRepository = customItemRepository;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        CooldownManager.getInstance().clearUUID(uuid);
        this.customItemRepository.getAllRegistered().forEach(
                customItem -> customItem.onPlayerLeave(event)
        );
    }

}
