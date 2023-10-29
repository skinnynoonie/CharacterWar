package me.skinnynoonie.characterwar.character.impls;

import me.skinnynoonie.characterwar.character.Character;
import me.skinnynoonie.characterwar.character.CharacterVerse;
import me.skinnynoonie.characterwar.item.CustomItemImpl;
import me.skinnynoonie.characterwar.item.impls.superman.SupermanBoots;
import me.skinnynoonie.characterwar.item.impls.superman.SupermanChestplate;
import me.skinnynoonie.characterwar.item.impls.superman.SupermanLeggings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class SupermanCharacter extends Character {

    @Override
    public @NotNull String getReferenceName() {
        return "superman";
    }

    @Override
    public @NotNull String getDisplayName() {
        return "Superman";
    }

    @Override
    public @NotNull String getDescription() {
        return "Invulnerability, incredible strength, the ability to leap incredible distances, and super speed.";
    }

    @Override
    public @NotNull CharacterVerse getVerse() {
        return CharacterVerse.DC;
    }

    @Override
    public @Nullable CustomItemImpl getHelmetImpl() {
        return null;
    }

    @Override
    public @Nullable CustomItemImpl getChestplateImpl() {
        return new SupermanChestplate();
    }

    @Override
    public @Nullable CustomItemImpl getLeggingImpl() {
        return new SupermanLeggings();
    }

    @Override
    public @Nullable CustomItemImpl getBootsImpl() {
        return new SupermanBoots();
    }

    @Override
    public @NotNull List<@NotNull CustomItemImpl> getItemImpls() {
        return Collections.emptyList();
    }

}
