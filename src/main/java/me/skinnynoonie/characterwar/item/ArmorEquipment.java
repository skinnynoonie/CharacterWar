package me.skinnynoonie.characterwar.item;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface ArmorEquipment<T> {

    @NotNull T getHelmet();

    @NotNull T getChestplate();

    @NotNull T getLeggings();

    @NotNull T getBoots();

    @NotNull
    default T getArmor(@NotNull ArmorEquipmentType armorType) {
        Objects.requireNonNull(armorType, "Parameter armorType is null.");
        return switch (armorType) {
            case HELMET -> getHelmet();
            case CHESTPLATE -> getChestplate();
            case LEGGINGS -> getLeggings();
            case BOOTS -> getBoots();
        };
    }

}
