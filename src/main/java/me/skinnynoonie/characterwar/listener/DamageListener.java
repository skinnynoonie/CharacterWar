package me.skinnynoonie.characterwar.listener;

import me.skinnynoonie.characterwar.event.CustomItemEquippedEntityDamageEvent;
import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.item.CustomItemKey;
import me.skinnynoonie.characterwar.repository.CustomItemRepository;
import me.skinnynoonie.characterwar.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DamageListener implements Listener {

    private final CustomItemRepository customItemRepository;

    public DamageListener(@NotNull CustomItemRepository customItemRepository) {
        Objects.requireNonNull(customItemRepository, "Parameter customItemRepository is null.");
        this.customItemRepository = customItemRepository;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }

        List<String> referenceNamesInArmor = new ArrayList<>();
        for (ItemStack itemStack : victim.getInventory().getArmorContents()) {
            String customItemReferenceName = ItemUtils.getStringFromPDC(itemStack, CustomItemKey.key());
            if (customItemReferenceName == null) {
                continue;
            }
            referenceNamesInArmor.add(customItemReferenceName);
        }
        Bukkit.getPluginManager().callEvent(new CustomItemEquippedEntityDamageEvent(event, referenceNamesInArmor));
    }

}
