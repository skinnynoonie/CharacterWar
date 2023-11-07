package me.skinnynoonie.characterwar.listener;

import me.skinnynoonie.characterwar.cooldown.CooldownManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        CooldownManager.getInstance().clearUUID(uuid);
    }

}
