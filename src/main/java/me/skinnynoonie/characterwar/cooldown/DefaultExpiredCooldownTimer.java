package me.skinnynoonie.characterwar.cooldown;

public class DefaultExpiredCooldownTimer {

    private long timeExpires = 0;

    public boolean expired() {
        return System.currentTimeMillis() > timeExpires;
    }

    public void start(long millis) {
        timeExpires = System.currentTimeMillis() + millis;
    }

}
