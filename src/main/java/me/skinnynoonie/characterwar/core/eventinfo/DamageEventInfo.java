package me.skinnynoonie.characterwar.core.eventinfo;

import com.google.common.base.Preconditions;
import me.skinnynoonie.characterwar.core.item.CustomItemManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DamageEventInfo extends EventInfo {

    private final Player attacker;
    private final Player victim;
    private final boolean attackerIsShooter;

    public DamageEventInfo(@NotNull CustomItemManager customItemManager,
                           @NotNull Player attacker,
                           @NotNull Player victim,
                           boolean attackerIsShooter) {
        super(customItemManager);
        Preconditions.checkNotNull(attacker, "attacker is null.");
        Preconditions.checkNotNull(victim, "victim is null.");
        this.attacker = attacker;
        this.victim = victim;
        this.attackerIsShooter = attackerIsShooter;
    }

    @NotNull
    public Player getAttacker() {
        return this.attacker;
    }

    @NotNull
    public Player getVictim() {
        return this.victim;
    }

    public boolean attackerIsShooter() {
        return this.attackerIsShooter;
    }

}
