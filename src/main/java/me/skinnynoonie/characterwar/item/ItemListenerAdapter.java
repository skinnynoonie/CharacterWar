package me.skinnynoonie.characterwar.item;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public interface ItemListenerAdapter {

    /**
     * Called when a player is hurt/damaged while holding this weapon.
     */
    default void onPlayerDamagedWithMainHand(@NotNull EntityDamageEvent event, @NotNull Player victim) {
    }

    default void onPlayerDamagedWithArmor(@NotNull EntityDamageEvent event, @NotNull Player victim) {
    }

}
