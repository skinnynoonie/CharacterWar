package me.skinnynoonie.characterwar.core;

import me.skinnynoonie.characterwar.core.command.GetCustomItemCommand;
import me.skinnynoonie.characterwar.core.cooldown.CooldownManager;
import me.skinnynoonie.characterwar.core.cooldown.manager.DefaultExpiredCooldownManager;
import me.skinnynoonie.characterwar.core.item.CustomItemManager;
import me.skinnynoonie.characterwar.core.listener.DamageListener;
import me.skinnynoonie.characterwar.core.test_items.MyCustomItemConfig;
import me.skinnynoonie.noonieconfigs.DefaultConfigServices;
import me.skinnynoonie.noonieconfigs.dao.JsonFileConfigRepository;
import org.bukkit.Bukkit;
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

        this.customItemManager = new CustomItemManager(
                this,
                DefaultConfigServices.createJsonConfigService(this.getDataFolder().toPath())
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
