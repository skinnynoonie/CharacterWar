package me.skinnynoonie.characterwar.character;

import me.skinnynoonie.characterwar.item.armor.ArmorEquipment;
import me.skinnynoonie.characterwar.item.CustomItem;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CustomCharacter {

    private final CustomCharacterInformation customCharacterInformation;
    private final ArmorEquipment<CustomItem> armorEquipment;
    private final List<CustomItem> otherItems;

    @SuppressWarnings("ConstantConditions")
    public CustomCharacter(@NotNull CustomCharacterInformation customCharacterInformation,
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

    public @NotNull CustomCharacterInformation getInformation() {
        return this.customCharacterInformation;
    }

    public @NotNull ArmorEquipment<@NotNull CustomItem> getArmor() {
        return this.armorEquipment;
    }

    public @NotNull List<@NotNull CustomItem> getItems() {
        return Collections.unmodifiableList(this.otherItems);
    }

    public static class Builder {

        private CustomCharacterInformation customCharacterInformation;
        private ArmorEquipment<CustomItem> armorEquipment;
        private List<CustomItem> otherItems;

        public CustomCharacter build() {
            return new CustomCharacter(this.customCharacterInformation, this.armorEquipment, this.otherItems);
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
