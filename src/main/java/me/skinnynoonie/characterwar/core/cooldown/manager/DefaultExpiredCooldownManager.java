package me.skinnynoonie.characterwar.core.cooldown.manager;

import com.google.common.base.Preconditions;
import me.skinnynoonie.characterwar.core.cooldown.CooldownManager;
import me.skinnynoonie.characterwar.core.cooldown.CooldownTimer;
import me.skinnynoonie.characterwar.core.cooldown.timer.DefaultExpiredCooldownTimer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DefaultExpiredCooldownManager implements CooldownManager {

    private final Map<UUID, Map<String, CooldownTimer>> uuidToKeyTimers;

    public DefaultExpiredCooldownManager() {
        this.uuidToKeyTimers = new HashMap<>();
    }

    @Override
    public boolean cooldownExpired(@NotNull UUID uuid, @NotNull String key) {
        Preconditions.checkNotNull(uuid, "Parameter uuid is null.");
        Preconditions.checkNotNull(key, "Parameter key is null.");
        Map<String, CooldownTimer> keyToTimerCache = this.uuidToKeyTimers.get(uuid);
        if (keyToTimerCache == null) {
            return true;
        }
        CooldownTimer timer = keyToTimerCache.get(key);
        if (timer == null) {
            return true;
        }
        if (timer.expired()) {
            keyToTimerCache.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOnCooldown(@NotNull UUID uuid, @NotNull String key) {
        return !this.cooldownExpired(uuid, key);
    }

    @Override
    public void startCooldown(@NotNull UUID uuid, @NotNull String key, long millis) {
        Preconditions.checkNotNull(uuid, "Parameter uuid is null.");
        Preconditions.checkNotNull(key, "Parameter key is null.");
        this.uuidToKeyTimers.putIfAbsent(uuid, new HashMap<>());
        this.uuidToKeyTimers.get(uuid).putIfAbsent(key, new DefaultExpiredCooldownTimer());
        this.uuidToKeyTimers.get(uuid).get(key).start(millis);
    }

    @Override
    public void clearUUID(@NotNull UUID uuid) {
        Preconditions.checkNotNull(uuid, "Parameter uuid is null.");
        this.uuidToKeyTimers.remove(uuid);
    }

}
