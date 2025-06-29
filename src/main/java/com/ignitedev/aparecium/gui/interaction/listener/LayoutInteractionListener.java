/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.gui.interaction.listener;

import com.ignitedev.aparecium.gui.basic.Layout;
import com.ignitedev.aparecium.gui.repository.InventoriesRepository;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class LayoutInteractionListener implements Listener {

  @EventHandler
  public void onDrag(InventoryDragEvent event) {
    Layout byInventory = InventoriesRepository.getInstance().findByInventory(event.getInventory());

    if (byInventory == null) {
      return;
    }
    if (!byInventory.getLayoutInteractions().isInventoryDrag()) {
      event.setResult(Result.DENY);
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onEnterItems(InventoryMoveItemEvent event) {
    InventoriesRepository instance = InventoriesRepository.getInstance();

    Layout byInventoryDestination = instance.findByInventory(event.getDestination());
    Layout byInventorySource = instance.findByInventory(event.getSource());

    if (byInventoryDestination != null) {
      if (!byInventoryDestination.getLayoutInteractions().isInventoryMoveItem()) {
        event.setCancelled(true);
      }
    }
    if (byInventorySource != null) {
      if (!byInventorySource.getLayoutInteractions().isInventoryMoveItem()) {
        event.setCancelled(true);
      }
    }
  }

  @EventHandler
  public void onClick(InventoryClickEvent event) {
    Layout byInventory = InventoriesRepository.getInstance().findByInventory(event.getInventory());

    if (byInventory == null) {
      return;
    }
    if (!byInventory.getLayoutInteractions().isInventoryClick()) {
      event.setResult(Result.DENY);
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onClose(InventoryCloseEvent event) {
    Layout byInventory = InventoriesRepository.getInstance().findByInventory(event.getInventory());

    if (byInventory == null) {
      return;
    }
    if (!byInventory.getLayoutInteractions().isInventoryClose()) {
      event.getPlayer().openInventory(byInventory.getInventory());
    }
  }
}
