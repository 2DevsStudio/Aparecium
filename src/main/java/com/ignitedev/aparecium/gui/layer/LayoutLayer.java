/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.gui.layer;

import com.ignitedev.aparecium.config.wrapper.LayerWrapper;
import com.ignitedev.aparecium.config.wrapper.MagicItemWrapper;
import com.ignitedev.aparecium.gui.AbstractLayoutLayer;
import com.ignitedev.aparecium.item.MagicItem;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

@Data
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LayoutLayer extends AbstractLayoutLayer {

  public LayoutLayer(AbstractLayoutLayerBuilder<?, ?> builder) {
    super(builder);
  }

  public LayoutLayer(
      String id,
      Map<Integer, MagicItemWrapper> content,
      InventoryType layoutInventoryType,
      int layoutSize) {
    super(id, content, layoutInventoryType, layoutSize);
  }

  public LayerWrapper toWrapper(){
    return new LayerWrapper(this.id, this);
  }

  @Override
  public void fill(Inventory inventory, boolean force) {
    if (!force) {
      if (inventory.getType() != this.layoutInventoryType) {
        return;
      }
      if (this.layoutInventoryType == InventoryType.CHEST
          && inventory.getSize() != this.layoutSize) {
        return;
      }
    }
    this.contents.forEach(
        (slot, magicItemID) -> {
          MagicItem availableMagicItem = magicItemID.getAvailableMagicItem();

          if (availableMagicItem != null) {
            inventory.setItem(slot, availableMagicItem.toItemStack());
          }
        });
  }
}
