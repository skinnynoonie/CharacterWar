package me.skinnynoonie.characterwar.core.item;

import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ItemBuilder {

    private final ItemStack itemToBuild;
    private final MiniMessage mm;

    public ItemBuilder(@NotNull ItemStack itemToBuild) {
        Preconditions.checkNotNull(itemToBuild, "itemToBuild is null.");
        this.itemToBuild = itemToBuild.clone();
        this.mm = MiniMessage.miniMessage();
    }

    public ItemBuilder(@NotNull Material itemToBuild) {
        this(new ItemStack(itemToBuild));
    }

    public ItemBuilder editMeta(Consumer<ItemMeta> meta) {
        return this.editMeta(ItemMeta.class, meta);
    }

    public <M extends ItemMeta> ItemBuilder editMeta(Class<M> metaType, Consumer<M> meta) {
        this.itemToBuild.editMeta(metaType, meta);
        return this;
    }

    public ItemBuilder setName(String name) {
        if (name == null) {
            return this.editMeta(meta -> meta.displayName(null));
        }
        Component componentText = this.mm.deserialize(name).decoration(TextDecoration.ITALIC, false);
        return this.editMeta(meta -> meta.displayName(componentText));
    }

    public ItemBuilder setLore(String... strings) {
        if (strings == null) {
            return this.editMeta(meta -> meta.lore(null));
        }
        List<Component> components = Arrays.stream(strings)
                .map(this.mm::deserialize)
                .map(c -> c.decoration(TextDecoration.ITALIC, false))
                .collect(Collectors.toList());
        return this.editMeta(meta -> meta.lore(components));
    }

    public ItemBuilder setLoreWithComponents(List<Component> components) {
        return this.editMeta(meta -> meta.lore(components));
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        return this.editMeta(meta -> meta.setUnbreakable(unbreakable));
    }

    public ItemBuilder setDurability(short durability) {
        return this.editMeta(Damageable.class, meta -> meta.setDamage(durability));
    }

    /**
     * Sets the durability using a percentage that is relative to the max durability of this item.
     * @param percentage This should be a value between 0 and 1.
     */
    public ItemBuilder setDurabilityPercentage(double percentage) {
        double percentageFixed = Math.min(1, Math.max(0, percentage));
        short durabilityFromPercentage = (short) (this.itemToBuild.getType().getMaxDurability() * percentage);
        return this.setDurability(durabilityFromPercentage);
    }

    public ItemBuilder setColor(Color color) {
        return this.editMeta(LeatherArmorMeta.class, (meta) -> meta.setColor(color));
    }

    public <T, V> ItemBuilder setPDCValue(NamespacedKey key, PersistentDataType<T, V> type, V value) {
        return this.editMeta(meta -> meta.getPersistentDataContainer().set(key, type, value));
    }

    public ItemBuilder setEnchant(Enchantment enchant, short tier) {
        this.itemToBuild.addUnsafeEnchantment(enchant, tier);
        return this;
    }

    public ItemStack build() {
        return this.itemToBuild.clone();
    }

}
