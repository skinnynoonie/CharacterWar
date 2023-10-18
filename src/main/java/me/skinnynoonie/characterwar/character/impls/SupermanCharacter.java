package me.skinnynoonie.characterwar.character.impls;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.skinnynoonie.characterwar.character.AbstractCharacter;
import me.skinnynoonie.characterwar.character.CharacterVerse;
import me.skinnynoonie.characterwar.cooldown.CooldownManager;
import me.skinnynoonie.characterwar.util.ArmorSetBuilder;
import me.skinnynoonie.characterwar.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.UUID;

public class SupermanCharacter extends AbstractCharacter {

    private final CooldownManager jumpCooldownManager = CooldownManager.create();
    private final CooldownManager attackCooldownManager = CooldownManager.create();
    private final Plugin plugin;

    public SupermanCharacter(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }
        if (!(event.getEntity() instanceof Player player) || !allArmorIsEquipped(player)) {
            return;
        }
        event.setDamage(0);
    }

    @EventHandler
    public void onDamageReceived(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player victim) || !allArmorIsEquipped(victim)) {
            return;
        }
        if (event.getDamage() <= 2) {
            event.setDamage(0);
            return;
        }
        event.setDamage(event.getDamage() * 0.3);
    }

    @EventHandler
    public void onDamageDealt(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker) || !allArmorIsEquipped(attacker)) {
            return;
        }
        event.setDamage(event.getDamage() * 10);

        UUID attackerUUID = attacker.getUniqueId();
        if (attackCooldownManager.isOnCooldown(attackerUUID)) {
            return;
        }
        attackCooldownManager.startCooldown(attackerUUID, 2500);

        Vector playerDir = attacker.getLocation().getDirection();
        Vector playerDirModified = playerDir.multiply(new Vector(1.5, 0, 1.5));
        event.getEntity().setVelocity(playerDirModified);
    }

    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!allArmorIsEquipped(player) || !player.isSneaking() || jumpCooldownManager.isOnCooldown(uuid)) {
            return;
        }
        jumpCooldownManager.startCooldown(uuid, 2000);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Vector playerDirBoosted = player.getLocation().getDirection().multiply(6);
            player.setVelocity(playerDirBoosted);
        }, 1);
    }

    @Override
    public String getDisplayName() {
        return "Superman";
    }

    @Override
    public String getDescription() {
        return "Invulnerability, incredible strength, the ability to leap incredible distances, and super speed.";
    }

    @Override
    public CharacterVerse getVerse() {
        return CharacterVerse.DC;
    }

    @Override
    public ItemStack[] getArmor() {
        return new ArmorSetBuilder()
                .setChestplate(
                        new ItemBuilder(Material.LEATHER_CHESTPLATE)
                                .setName("<red>Superman's Chestplate")
                                .setColor(Color.BLUE)
                                .setUnbreakable(true)
                                .build()
                )
                .setLeggings(
                        new ItemBuilder(Material.LEATHER_LEGGINGS)
                                .setName("<red>Superman's Leggings")
                                .setColor(Color.BLUE)
                                .setUnbreakable(true)
                                .build()
                )
                .setBoots(
                        new ItemBuilder(Material.LEATHER_BOOTS)
                                .setName("<red>Superman's Boots")
                                .setColor(Color.RED)
                                .setUnbreakable(true)
                                .build()
                )
                .build();
    }

    @Override
    public ItemStack[] getCustomItems() {
        return new ItemStack[0];
    }

}
