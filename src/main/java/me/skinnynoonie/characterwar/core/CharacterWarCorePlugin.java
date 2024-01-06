package me.skinnynoonie.characterwar.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.skinnynoonie.characterwar.core.command.GetCustomItemCommand;
import me.skinnynoonie.characterwar.core.config.CustomItemConfig;
import me.skinnynoonie.characterwar.core.cooldown.CooldownManager;
import me.skinnynoonie.characterwar.core.cooldown.manager.DefaultExpiredCooldownManager;
import me.skinnynoonie.characterwar.core.item.CustomItemManager;
import me.skinnynoonie.characterwar.core.item.ItemAdapter;
import me.skinnynoonie.characterwar.core.listener.DamageListener;
import me.skinnynoonie.characterwar.core.test_items.MyCustomItemConfig;
import me.skinnynoonie.noonieconfigs.DefaultConfigServices;
import me.skinnynoonie.noonieconfigs.converter.JsonFormConverter;
import me.skinnynoonie.noonieconfigs.dao.JsonFileConfigRepository;
import me.skinnynoonie.noonieconfigs.fallback.JsonFallbackValueProvider;
import me.skinnynoonie.noonieconfigs.service.BasicConfigService;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class CharacterWarCorePlugin extends JavaPlugin {

    private static CharacterWarCorePlugin plugin;

    public static CharacterWarCorePlugin getInstance() {
        return CharacterWarCorePlugin.plugin;
    }

    private CooldownManager cooldownManager;
    private CustomItemManager customItemManager;

    @Override
    public void onEnable() {
        try {
            this.initiateInstances();
            this.registerListeners();
            this.registerCommands();
            this.customItemManager.registerCustomItem("my_custom_item", new MyCustomItemConfig(), new MyCustomItemConfig.Listener());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
    }

    @NotNull
    public CooldownManager getCooldownManager() {
        return this.cooldownManager;
    }

    @NotNull
    public CustomItemManager getCustomItemManager() {
        return this.customItemManager;
    }

    private void initiateInstances() throws IOException {
        CharacterWarCorePlugin.plugin = this;
        this.cooldownManager = new DefaultExpiredCooldownManager();
        this.initializeCustomItemManager();
    }

    private void initializeCustomItemManager() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ItemStack.class, new ItemAdapter())
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .serializeNulls()
                .create();
        this.customItemManager = new CustomItemManager(
                this,
                new BasicConfigService<>(
                        JsonFileConfigRepository.newInstance(this.getDataFolder().toPath(), gson),
                        new JsonFallbackValueProvider(),
                        new JsonFormConverter(gson)
                )
        );
        this.customItemManager.initialize();
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DamageListener(this.customItemManager), this);
    }

    private void registerCommands() {
        super.getCommand("giveitem").setExecutor(new GetCustomItemCommand());
    }

}
