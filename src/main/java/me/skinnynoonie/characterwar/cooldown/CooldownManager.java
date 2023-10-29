package me.skinnynoonie.characterwar.cooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private static CooldownManager instance;

    public static CooldownManager getInstance() {
        if (instance == null) {
            instance = new CooldownManager();
            return instance;
        }
        return instance;
    }

    private final Map<UUID, Map<String, DefaultExpiredCooldownTimer>> cooldowns = new HashMap<>();

    private CooldownManager() {
    }

    public boolean cooldownExpired(UUID uuid, String key) {
        if (cooldowns.get(uuid) == null) {
            return true;
        }
        if (cooldowns.get(uuid).get(key) == null) {
            return false;
        }
        return cooldowns.get(uuid).get(key).expired();
    }

    public boolean isOnCooldown(UUID uuid, String key) {
        return !cooldownExpired(uuid, key);
    }

    public void startCooldown(UUID uuid, String key, long millis) {
        cooldowns.putIfAbsent(uuid, new HashMap<>());
        cooldowns.get(uuid).putIfAbsent(key, new DefaultExpiredCooldownTimer());
        cooldowns.get(uuid).get(key).start(millis);
    }

    public void clearUUID(UUID uuid) {
        cooldowns.remove(uuid);
    }

}
