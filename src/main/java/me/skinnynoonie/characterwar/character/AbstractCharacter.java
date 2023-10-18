package me.skinnynoonie.characterwar.character;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractCharacter implements Character, Listener {

    @Override
    public String getReferenceName() {
        return getDisplayName().toLowerCase().replace(" ", "_");
    }

    public boolean allArmorIsEquipped(Player player) {
        ItemStack[] characterArmor = getArmor();
        ItemStack[] playerArmor = player.getInventory().getArmorContents();

        for (int i = 0; i < playerArmor.length; i++) {
            if (!itemIsSimilar(playerArmor[i], characterArmor[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@link org.bukkit.Material#AIR} and null is used interchangeably.
     */
    public boolean itemIsSimilar(ItemStack itemOne, ItemStack itemTwo) {
        if (itemOne == null) {
            itemOne = new ItemStack(Material.AIR);
        }
        if (itemTwo == null) {
            itemTwo = new ItemStack(Material.AIR);
        }

        if (itemOne.getType() != itemTwo.getType()) {
            return false;
        }
        boolean sameDisplayName = itemOne.displayName().equals(itemTwo.displayName());
        if (!sameDisplayName) {
            return false;
        }
        return true;
    }

}
