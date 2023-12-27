package me.skinnynoonie.characterwar.core.potion;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PotionEffectAction {

    private final UUID playerUUID;
    private final PotionEffect potionEffect;
    private final boolean removeBeforeApply;

    public PotionEffectAction(@NotNull UUID playerUUID,
                              @NotNull PotionEffect potionEffect,
                              boolean removeBeforeApply) {
        Preconditions.checkNotNull(playerUUID, "Parameter playerUUID is null.");
        Preconditions.checkNotNull(potionEffect, "Parameter potionEffect is null.");
        this.playerUUID = playerUUID;
        this.potionEffect = potionEffect;
        this.removeBeforeApply = removeBeforeApply;
    }

    public void applyEffect() {
        Player target = Bukkit.getPlayer(this.playerUUID);
        if (target != null) {
            if (this.removeBeforeApply) {
                target.removePotionEffect(this.potionEffect.getType());
            }
            target.addPotionEffect(this.potionEffect);
        }
    }

    public void removeEffect() {
        Player target = Bukkit.getPlayer(this.playerUUID);
        if (target != null) {
            target.removePotionEffect(this.potionEffect.getType());
        }
    }

    @NotNull
    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    @NotNull
    public PotionEffect getPotionEffect() {
        return this.potionEffect;
    }

    public boolean isRemoveBeforeApply() {
        return this.removeBeforeApply;
    }

}
