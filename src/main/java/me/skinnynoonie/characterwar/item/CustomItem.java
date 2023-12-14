package me.skinnynoonie.characterwar.item;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface CustomItem extends CustomItemListener {

    CustomItem AIR = new CustomItem() {
        @Override
        public @NotNull ItemStack getItem() {
            return new ItemStack(Material.AIR);
        }

        @Override
        public boolean isAir() {
            return true;
        }
    };

    /**
     * ItemStacks that are returned by this method should have {@link CustomItemKey#key()} in the PDC.
     * This key should return the reference name of this ItemStack as a {@link java.lang.String}.
     */
    @NotNull ItemStack getItem();

    default boolean isAir() {
        return false;
    }

}
