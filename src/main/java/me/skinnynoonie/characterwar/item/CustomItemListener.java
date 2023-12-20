package me.skinnynoonie.characterwar.item;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.skinnynoonie.characterwar.eventinfo.DamageEventInfo;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public interface CustomItemListener extends Listener {

    default void onDamagedWhileWearing(EntityDamageEvent event) {}
    default void onDamagedWhileHolding(EntityDamageEvent event) {}

    default void onDamagedByPlayerWhileWearing(EntityDamageByEntityEvent event, DamageEventInfo info) {}
    default void onDamagedByPlayerWhileHolding(EntityDamageByEntityEvent event, DamageEventInfo info) {}

    default void onAttackPlayerWhileWearing(EntityDamageByEntityEvent event, DamageEventInfo info) {}
    default void onAttackPlayerWhileHolding(EntityDamageByEntityEvent event, DamageEventInfo info) {}

    default void onArmorEquip(PlayerArmorChangeEvent event) {}
    default void onArmorUnEquip(PlayerArmorChangeEvent event) {}

    /*** Not reference name specific.*/
    default void onPlayerLeave(PlayerQuitEvent event) {}

    default void onInteractWhileHolding(PlayerInteractEvent event) {}

}
