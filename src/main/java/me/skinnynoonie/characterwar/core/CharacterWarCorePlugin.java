package me.skinnynoonie.characterwar.core;

import me.skinnynoonie.characterwar.core.config.ConfigService;
import me.skinnynoonie.characterwar.core.config.CustomItemConfig;
import me.skinnynoonie.characterwar.core.config.file.FileConfigService;
import me.skinnynoonie.characterwar.core.cooldown.CooldownManager;
import me.skinnynoonie.characterwar.core.cooldown.manager.DefaultExpiredCooldownManager;
import me.skinnynoonie.characterwar.core.item.CustomItemManager;
import me.skinnynoonie.characterwar.core.listener.DamageListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CharacterWarCorePlugin extends JavaPlugin {

    private static CharacterWarCorePlugin plugin;

    public static CharacterWarCorePlugin getInstance() {
        return CharacterWarCorePlugin.plugin;
    }

    private ConfigService<CustomItemConfig> itemConfigService;
    private CustomItemManager customItemManager;
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        this.initiateInstances();
        this.registerListeners();
    }

    @Override
    public void onDisable() {
    }

    public ConfigService<CustomItemConfig> getItemConfigService() {
        return this.itemConfigService;
    }

    public CustomItemManager getCustomItemManager() {
        return this.customItemManager;
    }

    public CooldownManager getCooldownManager() {
        return this.cooldownManager;
    }

    private void initiateInstances() {
        CharacterWarCorePlugin.plugin = this;
        this.itemConfigService = new FileConfigService<>(super.getDataFolder().toPath().resolve("CustomItems"));
        this.customItemManager = new CustomItemManager(this, this.itemConfigService);
        this.cooldownManager = new DefaultExpiredCooldownManager();
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DamageListener(this.customItemManager), this);
    }

}
