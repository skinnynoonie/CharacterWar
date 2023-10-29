package me.skinnynoonie.characterwar.item.impls.superman;

import me.skinnynoonie.characterwar.item.CharacterPDCKey;
import me.skinnynoonie.characterwar.item.CustomItemImpl;
import me.skinnynoonie.characterwar.item.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class SupermanBoots implements CustomItemImpl {

    @Override
    public @NotNull ItemStack getItem() {
        NamespacedKey key = CharacterPDCKey.getKey();
        return new ItemBuilder(Material.LEATHER_BOOTS)
                .setName("<red>Superman's Boots")
                .setColor(Color.RED)
                .setUnbreakable(true)
                .editMeta(meta -> meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "superman_boots"))
                .build();
    }

    @Override
    public void onPlayerDamagedWithArmor(@NotNull EntityDamageEvent event, @NotNull Player victim) {
        event.setDamage(event.getDamage() * 0.90);
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setDamage(0);
        }
    }

}
