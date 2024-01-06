package me.skinnynoonie.characterwar.core.config;

import com.google.common.base.Preconditions;
import me.skinnynoonie.noonieconfigs.service.ConfigService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.NotSerializableException;
import java.util.HashMap;
import java.util.Optional;

public final class ConfigManager<T> {

    private final ConfigService<T> configService;
    private final HashMap<Class<? extends T>, T> cache;

    public ConfigManager(@NotNull ConfigService<T> configService) {
        Preconditions.checkNotNull(configService, "Parameter configService is null.");

        this.configService = configService;
        this.cache = new HashMap<>();
    }

    public void initialize() throws IOException {
        this.configService.initialize();
    }

    public void loadAndRegister(@NotNull T fallback) throws IOException {
        Preconditions.checkNotNull(fallback, "Parameter fallback is null.");

        Class<? extends T> fallbackConfigClass = (Class<? extends T>) fallback.getClass();
        this.cache.put(fallbackConfigClass, this.configService.loadWithFallback(fallback));
        this.configService.save(this.cache.get(fallbackConfigClass));
    }

    public <C extends T> Optional<C> get(@Nullable Class<C> configClass) {
        return (Optional<C>) this.cache.get(configClass);
    }

    public void save(Class<? extends T> configClass) throws IOException {
        Preconditions.checkNotNull(configClass, "Parameter configClass is null.");

        if (!this.cache.containsKey(configClass)) {
            throw new NotSerializableException(
                    "Cannot save config class %s, it is not saved in the cache.".formatted(configClass.getName())
            );
        }
        this.configService.save(this.cache.get(configClass));
    }

}
