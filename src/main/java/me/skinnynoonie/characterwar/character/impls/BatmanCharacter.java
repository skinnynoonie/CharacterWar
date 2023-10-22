package me.skinnynoonie.characterwar.character.impls;

import com.destroystokyo.paper.ParticleBuilder;
import me.skinnynoonie.characterwar.character.AbstractCharacter;
import me.skinnynoonie.characterwar.character.CharacterVerse;
import me.skinnynoonie.characterwar.cooldown.CooldownManager;
import me.skinnynoonie.characterwar.item.ArmorSetBuilder;
import me.skinnynoonie.characterwar.item.ItemBuilder;
import me.skinnynoonie.characterwar.util.Messages;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BatmanCharacter extends AbstractCharacter {

    private final ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET)
            .setUnbreakable(true)
            .setName("<black>Batman's Mask")
            .setColor(Color.BLACK)
            .build();
    private final ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE)
            .setUnbreakable(true)
            .setName("<black>Batman's Chestplate")
            .setColor(Color.BLACK)
            .build();
    private final ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS)
            .setUnbreakable(true)
            .setName("<gray>Batman's Leggings")
            .setColor(Color.GRAY)
            .build();
    private final ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS)
            .setUnbreakable(true)
            .setName("<gray>Batman's Boots")
            .setColor(Color.GRAY)
            .build();
    private final ItemStack grapplingHook = new ItemBuilder(Material.TRIPWIRE_HOOK)
            .setName("<gray>Batman's Grappling Hook")
            .build();

    @EventHandler
    public void onRightClickWithGrappling(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) {
            return;
        }
        if (!itemIsSimilar(event.getItem(), grapplingHook)) {
            return;
        }
        UUID uuid = event.getPlayer().getUniqueId();
        if (CooldownManager.getInstance().isOnCooldown(uuid, "batman_grappling_hook")) {
            return;
        }
        CooldownManager.getInstance().startCooldown(uuid, "batman_grappling_hook", 5000);

        Location playerEyeLoc = event.getPlayer().getEyeLocation();
        World world = playerEyeLoc.getWorld();
        RayTraceResult rayTraceResult = world.rayTraceBlocks(playerEyeLoc, playerEyeLoc.getDirection(), 50);

        if (rayTraceResult == null || rayTraceResult.getHitBlock() == null) {
            event.getPlayer().sendActionBar(Messages.text("<red>Out of range!"));
            return;
        }

        Vector playerEyeToHitBlock = rayTraceResult.getHitBlock().getLocation().toVector().subtract(playerEyeLoc.toVector());
        double lineLength = drawLine(playerEyeLoc, playerEyeToHitBlock);

        playerEyeLoc.setPitch(playerEyeLoc.getPitch() - 10);
        Vector increasedPitchPlayerEye = playerEyeLoc.getDirection();
        increasedPitchPlayerEye.normalize().multiply(lineLength);

        event.getPlayer().setVelocity(increasedPitchPlayerEye);
    }

    private double drawLine(Location start, Vector end) {
        start = start.clone();
        end = end.clone();
        ParticleBuilder particle = new ParticleBuilder(Particle.DUST_COLOR_TRANSITION)
                .data(new Particle.DustTransition(Color.GRAY, Color.WHITE, 3))
                .extra(1)
                .allPlayers();

        double playerEyeToHitBlockLength = end.length();
        end.normalize().multiply(0.5);
        for (double i = 0; i < playerEyeToHitBlockLength; i += 0.5) {
            Location offsetLoc = start.add(end);
            particle.location(offsetLoc).spawn();
        }
        return playerEyeToHitBlockLength;
    }

    @Override
    public @NotNull String getDisplayName() {
        return "batman";
    }

    @Override
    public @NotNull String getDescription() {
        return "Cool guy";
    }

    @Override
    public @NotNull CharacterVerse getVerse() {
        return CharacterVerse.DC;
    }

    @Override
    public @NotNull ItemStack[] getArmor() {
        return new ArmorSetBuilder()
                .setHelmet(helmet)
                .setChestplate(chestplate)
                .setLeggings(leggings)
                .setBoots(boots)
                .build();
    }

    @Override
    public @NotNull ItemStack[] getCustomItems() {
        return new ItemStack[] {
                grapplingHook
        };
    }
}
