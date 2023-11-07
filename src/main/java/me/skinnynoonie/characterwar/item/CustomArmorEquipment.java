package me.skinnynoonie.characterwar.item;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CustomArmorEquipment implements ArmorEquipment<CustomItem> {

    private final CustomItem helmet;
    private final CustomItem chestplate;
    private final CustomItem leggings;
    private final CustomItem boots;

    public CustomArmorEquipment(@NotNull CustomItem helmet,
                                @NotNull CustomItem chestplate,
                                @NotNull CustomItem leggings,
                                @NotNull CustomItem boots) {
        Objects.requireNonNull(helmet, "Parameter helmet is null.");
        Objects.requireNonNull(chestplate, "Parameter chestplate is null.");
        Objects.requireNonNull(leggings, "Parameter leggings is null.");
        Objects.requireNonNull(boots, "Parameter boots is null.");
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    @Override
    public @NotNull CustomItem getHelmet() {
        return this.helmet;
    }

    @Override
    public @NotNull CustomItem getChestplate() {
        return this.chestplate;
    }

    @Override
    public @NotNull CustomItem getLeggings() {
        return this.leggings;
    }

    @Override
    public @NotNull CustomItem getBoots() {
        return this.boots;
    }

    public static class Builder {

        private CustomItem helmet;
        private CustomItem chestplate;
        private CustomItem leggings;
        private CustomItem boots;

        public Builder() {
            this.helmet = CustomItem.AIR;
            this.chestplate = CustomItem.AIR;
            this.leggings = CustomItem.AIR;
            this.boots = CustomItem.AIR;
        }

        public CustomArmorEquipment build() {
            return new CustomArmorEquipment(this.helmet, this.chestplate, this.leggings, this.boots);
        }

        public Builder setHelmet(CustomItem helmet) {
            this.helmet = helmet;
            return this;
        }

        public Builder setChestplate(CustomItem chestplate) {
            this.chestplate = chestplate;
            return this;
        }

        public Builder setLeggings(CustomItem leggings) {
            this.leggings = leggings;
            return this;
        }

        public Builder setBoots(CustomItem boots) {
            this.boots = boots;
            return this;
        }

    }

}
