/*
 * Copyright (c) 2022-2023. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.gui.util;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.gui.AbstractLayout;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class LayoutUtility {

  public Inventory createProperInventory(
      AbstractLayout abstractLayout, @Nullable ApareciumComponent layoutTitle) {
    String string = null;
    Component component = null;

    if (layoutTitle != null) {
      string = layoutTitle.getAsString();
      component = layoutTitle.getAsComponent();
    }
    InventoryType inventoryType = abstractLayout.getInventoryType();
    int layoutSize = abstractLayout.getLayoutSize();

    if (inventoryType == null) {
      inventoryType = InventoryType.CHEST;
    }
    if (layoutSize == 0) {
      layoutSize = 27;
    }
    if (inventoryType == InventoryType.CHEST) {
      // PAPER CODE
      if (Aparecium.isUsingPaper()) {
        if (component != null) {
          return Bukkit.createInventory(abstractLayout, layoutSize, component);
        }
      }
      // END OF PAPER CODE
      return Bukkit.createInventory(abstractLayout, layoutSize, string != null ? string : "");
    } else {
      // PAPER CODE
      if (Aparecium.isUsingPaper()) {
        if (component != null) {
          return Bukkit.createInventory(abstractLayout, inventoryType, component);
        }
      }
      // END OF PAPER CODE
      return Bukkit.createInventory(abstractLayout, inventoryType, string != null ? string : "");
    }
  }
}
