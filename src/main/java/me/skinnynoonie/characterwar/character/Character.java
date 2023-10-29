package me.skinnynoonie.characterwar.character;

import me.skinnynoonie.characterwar.item.CustomItemImpl;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Character {

    private final CustomItemImpl helmet;
    private final CustomItemImpl chestplate;
    private final CustomItemImpl leggings;
    private final CustomItemImpl boots;
    private final List<CustomItemImpl> items;

    public Character() {
        helmet = getHelmetImpl();
        chestplate = getChestplateImpl();
        leggings = getLeggingImpl();
        boots = getBootsImpl();
        items = getItemImpls();
    }

    public abstract @NotNull String getReferenceName();

    public abstract @NotNull String getDisplayName();

    public abstract @NotNull String getDescription();

    public abstract @NotNull CharacterVerse getVerse();

    public abstract @Nullable CustomItemImpl getHelmetImpl();

    public abstract @Nullable CustomItemImpl getChestplateImpl();

    public abstract @Nullable CustomItemImpl getLeggingImpl();

    public abstract @Nullable CustomItemImpl getBootsImpl();

    public abstract @NotNull List<@NotNull CustomItemImpl> getItemImpls();

    public @NotNull ItemStack getHelmet() {
        return helmet != null ? helmet.getItem() : new ItemStack(Material.AIR);
    }

    public @NotNull ItemStack getChestplate() {
        return chestplate != null ? chestplate.getItem() : new ItemStack(Material.AIR);
    }

    public @NotNull ItemStack getLeggings() {
        return leggings != null ? leggings.getItem() : new ItemStack(Material.AIR);
    }

    public @NotNull ItemStack getBoots() {
        return boots != null ? boots.getItem() : new ItemStack(Material.AIR);
    }

    public @NotNull List<@NotNull ItemStack> getItems() {
        return items.stream()
                .map(CustomItemImpl::getItem)
                .collect(Collectors.toList());
    }

}
