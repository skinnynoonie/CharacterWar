package me.skinnynoonie.characterwar.items.flash;

import me.skinnynoonie.characterwar.CharacterWarPlugin;
import me.skinnynoonie.characterwar.cooldown.CooldownManager;
import me.skinnynoonie.characterwar.eventinfo.DamageEventInfo;
import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.item.CustomItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class TheFlashHelmet implements CustomItem {

    @Override
    public @NotNull ItemStack getItem() {
        return new CustomItemBuilder(Material.LEATHER_HELMET, "the_flash_helmet")
                .setColor(Color.RED)
                .setName("<red>The Flash's Helmet")
                .setLore("<gray>Hit someone while sneaking to teleport",
                        "<gray>behind them. (5s)")
                .build();
    }

    @Override
    public void onAttackPlayerWhileWearing(EntityDamageByEntityEvent event, DamageEventInfo info) {
        if (info.attackerIsShooter()) {
            return;
        }

        Player attacker = info.attacker();
        Player victim = info.victim();

        if (CooldownManager.getInstance().isOnCooldown(attacker.getUniqueId(), "the_flash_helmet") ||
                !attacker.isSneaking()) {
            return;
        }
        CooldownManager.getInstance().startCooldown(attacker.getUniqueId(), "the_flash_helmet", 5000);

        Location victimLoc = victim.getLocation();
        Vector behindVictim =  victimLoc.getDirection().multiply(-2).setY(0);

        Bukkit.getScheduler().runTaskLaterAsynchronously(CharacterWarPlugin.getInstance(), () -> {
            this.playEffects(attacker.getLocation());
            Bukkit.getScheduler().runTaskLater(CharacterWarPlugin.getInstance(),
                    () -> attacker.teleport(victimLoc.add(behindVictim)), 1);
        }, 1);
    }

    private void playEffects(Location start) {
        start.getWorld().playSound(start, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.WHITE, 15);
        for (int i = 0; i < 10; i++) {
            start.add(0, 1 / 5.0, 0);
            start.getWorld().spawnParticle(Particle.REDSTONE, start, 5, dustOptions);
        }
    }

}
