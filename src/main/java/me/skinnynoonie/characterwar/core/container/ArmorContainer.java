package me.skinnynoonie.characterwar.core.container;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ArmorContainer<T> {

    private final T helmet;
    private final T chestplate;
    private final T leggings;
    private final T boots;

    public ArmorContainer(@NotNull T helmet,
                          @NotNull T chestplate,
                          @NotNull T leggings,
                          @NotNull T boots) {
        Preconditions.checkNotNull(helmet, "helmet is null.");
        Preconditions.checkNotNull(chestplate, "chestplate is null.");
        Preconditions.checkNotNull(leggings, "leggings is null.");
        Preconditions.checkNotNull(boots, "boots is null.");
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    @NotNull
    public T getHelmet() {
        return this.helmet;
    }

    @NotNull
    public T getChestplate() {
        return this.chestplate;
    }

    @NotNull
    public T getLeggings() {
        return this.leggings;
    }

    @NotNull
    public T getBoots() {
        return this.boots;
    }

    @NotNull
    public T getArmor(@NotNull ArmorEquipmentType armorType) {
        Objects.requireNonNull(armorType, "Parameter armorType is null.");
        return switch (armorType) {
            case HELMET -> this.getHelmet();
            case CHESTPLATE -> this.getChestplate();
            case LEGGINGS -> this.getLeggings();
            case BOOTS -> this.getBoots();
        };
    }

}
