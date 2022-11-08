package com.ignitedev.aparecium.gui.basic;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.config.ItemBase;
import com.ignitedev.aparecium.config.LayerBase;
import com.ignitedev.aparecium.gui.AbstractLayout;
import com.ignitedev.aparecium.gui.layer.LayoutLayer;
import com.ignitedev.aparecium.item.basic.LayoutItem;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import java.util.Collections;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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
  public Inventory createLayout() {
    Inventory createdInventory;

    if (this.layoutTitle != null) {
      // PAPER CODE
      if (Aparecium.isUsingPaper()) {
        Component asComponent = this.layoutTitle.getAsComponent();

        if (asComponent != null) {
          if (this.inventoryType == InventoryType.CHEST) {
            createdInventory = Bukkit.createInventory(null, this.layoutSize, asComponent);
          } else {
            createdInventory = Bukkit.createInventory(null, this.inventoryType, asComponent);
          }
          fill(createdInventory, true, false);

          return createdInventory;
        }
      }
      // END OF PAPER CODE
    }
    createdInventory = createProperInventory(null, this.layoutTitle.getAsString());
    fill(createdInventory, true, false);

    return createdInventory;
  }

  private Inventory createProperInventory(@Nullable Component component, @Nullable String string) {
    Inventory createdInventory;

    if (this.inventoryType == InventoryType.CHEST) {
      if (component != null) {
        createdInventory = Bukkit.createInventory(null, this.layoutSize, component);
      } else {
        createdInventory =
            Bukkit.createInventory(null, this.layoutSize, string != null ? string : "");
      }
    } else {
      if (component != null) {
        createdInventory = Bukkit.createInventory(null, this.inventoryType, component);
      } else {
        createdInventory =
            Bukkit.createInventory(null, this.inventoryType, string != null ? string : "");
      }
    }
    return createdInventory;
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
    this.content.forEach((slot, itemId) -> inventory.setItem(slot, itemId.toItemStack(1)));
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
