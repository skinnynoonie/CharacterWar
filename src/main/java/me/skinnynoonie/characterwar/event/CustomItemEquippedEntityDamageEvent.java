package me.skinnynoonie.characterwar.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomItemEquippedEntityDamageEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final EntityDamageEvent entityDamageEvent;
    private final List<String> referenceNames;

    public CustomItemEquippedEntityDamageEvent(EntityDamageEvent entityDamageEvent, List<String> referenceNames) {
        this.entityDamageEvent = entityDamageEvent;
        this.referenceNames = referenceNames;
    }

    public EntityDamageEvent getEntityDamageEvent() {
        return this.entityDamageEvent;
    }

    public List<String> getReferenceNames() {
        return this.referenceNames;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return CustomItemEquippedEntityDamageEvent.handlers;
    }

    public static HandlerList getHandlerList() {
        return CustomItemEquippedEntityDamageEvent.handlers;
    }


}
