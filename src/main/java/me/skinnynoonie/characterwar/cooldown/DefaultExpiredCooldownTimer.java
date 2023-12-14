package me.skinnynoonie.characterwar.cooldown;

public class DefaultExpiredCooldownTimer implements CooldownTimer {

    private long timeExpires;

    public DefaultExpiredCooldownTimer() {
        this.timeExpires = 0;
    }

    @Override
    public boolean expired() {
        return System.currentTimeMillis() > this.timeExpires;
    }

    @Override
    public void start(long millis) {
        this.timeExpires = System.currentTimeMillis() + millis;
    }

}
