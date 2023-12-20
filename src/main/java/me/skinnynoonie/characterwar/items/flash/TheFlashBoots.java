package me.skinnynoonie.characterwar.items.flash;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.skinnynoonie.characterwar.CharacterWarPlugin;
import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.item.CustomItemBuilder;
import me.skinnynoonie.characterwar.potion.PermanentPotionEffectManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class TheFlashBoots implements CustomItem {

    private final PermanentPotionEffectManager potionEffectManager;

    public TheFlashBoots() {
        this.potionEffectManager = new PermanentPotionEffectManager(CharacterWarPlugin.getInstance());
    }

    @Override
    public @NotNull ItemStack getItem() {
        return new CustomItemBuilder(Material.LEATHER_BOOTS, "the_flash_boots")
                .setName("<yellow>The Flash's Boots")
                .setLore("<gray>Grants <yellow>Speed V <gray>when equipped.")
                .setColor(Color.YELLOW)
                .build();
    }

    @Override
    public void onArmorEquip(PlayerArmorChangeEvent event) {
        this.potionEffectManager.applyEffectLoop(event.getPlayer().getUniqueId(), PotionEffectType.SPEED, 4);
    }

    @Override
    public void onArmorUnEquip(PlayerArmorChangeEvent event) {
        this.potionEffectManager.removeEffectLoop(event.getPlayer().getUniqueId(), PotionEffectType.SPEED);
    }

    @Override
    public void onPlayerLeave(PlayerQuitEvent event) {
        this.potionEffectManager.removeEffectLoop(event.getPlayer().getUniqueId(), PotionEffectType.SPEED);
    }
}
