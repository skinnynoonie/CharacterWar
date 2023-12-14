package me.skinnynoonie.characterwar.listener;

import me.skinnynoonie.characterwar.eventinfo.DamageEventInfo;
import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.item.CustomItemKey;
import me.skinnynoonie.characterwar.repository.CustomItemRepository;
import me.skinnynoonie.characterwar.util.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class DamageListener implements Listener {

    private final CustomItemRepository customItemRepository;

    public DamageListener(@NotNull CustomItemRepository customItemRepository) {
        Objects.requireNonNull(customItemRepository, "Parameter customItemRepository cannot be null.");
        this.customItemRepository = customItemRepository;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }
        this.handleItemStacks(customItem -> customItem.onDamagedWhileWearing(event),
                victim.getInventory().getArmorContents());
        this.handleItemStacks(customItem -> customItem.onDamagedWhileHolding(event),
                victim.getInventory().getItemInMainHand());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDamagePlayer(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }

        Player attacker;
        boolean attackerIsShooter = false;
        if (event.getDamager() instanceof Player attackerPlayer) {
            attacker = attackerPlayer;
        } else {
            attacker = this.getShooterFromEvent(event);
            if (attacker == null) {
                return;
            }
            attackerIsShooter = true;
        }

        DamageEventInfo damageEventInfo = new DamageEventInfo(attacker, victim, attackerIsShooter);
        this.handleItemStacks(customItem -> customItem.onDamagedByPlayerWhileWearing(event, damageEventInfo),
                victim.getInventory().getArmorContents());
        this.handleItemStacks(customItem -> customItem.onDamagedByPlayerWhileHolding(event, damageEventInfo),
                victim.getInventory().getItemInMainHand());

        this.handleItemStacks(customItem -> customItem.onAttackPlayerWhileWearing(event, damageEventInfo),
                attacker.getInventory().getArmorContents());
        this.handleItemStacks(customItem -> customItem.onAttackPlayerWhileHolding(event, damageEventInfo),
                attacker.getInventory().getItemInMainHand());
    }

    private void handleItemStacks(Consumer<CustomItem> consumer, ItemStack... items) {
        for (ItemStack armorPiece : items) {
            String referenceName = ItemUtils.getStringFromPDC(armorPiece, CustomItemKey.key());
            if (referenceName == null) {
                continue;
            }
            CustomItem customItem = this.customItemRepository.fromReferenceName(referenceName);
            if (customItem == null) {
                continue;
            }
            consumer.accept(customItem);
        }
    }

    private Player getShooterFromEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile projectile
                && projectile.getShooter() instanceof Player shooter) {
            return shooter;
        }
        return null;
    }

}
