package me.skinnynoonie.characterwar.item.items;

import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.item.CustomItemKey;
import me.skinnynoonie.characterwar.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

/*
just a test
 */
public class NoonieHelmet implements CustomItem {

    @Override
    public @NotNull ItemStack getItem() {
        return new ItemBuilder(Material.NETHERITE_HELMET)
                .setUnbreakable(true)
                .setName("<blue>Noonie's Helmet")
                .setPDCValue(CustomItemKey.key(), PersistentDataType.STRING, "noonie")
                .build();
    }

    /*
    Will add better events
     */
    @EventHandler
    public void onHurt(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }
        System.out.println(victim.getName());
    }

}
