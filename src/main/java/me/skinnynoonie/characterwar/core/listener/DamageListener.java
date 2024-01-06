package me.skinnynoonie.characterwar.core.listener;

import me.skinnynoonie.characterwar.core.CharacterWarCorePlugin;
import me.skinnynoonie.characterwar.core.eventinfo.DamageEventInfo;
import me.skinnynoonie.characterwar.core.eventinfo.EventInfo;
import me.skinnynoonie.characterwar.core.item.CustomItemListener;
import me.skinnynoonie.characterwar.core.item.CustomItemManager;
import me.skinnynoonie.characterwar.core.util.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.function.Consumer;

public class DamageListener implements Listener {

    private final CustomItemManager customItemManager;

    public DamageListener(CustomItemManager customItemManager) {
        this.customItemManager = customItemManager;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityDamaged(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }
        EventInfo eventInfo = new EventInfo(this.customItemManager);

        PlayerInventory victimInv = victim.getInventory();
        handleReferenceNames(victimInv.getArmorContents(), listener -> listener.onDamagedWhileWearing(event, eventInfo));
        handleReferenceName(victimInv.getItemInMainHand(), listener -> listener.onDamagedWhileHolding(event, eventInfo));
    }

    @EventHandler
    public void onEntityDamagedByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }

        Player attacker;
        boolean attackerIsShooter = false;
        if (event.getDamager() instanceof Player attackerPlayer) {
            attacker = attackerPlayer;
        } else {
            attacker = this.getShooterFromEvent(event);
            if (attacker == null) { // The source of the POSSIBLE projectile is not a player.
                return;
            }
            attackerIsShooter = true;
        }

        DamageEventInfo damageEventInfo = new DamageEventInfo(this.customItemManager, attacker, victim, attackerIsShooter);

        PlayerInventory victimInv = victim.getInventory();
        this.handleReferenceNames(victimInv.getArmorContents(), listener -> listener.onDamagedByPlayerWhileWearing(event, damageEventInfo));
        this.handleReferenceName(victimInv.getItemInMainHand(), listener -> listener.onDamagedByPlayerWhileHolding(event, damageEventInfo));

        PlayerInventory attackerInv = attacker.getInventory();
        this.handleReferenceNames(attackerInv.getArmorContents(), listener -> listener.onAttackPlayerWhileWearing(event, damageEventInfo));
        this.handleReferenceName(attackerInv.getItemInMainHand(), listener -> listener.onAttackPlayerWhileHolding(event, damageEventInfo));
    }

    private void handleReferenceNames(ItemStack[] items, Consumer<CustomItemListener> consumer) {
        for (ItemStack item : items) {
            this.handleReferenceName(item, consumer);
        }
    }

    private void handleReferenceName(ItemStack item, Consumer<CustomItemListener> consumer) {
        String referenceName = ItemUtils.getStringFromPDC(item, this.customItemManager.getReferenceNameKey());
        if (referenceName != null) {
            this.customItemManager.getListener(referenceName).ifPresent(consumer);
        }
    }

    private Player getShooterFromEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile projectile &&
                projectile.getShooter() instanceof Player shooter) {
            return shooter;
        }
        return null;
    }

}
