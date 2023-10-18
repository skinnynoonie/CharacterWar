package me.skinnynoonie.characterwar.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder {

    private final ItemStack itemToBuild;
    private final MiniMessage mm = MiniMessage.miniMessage();

    public ItemBuilder(ItemStack itemToBuild) {
        this.itemToBuild = itemToBuild;
    }

    public ItemBuilder(Material itemToBuild) {
        this(new ItemStack(itemToBuild));
    }

    public ItemBuilder setName(String name) {
        Component componentText = mm.deserialize(name);
        itemToBuild.editMeta(meta -> meta.displayName(componentText));
        return this;
    }

    public ItemBuilder setLore(String... strings) {
        List<Component> components = Arrays.stream(strings).map(mm::deserialize).collect(Collectors.toList());
        itemToBuild.editMeta(meta -> meta.lore(components));
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        itemToBuild.editMeta(meta -> meta.setUnbreakable(unbreakable));
        return this;
    }

    public ItemBuilder setDurability(short durability) {
        itemToBuild.editMeta(Damageable.class, meta -> meta.setDamage(durability));
        return this;
    }

    /**
     * Sets the durability using a percentage that is relative to the max durability of this item.
     * @param percentage This should be a value between 0 and 1.
     */
    public ItemBuilder setDurabilityPercentage(double percentage) {
        double percentageFixed = Math.min(1, Math.max(0, percentage));
        short durabilityFromPercentage = (short) (itemToBuild.getType().getMaxDurability() * percentage);
        return setDurability(durabilityFromPercentage);
    }

    public ItemBuilder setColor(Color color) {
        itemToBuild.editMeta(LeatherArmorMeta.class, (meta) -> meta.setColor(color));
        return this;
    }

    public ItemStack build() {
        return itemToBuild;
    }

}
