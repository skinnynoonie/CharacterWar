package me.skinnynoonie.characterwar.core.item;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.skinnynoonie.characterwar.core.eventinfo.DamageEventInfo;
import me.skinnynoonie.characterwar.core.eventinfo.EventInfo;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public interface CustomItemListener extends Listener {

    default void onDamagedWhileWearing(EntityDamageEvent event, EventInfo eventInfo) {}
    default void onDamagedWhileHolding(EntityDamageEvent event, EventInfo eventInfo) {}

    default void onDamagedByPlayerWhileWearing(EntityDamageByEntityEvent event, DamageEventInfo info) {}
    default void onDamagedByPlayerWhileHolding(EntityDamageByEntityEvent event, DamageEventInfo info) {}

    default void onAttackPlayerWhileWearing(EntityDamageByEntityEvent event, DamageEventInfo info) {}
    default void onAttackPlayerWhileHolding(EntityDamageByEntityEvent event, DamageEventInfo info) {}

    default void onArmorEquip(PlayerArmorChangeEvent event, EventInfo eventInfo) {}
    default void onArmorUnEquip(PlayerArmorChangeEvent event, EventInfo eventInfo) {}

    /*** Not reference name specific.*/
    default void onPlayerLeave(PlayerQuitEvent event, EventInfo eventInfo) {}

    default void onInteractWhileHolding(PlayerInteractEvent event, EventInfo eventInfo) {}

}
