package me.skinnynoonie.characterwar.item;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface CustomItemListener extends Listener {

    default void onRightClickWithCustomItem(PlayerInteractEvent event) {
    }

    default void onDamagedWearingCustomItem(EntityDamageEvent event) {
    }

}
