/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
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

/**
 * Utility class providing helper methods for inventory manipulation and checks.
 */
@SuppressWarnings("unused")
@UtilityClass
public class InventoryUtility {

  /**
   * Checks if the target inventory contains the specified item stack.
   *
   * @param inventory The inventory to check.
   * @param itemStack The item stack to look for.
   * @return True if the inventory contains the item stack, false otherwise.
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

  /**
   * Checks if a specific slot in the inventory is empty.
   *
   * @param inventory The inventory to check.
   * @param slot The slot index to check.
   * @return True if the slot is empty, false otherwise.
   */
  public boolean isSlotEmpty(Inventory inventory, int slot) {
    ItemStack item = inventory.getItem(slot);

    return item == null || item.getType().isAir();
  }

  /**
   * Checks if the inventory has enough space for a specified amount of items.
   *
   * @param amount The amount of items to check for.
   * @param inventory The inventory to check.
   * @return True if the inventory has enough space, false otherwise.
   */
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

  /**
   * Gets the number of free slots in the inventory.
   *
   * @param inventory The inventory to check.
   * @return The number of free slots in the inventory.
   */
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

  /**
   * Compares the titles of two inventory views to check if they are identical.
   *
   * @param firstView The first inventory view.
   * @param secondView The second inventory view.
   * @return True if the titles are identical, false otherwise.
   */
  public boolean compareInventories(InventoryView firstView, InventoryView secondView) {
    return compareInventories(firstView.title(), secondView.title());
  }

  /**
   * Compares two inventory titles as strings to check if they are identical.
   *
   * @param firstString The title of the first inventory.
   * @param secondString The title of the second inventory.
   * @return True if the titles are identical, false otherwise.
   */
  public boolean compareInventories(String firstString, String secondString) {
    String firstViewTitle = TextUtility.removeColor(firstString);
    String secondViewTitle = TextUtility.removeColor(secondString);

    return firstViewTitle.equalsIgnoreCase(secondViewTitle);
  }

  /**
   * Compares two inventory titles as components to check if they are identical.
   *
   * @param firstComponent The title of the first inventory as a component.
   * @param secondComponent The title of the second inventory as a component.
   * @return True if the titles are identical, false otherwise.
   */
  public boolean compareInventories(Component firstComponent, Component secondComponent) {
    List<Component> firstViewTitle = TextUtility.removeColorComponent(List.of(firstComponent));
    List<Component> secondViewTitle = TextUtility.removeColorComponent(List.of(secondComponent));

    return firstViewTitle.equals(secondViewTitle);
  }
}