package me.skinnynoonie.characterwar.character;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CustomCharacterInformationImpl implements CustomCharacterInformation {

    private final String referenceName;
    private final String displayName;
    private final CharacterVerse verse;

    public CustomCharacterInformationImpl(@NotNull String referenceName, @NotNull String displayName, CharacterVerse verse) {
        Objects.requireNonNull(referenceName, "Parameter referenceName is null.");
        if (!referenceName.matches("^[a-z-_0-9]+$")) {
            throw new IllegalStateException("Parameter referenceName ('" + referenceName + "') does not match: ^[a-z-_0-9]+$");
        }
        Objects.requireNonNull(displayName, "Parameter displayName is null.");
        Objects.requireNonNull(verse, "Parameter verse is null.");
        this.referenceName = referenceName;
        this.displayName = displayName;
        this.verse = verse;
    }

    @Override
    public @NotNull String getReferenceName() {
        return this.referenceName;
    }

    @Override
    public @NotNull String getDisplayName() {
        return this.displayName;
    }

    @Override
    public @NotNull CharacterVerse getVerse() {
        return this.verse;
    }

    public static final class Builder {

        private String referenceName;
        private String displayName;
        private CharacterVerse verse;

        public CustomCharacterInformationImpl build() {
            return new CustomCharacterInformationImpl(this.referenceName, this.displayName, this.verse);
        }

        public Builder setReferenceName(String referenceName) {
            this.referenceName = referenceName;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setVerse(CharacterVerse verse) {
            this.verse = verse;
            return this;
        }

    }

}
