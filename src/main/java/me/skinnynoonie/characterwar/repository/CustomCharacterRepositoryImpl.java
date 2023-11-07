package me.skinnynoonie.characterwar.repository;

import me.skinnynoonie.characterwar.character.CustomCharacter;
import me.skinnynoonie.characterwar.item.ArmorEquipment;
import me.skinnynoonie.characterwar.item.CustomItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomCharacterRepositoryImpl implements CustomCharacterRepository {

    private final CustomItemRepository customItemRepository;
    private final Map<String, CustomCharacter> referenceNameToCharacter;

    public CustomCharacterRepositoryImpl(@NotNull CustomItemRepository customItemRepository) {
        Objects.requireNonNull(customItemRepository, "Parameter customItemRepository is null.");
        this.customItemRepository = customItemRepository;
        this.referenceNameToCharacter = new HashMap<>();
    }

    @Override
    public void register(@NotNull CustomCharacter customCharacter) {
        Objects.requireNonNull(customCharacter, "Parameter customCharacter is null.");
        String referenceName = customCharacter.getInformation().getReferenceName();
        if (this.referenceNameToCharacter.containsKey(referenceName)) {
            throw new IllegalArgumentException("CustomCharacter reference name '" + referenceName + "' is already registered!");
        }
        this.referenceNameToCharacter.put(referenceName, customCharacter);
        ArmorEquipment<CustomItem> customArmor = customCharacter.getArmor();
        this.customItemRepository.registerAll(customCharacter.getItems());
        this.customItemRepository.register(customArmor.getHelmet());
        this.customItemRepository.register(customArmor.getChestplate());
        this.customItemRepository.register(customArmor.getLeggings());
        this.customItemRepository.register(customArmor.getBoots());
    }

    @Override
    public @Nullable CustomCharacter fromReferenceName(@NotNull String referenceName) {
        Objects.requireNonNull(referenceName, "Parameter referenceName is null.");
        return this.referenceNameToCharacter.get(referenceName);
    }

    @Override
    public @NotNull List<@NotNull CustomCharacter> getRegisteredCharacters() {
        return this.referenceNameToCharacter.values().stream().toList();
    }

    @Override
    public @NotNull CustomItemRepository getCustomItemRepository() {
        return this.customItemRepository;
    }

}
