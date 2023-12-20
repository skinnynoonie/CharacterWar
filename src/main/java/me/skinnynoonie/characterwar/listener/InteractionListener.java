package me.skinnynoonie.characterwar.listener;

import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.item.CustomItemKey;
import me.skinnynoonie.characterwar.repository.CustomItemRepository;
import me.skinnynoonie.characterwar.util.ItemUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InteractionListener implements Listener {

    private final CustomItemRepository customItemRepository;

    public InteractionListener(@NotNull CustomItemRepository customItemRepository) {
        Objects.requireNonNull(customItemRepository, "Parameter customItemRepository cannot be null.");
        this.customItemRepository = customItemRepository;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent event) {
        String referenceName = ItemUtils.getStringFromPDC(event.getItem(), CustomItemKey.key());
        if (referenceName == null) {
            return;
        }
        CustomItem customItem = this.customItemRepository.fromReferenceName(referenceName);
        if (customItem == null) {
            return;
        }
        customItem.onInteractWhileHolding(event);
    }

}
