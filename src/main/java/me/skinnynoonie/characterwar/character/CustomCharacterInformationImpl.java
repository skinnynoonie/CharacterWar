package me.skinnynoonie.characterwar.character;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CustomCharacterInformationImpl implements CustomCharacterInformation {

    private final String referenceName;
    private final String displayName;

    public CustomCharacterInformationImpl(@NotNull String referenceName, @NotNull String displayName) {
        Objects.requireNonNull(referenceName, "Parameter referenceName is null.");
        Objects.requireNonNull(displayName, "Parameter displayName is null.");
        if (!referenceName.matches("^[a-z-_0-9]+$")) {
            throw new IllegalStateException("Parameter referenceName ('" + referenceName + "') does not match: ^[a-z-_0-9]+$");
        }
        this.referenceName = referenceName;
        this.displayName = displayName;
    }

    @Override
    public @NotNull String getReferenceName() {
        return referenceName;
    }

    @Override
    public @NotNull String getDisplayName() {
        return displayName;
    }

    public static final class Builder {

        private String referenceName;
        private String displayName;

        public CustomCharacterInformationImpl build() {
            return new CustomCharacterInformationImpl(this.referenceName, this.displayName);
        }

        public Builder setReferenceName(String referenceName) {
            this.referenceName = referenceName;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

    }

}
