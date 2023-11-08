package me.skinnynoonie.characterwar.character;

import me.skinnynoonie.characterwar.item.CustomArmorEquipment;
import me.skinnynoonie.characterwar.item.items.NoonieHelmet;

import java.util.Collections;

/*
Test class
 */
public class Characters {

    private Characters() {
    }

    public static CustomCharacter createNoonie() {
        return new CustomCharacterImpl.Builder()
                .setOtherItems(Collections.emptyList())
                .setCustomCharacterInformation(
                        new CustomCharacterInformationImpl.Builder()
                                .setDisplayName("Noonie")
                                .setReferenceName("noonie")
                                .setVerse(CharacterVerse.DC)
                                .build()
                )
                .setArmorEquipment(
                        new CustomArmorEquipment.Builder()
                                .setHelmet(new NoonieHelmet())
                                .build()
                )
                .build();
    }

}
