package me.skinnynoonie.characterwar.characters;

import me.skinnynoonie.characterwar.character.CharacterVerse;
import me.skinnynoonie.characterwar.character.CustomCharacter;
import me.skinnynoonie.characterwar.character.CustomCharacterInformation;
import me.skinnynoonie.characterwar.item.armor.CustomArmorEquipment;
import me.skinnynoonie.characterwar.items.flash.TheFlashHelmet;

import java.util.Collections;

public class FlashCharacter extends CustomCharacter {
    public FlashCharacter() {
        super(
                new CustomCharacterInformation.Builder()
                        .setDisplayName("The Flash")
                        .setReferenceName("the_flash")
                        .setVerse(CharacterVerse.DC)
                        .build(),
                new CustomArmorEquipment.Builder()
                        .setHelmet(new TheFlashHelmet())
                        .build(),
                Collections.emptyList()
        );
    }
}
