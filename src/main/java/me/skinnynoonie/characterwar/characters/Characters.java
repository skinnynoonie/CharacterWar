package me.skinnynoonie.characterwar.characters;

import me.skinnynoonie.characterwar.character.CharacterVerse;
import me.skinnynoonie.characterwar.character.CustomCharacter;
import me.skinnynoonie.characterwar.character.CustomCharacterInformation;
import me.skinnynoonie.characterwar.item.armor.CustomArmorEquipment;
import me.skinnynoonie.characterwar.items.NoonieHelmet;

import java.util.Collections;

/*
Test class
 */
public class Characters {

    private Characters() {
    }

    public static CustomCharacter createNoonie() {
        return new CustomCharacter.Builder()
                .setOtherItems(Collections.emptyList())
                .setCustomCharacterInformation(
                        new CustomCharacterInformation.Builder()
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
