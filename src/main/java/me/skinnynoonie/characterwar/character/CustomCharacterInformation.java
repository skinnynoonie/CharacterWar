package me.skinnynoonie.characterwar.character;

import org.jetbrains.annotations.NotNull;

public interface CustomCharacterInformation {

    @NotNull String getReferenceName();

    @NotNull String getDisplayName();

    @NotNull CharacterVerse getVerse();

}
