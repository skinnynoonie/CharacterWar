package me.skinnynoonie.characterwar.item.events;

import me.skinnynoonie.characterwar.item.CustomItemImpl;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public record CustomEntityDamageEvent(NamespacedKey key, Map<String, CustomItemImpl> itemImpls) implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }

        handleMainHand(event, victim);
        handleArmorContents(event, victim);
    }

    private void handleMainHand(EntityDamageEvent event, Player victim) {
        ItemMeta mainHandItemMeta = victim.getInventory().getItemInMainHand().getItemMeta();
        if (mainHandItemMeta == null) {
            return;
        }

        String possibleCustomItemKey = mainHandItemMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        CustomItemImpl itemImpl = itemImpls.get(possibleCustomItemKey);
        if (itemImpl != null) {
            itemImpl.onPlayerDamagedWithMainHand(event, victim);
        }
    }

    private void handleArmorContents(EntityDamageEvent event, Player victim) {
        ItemStack[] armorContents = victim.getInventory().getArmorContents();
        for (ItemStack armorPiece : armorContents) {
            if (armorPiece == null) {
                return;
            }

            ItemMeta armorPieceItemMeta = armorPiece.getItemMeta();
            if (armorPieceItemMeta == null) {
                continue;
            }

            String possibleCustomItemKey = armorPieceItemMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
            CustomItemImpl itemImpl = itemImpls.get(possibleCustomItemKey);
            if (itemImpl != null) {
                itemImpl.onPlayerDamagedWithArmor(event, victim);
            }
        }
    }


}
