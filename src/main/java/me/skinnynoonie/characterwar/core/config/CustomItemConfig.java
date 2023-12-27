package me.skinnynoonie.characterwar.core.config;

import com.google.common.base.Preconditions;
import me.skinnynoonie.characterwar.core.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public abstract class CustomItemConfig implements Configurable {

    protected final Material material;
    protected final String name;
    protected final List<String> lore;
    protected final boolean unbreakable;

    protected CustomItemConfig(Material material,
                               String name,
                               List<String> lore,
                               boolean unbreakable) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.unbreakable = unbreakable;
    }

    @Nullable
    public ItemStack buildItem(@NotNull NamespacedKey referenceNameKey, @NotNull String referenceName) {
        Preconditions.checkNotNull(referenceNameKey, "referenceNameKey is null.");
        Preconditions.checkNotNull(referenceName, "referenceName is null.");
        if (!this.isValid()) {
            return null;
        }
        return new ItemBuilder(this.material)
                .setName(this.name)
                .setLore(this.lore.toArray(String[]::new))
                .setUnbreakable(this.unbreakable)
                .setPDCValue(referenceNameKey, PersistentDataType.STRING, referenceName)
                .build();
    }

    @Override
    public boolean isValid() {
        return this.material != null;
    }

    public Material getMaterial() {
        return this.material;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getLore() {
        return Collections.unmodifiableList(this.lore);
    }

    public boolean isUnbreakable() {
        return this.unbreakable;
    }

}
