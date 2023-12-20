package me.skinnynoonie.characterwar.listener;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.item.CustomItemKey;
import me.skinnynoonie.characterwar.repository.CustomItemRepository;
import me.skinnynoonie.characterwar.util.ItemUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InventoryListener implements Listener {

    private final CustomItemRepository customItemRepository;

    public InventoryListener(@NotNull CustomItemRepository customItemRepository) {
        Objects.requireNonNull(customItemRepository, "Parameter customItemRepository cannot be null.");
        this.customItemRepository = customItemRepository;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onArmorChange(PlayerArmorChangeEvent event) {
        String oldItemReferenceName = ItemUtils.getStringFromPDC(event.getOldItem(), CustomItemKey.key());
        if (oldItemReferenceName != null) {
            CustomItem customItem = this.customItemRepository.fromReferenceName(oldItemReferenceName);
            if (customItem != null) {
                customItem.onArmorUnEquip(event);
            }
        }

        String newItemReferenceName = ItemUtils.getStringFromPDC(event.getNewItem(), CustomItemKey.key());
        if (newItemReferenceName != null) {
            CustomItem customItem = this.customItemRepository.fromReferenceName(newItemReferenceName);
            if (customItem != null) {
                customItem.onArmorEquip(event);
            }
        }
    }



}
