package me.skinnynoonie.characterwar.character;

import me.skinnynoonie.characterwar.item.ArmorEquipment;
import me.skinnynoonie.characterwar.item.CustomItem;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CustomCharacterImpl implements CustomCharacter {

    private final CustomCharacterInformation customCharacterInformation;
    private final ArmorEquipment<CustomItem> armorEquipment;
    private final List<CustomItem> otherItems;

    public CustomCharacterImpl(@NotNull CustomCharacterInformation customCharacterInformation,
                               @NotNull ArmorEquipment<CustomItem> armorEquipment,
                               @NotNull List<@NotNull CustomItem> otherItems) {
        Objects.requireNonNull(customCharacterInformation, "Parameter customCharacterInformation is null.");
        Objects.requireNonNull(armorEquipment, "Parameter armorEquipment is null.");
        Objects.requireNonNull(otherItems, "Parameter otherItems is null.");
        if (otherItems.stream().anyMatch(Objects::isNull)) {
            throw new NullPointerException("Parameter otherItems has null elements.");
        }
        this.customCharacterInformation = customCharacterInformation;
        this.armorEquipment = armorEquipment;
        this.otherItems = otherItems;
    }

    @Override
    public @NotNull CustomCharacterInformation getInformation() {
        return this.customCharacterInformation;
    }

    @Override
    public @NotNull ArmorEquipment<@NotNull CustomItem> getArmor() {
        return this.armorEquipment;
    }

    @Override
    public @NotNull List<@NotNull CustomItem> getItems() {
        return Collections.unmodifiableList(this.otherItems);
    }

    public static class Builder {

        private CustomCharacterInformation customCharacterInformation;
        private ArmorEquipment<CustomItem> armorEquipment;
        private List<CustomItem> otherItems;

        public CustomCharacterImpl build() {
            return new CustomCharacterImpl(this.customCharacterInformation, this.armorEquipment, this.otherItems);
        }

        public Builder setCustomCharacterInformation(CustomCharacterInformation customCharacterInformation) {
            this.customCharacterInformation = customCharacterInformation;
            return this;
        }

        public Builder setArmorEquipment(ArmorEquipment<CustomItem> armorEquipment) {
            this.armorEquipment = armorEquipment;
            return this;
        }

        public Builder setOtherItems(List<CustomItem> otherItems) {
            this.otherItems = otherItems;
            return this;
        }

    }

}
