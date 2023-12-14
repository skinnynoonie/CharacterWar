package me.skinnynoonie.characterwar.cooldown;

public interface CooldownTimer {

    boolean expired();

    void start(long millis);

}
