package me.skinnynoonie.characterwar.core.cooldown;

public interface CooldownTimer {

    boolean expired();

    void start(long millis);

}
