/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.gui.basic;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.config.ItemBase;
import com.ignitedev.aparecium.config.LayerBase;
import com.ignitedev.aparecium.gui.AbstractLayout;
import com.ignitedev.aparecium.gui.AbstractLayoutLayer;
import com.ignitedev.aparecium.gui.layer.LayoutLayer;
import com.ignitedev.aparecium.gui.util.LayoutUtility;
import com.ignitedev.aparecium.item.basic.LayoutItem;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Nullable;

@Data
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Layout extends AbstractLayout {

  @Autowired // autowired fields HAS to be STATIC
  private static ItemBase itemBase;

  @Autowired // autowired fields HAS to be STATIC
  private static LayerBase layerBase;

  public Layout(
      String id,
      @Nullable ApareciumComponent layoutTitle,
      int layoutSize,
      InventoryType inventoryType,
      @Nullable LayoutLayer layoutBackgroundLayer,
      @Nullable Map<Integer, String> layers,
      Map<Integer, LayoutItem> content) {
    super(id, layoutTitle, layoutSize, inventoryType, layoutBackgroundLayer, layers, content);
  }

  public Layout(String id, int layoutSize) {
    super(id, layoutSize);
    this.id = id;
    this.layoutSize = layoutSize;
  }

  public Layout(String id, @Nullable ApareciumComponent layoutTitle, InventoryType inventoryType) {
    super(id, layoutTitle, inventoryType);
    this.id = id;
    this.layoutTitle = layoutTitle;
    this.inventoryType = inventoryType;
  }

  public Layout(AbstractLayoutBuilder<?, ?> builder) {
    super(builder);
  }

  @Override
  public Inventory createLayout(AbstractLayoutLayer... additionalLayers) {
    Inventory createdInventory = LayoutUtility.createProperInventory(this, this.layoutTitle);
    fill(createdInventory, true, false, additionalLayers);
    this.setCreatedInventoryInstance(createdInventory);

    return createdInventory;
  }

  /**
   * @implNote should be only used if you want to manually fill set inventory, otherwise use {{@link
   *     #createLayout(AbstractLayoutLayer...)}}
   * @param inventory set to be filled
   * @param fillBackground if true, then background layer will be pasted
   * @param force should we force layout if inventoryType doesn't match with layoutSize
   */
  @Override
  public void fill(
      Inventory inventory,
      boolean fillBackground,
      boolean force,
      AbstractLayoutLayer... additionalLayers) {
    if (!force) {
      if (inventory.getType() != this.inventoryType) {
        return;
      }
      if (this.inventoryType == InventoryType.CHEST && inventory.getSize() != this.layoutSize) {
        return;
      }
    }
    if (this.layers != null && !this.layers.isEmpty()) {
      for (int i = 0; i <= Collections.max(this.layers.keySet()); i++) {
        String layerId = this.layers.get(i);

        if (layerId != null) {
          layerBase.getById(layerId).fill(inventory, force);
        }
      }
    }
    for (AbstractLayoutLayer additionalLayer : additionalLayers) {
      additionalLayer.fill(inventory, force);
    }
    for (Entry<Integer, LayoutItem> entry : this.contents.entrySet()) {
      inventory.setItem(entry.getKey(), entry.getValue().toItemStack(1));
    }
    if (fillBackground) {
      fillBackground(inventory);
    }
  }

  @Override
  public void fillBackground(Inventory inventory) {
    if (this.layoutBackgroundLayer != null) {
      this.layoutBackgroundLayer.fill(inventory, true);
    }
  }
}
