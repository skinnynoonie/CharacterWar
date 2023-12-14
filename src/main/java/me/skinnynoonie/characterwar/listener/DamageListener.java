package me.skinnynoonie.characterwar.listener;

import me.skinnynoonie.characterwar.eventinfo.DamageEventInfo;
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

        ItemStack[] armor = victim.getInventory().getArmorContents();
        for (ItemStack armorPiece : armor) {
            String referenceName = ItemUtils.getStringFromPDC(armorPiece, CustomItemKey.key());
            if (referenceName == null) {
                continue;
            }
            this.customItemRepository.fromReferenceName(referenceName).onDamagedWhileWearing(event);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDamagePlayer(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }

        Player attacker = null;
        boolean shooter = false;
        if (event.getDamager() instanceof Player) {
            attacker = (Player) event.getDamager();
        } else if (event.getDamager() instanceof Projectile projectile) {
            if (projectile.getShooter() instanceof Player) {
                attacker = (Player) projectile.getShooter();
                shooter = true;
            }
        }

        if (attacker == null) {
            return;
        }

        DamageEventInfo damageEventInfo = new DamageEventInfo(attacker, shooter);

        ItemStack[] armor = victim.getInventory().getArmorContents();
        for (ItemStack armorPiece : armor) {
            String referenceName = ItemUtils.getStringFromPDC(armorPiece, CustomItemKey.key());
            if (referenceName == null) {
                continue;
            }
            this.customItemRepository.fromReferenceName(referenceName).onDamagedByPlayerWhileWearing(event, damageEventInfo);
        }

    }

}
