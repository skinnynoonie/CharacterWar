package me.skinnynoonie.characterwar.character;

import me.skinnynoonie.characterwar.item.ArmorEquipment;
import me.skinnynoonie.characterwar.item.CustomItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CustomCharacter {

    @NotNull CustomCharacterInformation getInformation();

    @NotNull ArmorEquipment<@NotNull CustomItem> getArmor();

    @NotNull List<@NotNull CustomItem> getItems();

}