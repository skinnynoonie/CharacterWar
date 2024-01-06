package me.skinnynoonie.characterwar.core.test_items;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.skinnynoonie.characterwar.core.config.CustomItemConfig;
import me.skinnynoonie.characterwar.core.eventinfo.DamageEventInfo;
import me.skinnynoonie.characterwar.core.eventinfo.EventInfo;
import me.skinnynoonie.characterwar.core.item.CustomItemListener;
import me.skinnynoonie.characterwar.core.item.ItemBuilder;
import me.skinnynoonie.noonieconfigs.attribute.Config;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static org.bukkit.Bukkit.broadcastMessage;

@Config(name = "MyFirstCustomItemConfig")
public class MyCustomItemConfig extends CustomItemConfig {

    public MyCustomItemConfig() {
        super(new ItemBuilder(Material.DIAMOND_SWORD)
                .setName("<red>Cool sword")
                .setLore("<blue>hi <black>dark...", "<purple>lol")
                .setUnbreakable(true)
                .build()
        );
    }

    public static class Listener implements CustomItemListener {
        @Override
        public void onDamagedWhileWearing(EntityDamageEvent event, EventInfo eventInfo) {
            broadcastMessage("Hurt while wearing (global)");
        }

        @Override
        public void onDamagedWhileHolding(EntityDamageEvent event, EventInfo eventInfo) {
            broadcastMessage("Hurt while holding (global)");
        }

        @Override
        public void onDamagedByPlayerWhileWearing(EntityDamageByEntityEvent event, DamageEventInfo info) {
            broadcastMessage("Hurt while wearing (by another player)");
        }

        @Override
        public void onDamagedByPlayerWhileHolding(EntityDamageByEntityEvent event, DamageEventInfo info) {
            broadcastMessage("Hurt while holding (by another player)");
        }

        @Override
        public void onAttackPlayerWhileWearing(EntityDamageByEntityEvent event, DamageEventInfo info) {
            broadcastMessage("Attacked another player while wearing");
        }

        @Override
        public void onAttackPlayerWhileHolding(EntityDamageByEntityEvent event, DamageEventInfo info) {
            broadcastMessage("Attacked another player while holding");
        }

        @Override
        public void onArmorEquip(PlayerArmorChangeEvent event, EventInfo eventInfo) {
        }

        @Override
        public void onArmorUnEquip(PlayerArmorChangeEvent event, EventInfo eventInfo) {
        }

        @Override
        public void onPlayerLeave(PlayerQuitEvent event, EventInfo eventInfo) {
        }

        @Override
        public void onInteractWhileHolding(PlayerInteractEvent event, EventInfo eventInfo) {
        }
    }

}
