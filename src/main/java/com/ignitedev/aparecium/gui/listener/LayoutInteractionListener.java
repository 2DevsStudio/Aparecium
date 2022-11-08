/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.gui.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class LayoutInteractionListener implements Listener {

  @EventHandler
  public void onDrag(InventoryDragEvent event) {}

  @EventHandler
  public void onEnterItems(InventoryMoveItemEvent event) {}

  @EventHandler
  public void onClick(InventoryClickEvent event) {}

  @EventHandler
  public void onClose(InventoryCloseEvent event) {}
}
