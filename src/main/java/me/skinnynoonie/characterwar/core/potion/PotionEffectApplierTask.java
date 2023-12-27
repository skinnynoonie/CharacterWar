package me.skinnynoonie.characterwar.core.potion;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PotionEffectApplierTask extends BukkitRunnable {

    private final Set<PotionEffectAction> potionEffectActions;

    public PotionEffectApplierTask() {
        this.potionEffectActions = new HashSet<>();
    }

    public void addPotionEffectAction(@NotNull PotionEffectAction potionEffectAction) {
        Preconditions.checkNotNull(potionEffectAction, "Parameter potionEffectAction is null.");
        this.potionEffectActions.add(potionEffectAction);
    }

    public void removePotionEffectAction(@NotNull UUID playerUUID,
                                         @NotNull PotionEffectType potionEffectType,
                                         boolean clearEffect) {
        Preconditions.checkNotNull(playerUUID, "Parameter playerUUID is null.");
        Preconditions.checkNotNull(potionEffectType, "Parameter potionEffectType is null.");
        this.potionEffectActions.removeIf(action -> action.getPlayerUUID().equals(playerUUID) && action.getPotionEffect().getType() == potionEffectType);
        if (clearEffect) {
            Player target = Bukkit.getPlayer(playerUUID);
            if (target != null) {
                target.removePotionEffect(potionEffectType);
            }
        }
    }

    @Override
    public void run() {
        this.potionEffectActions.forEach(PotionEffectAction::applyEffect);
    }



}
