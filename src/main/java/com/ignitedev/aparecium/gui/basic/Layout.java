package com.ignitedev.aparecium.gui.basic;

import com.ignitedev.aparecium.config.ItemBase;
import com.ignitedev.aparecium.config.LayerBase;
import com.ignitedev.aparecium.gui.AbstractLayout;
import com.ignitedev.aparecium.gui.layer.LayoutLayer;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import java.util.Collections;
import java.util.Map;
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

  @Autowired private static LayerBase layerBase;

  public Layout(
      String id,
      @Nullable String layoutTitle,
      int layoutSize,
      InventoryType inventoryType,
      @Nullable LayoutLayer layoutBackgroundLayer,
      @Nullable Map<Integer, String> layers,
      Map<Integer, String> content) {
    super(id, layoutTitle, layoutSize, inventoryType, layoutBackgroundLayer, layers, content);
  }

  public Layout(AbstractLayoutBuilder<?, ?> builder) {
    super(builder);
  }

  @Override
  public void fill(Inventory inventory, boolean fillBackground, boolean force) {
    if (!force) {
      if (inventory.getType() != this.inventoryType) {
        return;
      }
      if (this.inventoryType == InventoryType.CHEST && inventory.getSize() != this.layoutSize) {
        return;
      }
    }
    for (int i = 0; i <= Collections.max(this.layers.keySet()); i++) {
      String layerId = this.layers.get(i);

      if (layerId != null) {
        layerBase.getById(layerId).fill(inventory, force);
      }
    }
    this.content.forEach(
        (slot, itemId) -> inventory.setItem(slot, itemBase.getById(itemId).toItemStack(1)));
    fillBackground(inventory);
  }

  @Override
  public void fillBackground(Inventory inventory) {
    if (this.layoutBackgroundLayer != null) {
      this.layoutBackgroundLayer.fill(inventory, true);
    }
  }
}
