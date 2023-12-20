package me.skinnynoonie.characterwar.cooldown;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CooldownManager {

    private static final class InstanceHolder {
        private static final CooldownManager instance = new CooldownManager();
    }

    @NotNull
    public static CooldownManager getInstance() {
        return InstanceHolder.instance;
    }

    private final Map<UUID, Map<String, CooldownTimer>> uuidToKeyTimers;

    private CooldownManager() {
        this.uuidToKeyTimers = new HashMap<>();
    }

    public boolean cooldownExpired(@NotNull UUID uuid, @NotNull String key) {
        Objects.requireNonNull(uuid, "Parameter uuid is null.");
        Objects.requireNonNull(key, "Parameter key is null.");
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

    public boolean isOnCooldown(@NotNull UUID uuid, @NotNull String key) {
        return !this.cooldownExpired(uuid, key);
    }

    public void startCooldown(@NotNull UUID uuid, @NotNull String key, long millis) {
        Objects.requireNonNull(uuid, "Parameter uuid is null.");
        Objects.requireNonNull(key, "Parameter key is null.");
        this.uuidToKeyTimers.putIfAbsent(uuid, new HashMap<>());
        this.uuidToKeyTimers.get(uuid).putIfAbsent(key, new DefaultExpiredCooldownTimer());
        this.uuidToKeyTimers.get(uuid).get(key).start(millis);
    }

    public void clearUUID(@NotNull UUID uuid) {
        Objects.requireNonNull(uuid, "Parameter uuid is null.");
        this.uuidToKeyTimers.remove(uuid);
    }

}
