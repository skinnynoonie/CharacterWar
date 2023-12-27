package me.skinnynoonie.characterwar.core.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Config {

    /**
     * Represents the name of this config.
     * This name will be used for certain tasks depending on the implementation surrounding the annotated config.
     * Thus meaning that it is a good convention for config names to be unique.
     * @return The name of the config.
     */
    String name();

}
