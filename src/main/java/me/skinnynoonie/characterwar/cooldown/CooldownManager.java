package me.skinnynoonie.characterwar.cooldown;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private static final List<CooldownManager> cooldownManagers = new ArrayList<>();

    public static CooldownManager create() {
        CooldownManager cooldownManager = new CooldownManager();
        cooldownManagers.add(cooldownManager);
        return cooldownManager;
    }

    public static List<CooldownManager> getCooldownManagers() {
        return Collections.unmodifiableList(cooldownManagers);
    }

    public static void removeUuidFromAllManagers(UUID uuid) {
        for (CooldownManager cooldownManager : cooldownManagers) {
            cooldownManager.cooldowns.remove(uuid);
        }
    }

    private final Map<UUID, DefaultExpiredCooldownTimer> cooldowns = new HashMap<>();

    private CooldownManager() {
    }

    public boolean cooldownExpired(UUID uuid) {
        cooldowns.putIfAbsent(uuid, new DefaultExpiredCooldownTimer());
        return cooldowns.get(uuid).expired();
    }

    public boolean isOnCooldown(UUID uuid) {
        return !cooldownExpired(uuid);
    }

    public void startCooldown(UUID uuid, long millis) {
        cooldowns.putIfAbsent(uuid, new DefaultExpiredCooldownTimer());
        cooldowns.get(uuid).start(millis);
    }

}
