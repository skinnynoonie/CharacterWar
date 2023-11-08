package me.skinnynoonie.characterwar.item.items;

import me.skinnynoonie.characterwar.event.CustomItemEquippedEntityDamageEvent;
import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.item.CustomItemKey;
import me.skinnynoonie.characterwar.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class NoonieHelmet implements CustomItem {

    @Override
    public @NotNull ItemStack getItem() {
        return new ItemBuilder(Material.NETHERITE_HELMET)
                .setUnbreakable(true)
                .setName("<blue>Noonie's Helmet")
                .setPDCValue(CustomItemKey.key(), PersistentDataType.STRING, "noonie")
                .build();
    }

    @EventHandler
    public void onDamage(CustomItemEquippedEntityDamageEvent event) {
        if (!event.getReferenceNames().contains("noonie")) {
            return;
        }
        event.getEntityDamageEvent().setDamage(0);
    }

}
