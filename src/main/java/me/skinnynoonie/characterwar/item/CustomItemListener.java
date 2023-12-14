package me.skinnynoonie.characterwar.item;

import me.skinnynoonie.characterwar.eventinfo.DamageEventInfo;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public interface CustomItemListener extends Listener {

    default void onDamagedWhileWearing(EntityDamageEvent event) {}
    default void onDamagedWhileHolding(EntityDamageEvent event) {}

    default void onDamagedByPlayerWhileWearing(EntityDamageByEntityEvent event, DamageEventInfo info) {}
    default void onDamagedByPlayerWhileHolding(EntityDamageByEntityEvent event, DamageEventInfo info) {}

    default void onAttackPlayerWhileWearing(EntityDamageByEntityEvent event, DamageEventInfo info) {}
    default void onAttackPlayerWhileHolding(EntityDamageByEntityEvent event, DamageEventInfo info) {}


}
