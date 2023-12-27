package me.skinnynoonie.characterwar.core.cooldown;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface CooldownManager {

    boolean cooldownExpired(@NotNull UUID uuid, @NotNull String key);

    boolean isOnCooldown(@NotNull UUID uuid, @NotNull String key);

    void startCooldown(@NotNull UUID uuid, @NotNull String key, long millis);

    void clearUUID(@NotNull UUID uuid);

}
