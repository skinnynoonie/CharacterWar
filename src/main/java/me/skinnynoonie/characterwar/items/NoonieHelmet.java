package me.skinnynoonie.characterwar.items;

import me.skinnynoonie.characterwar.eventinfo.DamageEventInfo;
import me.skinnynoonie.characterwar.item.CustomItem;
import me.skinnynoonie.characterwar.item.CustomItemKey;
import me.skinnynoonie.characterwar.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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

    @Override
    public void onDamagedWhileWearing(EntityDamageEvent event) {
        event.setDamage(event.getDamage() * 0.10);
    }

    @Override
    public void onDamagedByPlayerWhileWearing(EntityDamageByEntityEvent event, DamageEventInfo info) {
        info.attacker().damage(5, event.getEntity());
    }

}
