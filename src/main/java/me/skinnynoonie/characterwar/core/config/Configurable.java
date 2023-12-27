package me.skinnynoonie.characterwar.core.config;

public interface Configurable {

    /**
     * Checks if the current state of this configuration is correctly configured. This means that all the values are valid, possible, allowed, etc.
     * @return true if this configuration's state is valid.
     */
    boolean isValid();

}
