package me.skinnynoonie.characterwar.eventinfo;

import org.bukkit.entity.Player;

public record DamageEventInfo(Player attacker,
                              Player victim,
                              boolean attackerIsShooter) {

}
