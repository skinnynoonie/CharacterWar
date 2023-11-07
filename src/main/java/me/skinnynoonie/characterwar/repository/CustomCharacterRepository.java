package me.skinnynoonie.characterwar.repository;

import me.skinnynoonie.characterwar.character.CustomCharacter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CustomCharacterRepository {

    /**
     * Register a character to this repository.
     * @param customCharacter This will register this character in a key value map, where the key is the reference name of this character.
     *                        All custom items this character holds will be registered in a {@link me.skinnynoonie.characterwar.repository.CustomItemRepository}.
     */
    void register(@NotNull CustomCharacter customCharacter);

    @Nullable CustomCharacter fromReferenceName(@NotNull String referenceName);

    @NotNull List<@NotNull CustomCharacter> getRegisteredCharacters();

    @NotNull CustomItemRepository getCustomItemRepository();

}
