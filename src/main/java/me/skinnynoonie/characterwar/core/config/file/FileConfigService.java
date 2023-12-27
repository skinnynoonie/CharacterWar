package me.skinnynoonie.characterwar.core.config.file;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import me.skinnynoonie.characterwar.core.config.Config;
import me.skinnynoonie.characterwar.core.config.ConfigService;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileConfigService<T> implements ConfigService<T> {

    private final Path configFolderPath;
    private final Map<Class<? extends T>, T> configMap;
    private final Gson gson;

    public FileConfigService(@NotNull Path configFolderPath, @NotNull Map<Type, Object> gsonSerializers) {
        Preconditions.checkNotNull(configFolderPath, "configFolderPath is null.");
        Preconditions.checkNotNull(gsonSerializers, "gsonSerializers is null.");
        this.configFolderPath = configFolderPath;
        this.configMap = new HashMap<>();
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping();
        gsonSerializers.forEach(gsonBuilder::registerTypeAdapter);
        this.gson = gsonBuilder.create();
    }

    public FileConfigService(@NotNull Path configFolderPath) {
        this(configFolderPath, Collections.emptyMap());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadAndRegister(@NotNull T config) throws IOException {
        Preconditions.checkNotNull(config, "config is null.");
        Class<? extends T> configClass = (Class<? extends T>) config.getClass();
        Preconditions.checkState(!this.configMap.containsKey(configClass), "Config" + configClass.getName() + " is already registered.");
        String configName = this.getNameAndValidateConfigClass(configClass);

        if (!this.isSaved(configClass)) {
            this.configMap.put(configClass, config);
            this.save(configClass);
            return;
        }

        Path pathToConfig = this.configFolderPath.resolve(configName + ".json");
        T configFromPath = this.gson.fromJson(Files.readString(pathToConfig), configClass);
        this.configMap.put(configClass, configFromPath);
        this.save((Class<? extends T>) configFromPath.getClass()); // Append any possible missing key value pairs.
    }

    @Override
    public void update(@NotNull Class<? extends T> configClass) throws IOException {
        Preconditions.checkState(this.configMap.containsKey(configClass), "configClass is not registered.");

        Path pathToConfig = this.configFolderPath.resolve(this.getConfigClassName(configClass) + ".json");
        T updatedConfig = this.gson.fromJson(Files.readString(pathToConfig), configClass);
        this.configMap.put(configClass, updatedConfig);
    }

    @Override
    @NotNull
    @SuppressWarnings("unchecked")
    public <C extends T> C get(@NotNull Class<C> configClass) {
        Preconditions.checkNotNull(configClass, "configClass is null.");
        if (!this.configMap.containsKey(configClass)) {
            throw new NoSuchElementException("configClass is not registered.");
        }

        return (C) this.configMap.get(configClass);
    }

    @Override
    @NotNull
    public List<@NotNull T> getRegistered() {
        return new ArrayList<>(this.configMap.values());
    }

    @Override
    public void save(@NotNull Class<? extends T> configClass) throws IOException {
        T config = this.get(configClass);

        if (Files.notExists(this.configFolderPath)) {
            Files.createDirectories(this.configFolderPath);
        }

        File configFile = this.configFolderPath.resolve(this.getConfigClassName(configClass) + ".json").toFile();
        PrintWriter printWriter = new PrintWriter(configFile);
        try {
            printWriter.print(this.gson.toJson(config));
        } catch (JsonParseException e) {
            throw new IOException(e);
        }
        printWriter.close();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveAll() throws IOException {
        for (T config : this.getRegistered()) {
            this.save((Class<? extends T>) config.getClass());
        }
    }

    @Override
    public boolean isSaved(Class<? extends T> configClass) {
        String configName = this.getConfigClassName(configClass);
        return configName != null && Files.exists(this.configFolderPath.resolve(configName + ".json"));
    }

    @Override
    public boolean isRegistered(Class<? extends T> configClass) {
        return this.configMap.containsKey(configClass);
    }

    @NotNull
    public Gson getGson() {
        return this.gson;
    }

    @NotNull
    public Path getConfigFolderPath() {
        return this.configFolderPath;
    }

    private String getNameAndValidateConfigClass(Class<?> configClass) {
        Preconditions.checkNotNull(configClass, "configClass is null.");

        String name = this.getConfigClassName(configClass);
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("name of configClass is not properly annotated.");
        }

        return name;
    }

    private String getConfigClassName(Class<?> configClass) {
        Config config = configClass.getAnnotation(Config.class);
        if (config == null) {
            return null;
        }
        return config.name();
    }

}
