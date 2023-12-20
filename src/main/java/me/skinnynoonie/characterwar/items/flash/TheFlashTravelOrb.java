package me.skinnynoonie.characterwar.items.flash;

import me.skinnynoonie.bukkit.locationutils.shape.ConcreteLine;
import me.skinnynoonie.characterwar.CharacterWarPlugin;
import me.skinnynoonie.characterwar.cooldown.CooldownManager;
import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.item.CustomItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class TheFlashTravelOrb implements CustomItem {
    @Override
    public @NotNull ItemStack getItem() {
        return new CustomItemBuilder(Material.YELLOW_DYE, "the_flash_travel_orb")
                .setName("<yellow>The Flash's Travel Orb")
                .setLore("<gray>Right click to teleport", "<gray>50 blocks in front of you. (5min)")
                .build();
    }

    @Override
    public void onInteractWhileHolding(PlayerInteractEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();

        if (CooldownManager.getInstance().isOnCooldown(player.getUniqueId(), "the_flash_travel_orb")) {
            return;
        }
        CooldownManager.getInstance().startCooldown(player.getUniqueId(), "the_flash_travel_orb", 1000 * 60 * 5);

        Location playerLoc = player.getLocation();
        Location fiftyBlocksInFront = this.getLocationFiftyBlocksInFront(playerLoc);

        this.displayEffects(playerLoc, fiftyBlocksInFront);
        player.teleport(fiftyBlocksInFront);
    }

    private Location getLocationFiftyBlocksInFront(Location start) {
        Vector locationDir = start.getDirection().setY(0).normalize();
        return start.clone().add(locationDir.multiply(50));
    }

    private void displayEffects(Location start, Location end) {
        start.getWorld().strikeLightningEffect(start);
        end.getWorld().strikeLightningEffect(end);
        Bukkit.getScheduler().runTaskAsynchronously(CharacterWarPlugin.getInstance(),
                () -> this.displayLine(start, end)
        );
    }

    private void displayLine(Location start, Location end) {
        Particle.DustOptions dustOptions =  new Particle.DustOptions(Color.YELLOW, 5);
        for (Vector p : new ConcreteLine(start, end, 0.2).getPositionList()) {
            Location currentLocFromStart = start.clone().add(p);
            start.getWorld().spawnParticle(Particle.REDSTONE, currentLocFromStart, 1, dustOptions);
        }
    }

}
