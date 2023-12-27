package me.skinnynoonie.characterwar.core.cooldown.manager;

import me.skinnynoonie.characterwar.core.cooldown.CooldownManager;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DefaultExpiredCooldownManagerTest {

    private static final UUID PLAYER_ONE = UUID.randomUUID();
    private static final UUID PLAYER_TWO = UUID.randomUUID();
    private static final UUID UNREGISTERED_PLAYER = UUID.randomUUID();

    @Test
    void testIfCooldownsWork_differentKeys() throws InterruptedException {
        CooldownManager cooldownManager = new DefaultExpiredCooldownManager();
        cooldownManager.startCooldown(PLAYER_ONE, "playerOneKey", 500);
        cooldownManager.startCooldown(PLAYER_TWO, "playerTwoKey", 250);

        assertTrue(cooldownManager.isOnCooldown(PLAYER_ONE, "playerOneKey"));
        assertFalse(cooldownManager.cooldownExpired(PLAYER_ONE, "playerOneKey"));
        assertFalse(cooldownManager.isOnCooldown(PLAYER_ONE, "playerTwoKey"));
        assertTrue(cooldownManager.cooldownExpired(PLAYER_ONE, "playerTwoKey"));

        assertTrue(cooldownManager.isOnCooldown(PLAYER_TWO, "playerTwoKey"));
        assertFalse(cooldownManager.cooldownExpired(PLAYER_TWO, "playerTwoKey"));
        assertFalse(cooldownManager.isOnCooldown(PLAYER_TWO, "playerOneKey"));
        assertTrue(cooldownManager.cooldownExpired(PLAYER_TWO, "playerOneKey"));

        assertFalse(cooldownManager.isOnCooldown(UNREGISTERED_PLAYER, "playerOneKey"));
        assertFalse(cooldownManager.isOnCooldown(UNREGISTERED_PLAYER, "playerTwoKey"));
        assertTrue(cooldownManager.cooldownExpired(UNREGISTERED_PLAYER, "playerOneKey"));
        assertTrue(cooldownManager.cooldownExpired(UNREGISTERED_PLAYER, "playerTwoKey"));

        Thread.sleep(250);

        assertTrue(cooldownManager.isOnCooldown(PLAYER_ONE, "playerOneKey"));
        assertFalse(cooldownManager.cooldownExpired(PLAYER_ONE, "playerOneKey"));
        assertFalse(cooldownManager.isOnCooldown(PLAYER_ONE, "playerTwoKey"));
        assertTrue(cooldownManager.cooldownExpired(PLAYER_ONE, "playerTwoKey"));

        assertFalse(cooldownManager.isOnCooldown(PLAYER_TWO, "playerTwoKey"));
        assertTrue(cooldownManager.cooldownExpired(PLAYER_TWO, "playerTwoKey"));
        assertFalse(cooldownManager.isOnCooldown(PLAYER_TWO, "playerOneKey"));
        assertTrue(cooldownManager.cooldownExpired(PLAYER_TWO, "playerOneKey"));

        assertFalse(cooldownManager.isOnCooldown(UNREGISTERED_PLAYER, "playerOneKey"));
        assertFalse(cooldownManager.isOnCooldown(UNREGISTERED_PLAYER, "playerTwoKey"));
        assertTrue(cooldownManager.cooldownExpired(UNREGISTERED_PLAYER, "playerOneKey"));
        assertTrue(cooldownManager.cooldownExpired(UNREGISTERED_PLAYER, "playerTwoKey"));
    }

    @Test
    void clearUUID() {
        CooldownManager cooldownManager = new DefaultExpiredCooldownManager();
        cooldownManager.startCooldown(PLAYER_ONE, "playerOneKey", 500);
        cooldownManager.clearUUID(PLAYER_ONE);
        assertFalse(cooldownManager.isOnCooldown(PLAYER_ONE, "playerOneKey"));
        assertTrue(cooldownManager.cooldownExpired(PLAYER_ONE, "playerOneKey"));
    }

}