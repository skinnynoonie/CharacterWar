package me.skinnynoonie.characterwar.core.config;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface ConfigService<T> {

    /**
     * Loads and registers a configuration.
     * If this is the first time loading a configuration object, then extra steps may be executed.
     * @param config The configuration object which must be registered and annotated properly.
     *               This config will be used as the default config if it is not saved in the database.
     * @throws IOException If an I/O exception occurs.
     * @throws IllegalStateException If the config is already registered, or if the class is not annotated properly.
     */
    void loadAndRegister(@NotNull T config) throws IOException;

    /**
     * Gets the contents from the configuration storage again, essentially updating the current cached config.
     * @param configClass The configuration class reference.
     * @throws IOException If an I/O exception occurs.
     * @throws IllegalStateException If the configuration class is not registered in this service's cache.
     */
    void update(@NotNull Class<? extends T> configClass) throws IOException;

    /**
     * Gets a configuration using this service, which will be a reference rather than a new instance.
     * @param configClass The configuration class which must be registered.
     * @return The configuration class reference.
     * @throws NoSuchElementException If the configClass is not registered.
     * @apiNote This method will not throw any exception if configClass is not properly annotated.
     */
    @NotNull
    <C extends T> C get(@NotNull Class<C> configClass);

    /**
     * Gets a configuration using this service, which will be a reference rather than a new instance.
     * Instead of throwing an exception, this method will return an Optional.
     * This optional will be empty due to the most likely case of a configuration never being registered.
     * @param configClass The configuration class reference
     * @return The configuration if it exists within this service's cache.
     */
    default <C extends T> Optional<C> findByClass(@NotNull Class<C> configClass) {
        try {
            return Optional.of(this.get(configClass));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    /**
     * Gets all the configurations currently cached in this service.
     * @return All configurations currently being stored.
     */
    @NotNull
    List<@NotNull T> getRegistered();

    /**
     * Saves a configuration using this service, which will save the instance of this config stored.
     * @param configClass The configuration class which must be registered.
     * @throws IOException If an I/O exception occurs.
     * @throws NoSuchElementException If the configClass is not registered.
     * @apiNote This method will not throw any exception if configClass is not properly annotated.
     */
    void save(@NotNull Class<? extends T> configClass) throws IOException;

    /**
     * Saves all configurations that are registered within this service.
     * @throws IOException If an I/O exception occurs.
     */
    void saveAll() throws IOException;

    /**
     * This method will check if the configClass is saved within this service's non-volatile storage.
     * This method may or may not check if the configClass is corrupted in this service's storage.
     * @param configClass The configuration class which will be used to check the status.
     * @return true if the configClass is saved somewhere within this service's storage.
     * @throws IOException If an I/O exception occurs.
     * @apiNote This method will not throw any exception if configClass is not properly annotated.
     *          This method is not the same as {@link #isRegistered(Class)}.
     */
    boolean isSaved(Class<? extends T> configClass) throws IOException;
    
    /**
     * This method will check if an instance of this config is registered.
     * @param configClass The configuration class used to check the registration status.
     * @return true if the configClass is registered within this service.
     * @apiNote This method will not throw any exception if configClass is not properly annotated.
     *          This method is not the same as {@link #isSaved(Class)}.
     */
    boolean isRegistered(Class<? extends T> configClass);

}
