package me.skinnynoonie.characterwar.items.flash;

import com.destroystokyo.paper.ParticleBuilder;
import me.skinnynoonie.characterwar.CharacterWarPlugin;
import me.skinnynoonie.characterwar.cooldown.CooldownManager;
import me.skinnynoonie.characterwar.eventinfo.DamageEventInfo;
import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.item.CustomItemKey;
import me.skinnynoonie.characterwar.item.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class TheFlashHelmet implements CustomItem {

    @Override
    public @NotNull ItemStack getItem() {
        return new ItemBuilder(Material.LEATHER_HELMET)
                .setColor(Color.RED)
                .setName("<red>The Flash's Helmet")
                .setUnbreakable(true)
                .setPDCValue(CustomItemKey.key(), PersistentDataType.STRING, "the_flash_helmet")
                .setLore("<gray>Hit someone while sneaking to teleport",
                        "<gray>behind them. (5 second cooldown)")
                .build();
    }

    @Override
    public void onAttackPlayerWhileWearing(EntityDamageByEntityEvent event, DamageEventInfo info) {
        if (info.attackerIsShooter()) {
            return;
        }

        Player attacker = info.attacker();
        Player victim = info.victim();

        if (CooldownManager.getInstance().isOnCooldown(attacker.getUniqueId(), "the_flash_helmet")) {
            return;
        }

        if (!attacker.isSneaking()) {
            return;
        }

        Location victimLoc = victim.getLocation();
        Vector fiveBlocksBehindVictim =  victimLoc.getDirection().multiply(-5).setY(0);

        Bukkit.getScheduler().runTaskLater(CharacterWarPlugin.getInstance(), () -> {
            BlockIterator blockIterator = new BlockIterator(victimLoc.getWorld(), victimLoc.toVector(), fiveBlocksBehindVictim, 0, 5);
            while (blockIterator.hasNext()) {
                if (blockIterator.next().getType() != Material.AIR) {
                    return;
                }
            }

            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.WHITE, 15);
            attacker.getWorld().spawnParticle(Particle.REDSTONE,
                    attacker.getLocation(),
                    5,
                    dustOptions);
            attacker.getWorld().spawnParticle(Particle.REDSTONE,
                    attacker.getEyeLocation(),
                    5,
                    dustOptions);

            attacker.teleport(victimLoc.add(fiveBlocksBehindVictim));
            CooldownManager.getInstance().startCooldown(attacker.getUniqueId(), "the_flash_helmet", 5000);
        }, 1);
    }
}
