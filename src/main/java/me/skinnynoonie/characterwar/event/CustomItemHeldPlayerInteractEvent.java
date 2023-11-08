package me.skinnynoonie.characterwar.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public class CustomItemHeldPlayerInteractEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final PlayerInteractEvent playerInteractEvent;
    private final String referenceName;

    public CustomItemHeldPlayerInteractEvent(PlayerInteractEvent playerInteractEvent, String referenceName) {
        this.playerInteractEvent = playerInteractEvent;
        this.referenceName = referenceName;
    }

    public PlayerInteractEvent getPlayerInteractEvent() {
        return this.playerInteractEvent;
    }

    public String getReferenceName() {
        return this.referenceName;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return CustomItemHeldPlayerInteractEvent.handlers;
    }

    public static HandlerList getHandlerList() {
        return CustomItemHeldPlayerInteractEvent.handlers;
    }

}
