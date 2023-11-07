package me.skinnynoonie.characterwar.cooldown;

public class DefaultExpiredCooldownTimer {

    private long timeExpires;

    public DefaultExpiredCooldownTimer() {
        this.timeExpires = 0;
    }

    public boolean expired() {
        return System.currentTimeMillis() > this.timeExpires;
    }

    public void start(long millis) {
        this.timeExpires = System.currentTimeMillis() + millis;
    }

}
