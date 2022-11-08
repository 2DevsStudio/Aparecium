package com.ignitedev.aparecium.example;

import com.ignitedev.aparecium.gui.basic.Layout;
import com.ignitedev.aparecium.gui.layer.LayoutLayer;
import java.util.Map;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

@Example
public class GuiExample {

  @Example
  public void createGuiWithLayout() {
    // optional ofc ( it is background layer )

    LayoutLayer backgroundLayer =
        LayoutLayer.builder()
            .id("backgroundLayer")
            .layoutSize(18)
            .layoutInventoryType(InventoryType.CHEST)
            .content(0, "itemId")
            .build();

    Layout layout =
        Layout.builder()
            .id("exampleLayout")
            .layoutSize(18)
            .layoutTitle("2Devs on FIRE")
            .inventoryType(InventoryType.CHEST)
            .content(Map.of(0, "itemId", 1, "itemId"))
            .layers(Map.of(0, "LayerID5", 1, "LayerID3", 2, "LayerID8"))
            .layoutBackgroundLayer(backgroundLayer)
            .build();

    // If someone don't like builder pattern

    Layout layoutAnother =
        new Layout(
            "exampleLayout",
            "2Devs On FIRE",
            18,
            InventoryType.CHEST,
            backgroundLayer,
            Map.of(0, "LayerID5", 1, "LayerID3", 2, "LayerID8"),
            Map.of(0, "itemId", 1, "itemId"));

    // this Inventory contains all specified data
    Inventory createdInventory = layout.createLayout();
  }
}
