/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.util;

import com.ignitedev.aparecium.util.text.TextUtility;
import java.util.List;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
@UtilityClass
public class InventoryUtility {

  /**
   * @implNote check if target inventory contains specified itemstack
   */
  public boolean inventoryContainsItemStack(Inventory inventory, ItemStack itemStack) {
    for (ItemStack content : inventory.getContents()) {
      if (content == null) {
        continue;
      }
      if (content.isSimilar(itemStack)) {
        return true;
      }
    }
    return false;
  }

  public boolean isSlotEmpty(Inventory inventory, int slot) {
    ItemStack item = inventory.getItem(slot);

    return item == null || item.getType().isAir();
  }

  public boolean hasPlace(int amount, Inventory inventory) {
    int slot = 1;

    for (ItemStack itemStack : inventory.getStorageContents()) {
      if (itemStack == null) {
        continue;
      }
      if (itemStack.getType() != Material.AIR) {
        slot++;
      }
    }
    double slotNeed = (double) amount / 64;
    int inventorySpace = (int) Math.ceil(slotNeed);
    return slot >= inventorySpace;
  }

  public int getFreeSlots(Inventory inventory) {
    int count = 0;

    for (int i = 0; i < inventory.getSize(); i++) {
      ItemStack item = inventory.getItem(i);

      if (item != null) {
        if (item.getType().isAir()) {
          count++;
        }
      }
    }
    return count;
  }

  public boolean compareInventories(InventoryView firstView, InventoryView secondView) {
    return compareInventories(firstView.title(), secondView.title());
  }

  public boolean compareInventories(String firstString, String secondString) {
    String firstViewTitle = TextUtility.removeColor(firstString);
    String secondViewTitle = TextUtility.removeColor(secondString);

    return firstViewTitle.equalsIgnoreCase(secondViewTitle);
  }

  public boolean compareInventories(Component firstComponent, Component secondComponent) {
    List<Component> firstViewTitle = TextUtility.removeColorComponent(List.of(firstComponent));
    List<Component> secondViewTitle = TextUtility.removeColorComponent(List.of(secondComponent));

    return firstViewTitle.equals(secondViewTitle);
  }
}
