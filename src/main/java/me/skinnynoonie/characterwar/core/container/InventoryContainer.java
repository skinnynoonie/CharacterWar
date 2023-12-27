package me.skinnynoonie.characterwar.core.container;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class InventoryContainer<T> {

    private final ArmorContainer<T> armorContainer;
    private final List<T> otherItems;

    @SuppressWarnings("ConstantConditions")
    public InventoryContainer(@NotNull ArmorContainer<T> armorContainer,
                              @NotNull List<@NotNull T> otherItems) {
        Preconditions.checkNotNull(armorContainer, "armorContainer is null.");
        Preconditions.checkNotNull(otherItems, "otherItems is null.");
        Preconditions.checkState(otherItems.stream().allMatch(Objects::nonNull), "otherItems has null elements.");
        this.armorContainer = armorContainer;
        this.otherItems = otherItems;
    }

    @NotNull
    public ArmorContainer<T> getArmorContainer() {
        return this.armorContainer;
    }

    @NotNull
    public List<@NotNull T> getOtherItems() {
        return Collections.unmodifiableList(this.otherItems);
    }

}
