package me.skinnynoonie.characterwar.potion;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PermanentPotionEffectManager {

    private final PotionEffectApplierTask potionEffectApplierTask;

    public PermanentPotionEffectManager(@NotNull Plugin plugin) {
        Preconditions.checkNotNull(plugin, "Parameter plugin is null.");
        this.potionEffectApplierTask = new PotionEffectApplierTask();
        this.potionEffectApplierTask.runTaskTimer(plugin, 0, 20 * 5);
    }

    public void applyEffectLoop(UUID playerUUID, PotionEffectType potionEffect, int amplifier) {
        this.potionEffectApplierTask.addPotionEffectAction(
                new PotionEffectAction(playerUUID, new PotionEffect(potionEffect, 20 * 10, amplifier), true)
        );
    }

    public void removeEffectLoop(UUID playerUUID, PotionEffectType potionEffectType) {
        this.potionEffectApplierTask.removePotionEffectAction(playerUUID, potionEffectType, true);
    }

}
