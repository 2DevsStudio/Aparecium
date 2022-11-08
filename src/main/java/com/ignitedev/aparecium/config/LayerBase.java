package com.ignitedev.aparecium.config;

import com.ignitedev.aparecium.gui.layer.LayoutLayer;
import com.ignitedev.aparecium.item.basic.LayoutItem;
import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.interfaces.Configuration;
import java.util.Map;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("FieldMayBeFinal") // simplejsonconfig is not supporting final fields yet
@Getter
@Configuration("layer-base.json")
public class LayerBase extends Config {

  private Map<String, LayoutLayer> layouts = defaultLayouts();

  private LayoutLayer defaultLayer =
      LayoutLayer.builder()
          .id("default")
          .content(0, LayoutItem.getCachedLayoutItem("itemId"))
          .layoutSize(9)
          .layoutInventoryType(InventoryType.CHEST)
          .build();

  /**
   * @implNote please note that this method save and reload config, if you have any pending changes
   *     in your config file then it might be overridden
   */
  public void saveLayer(LayoutLayer layer) {
    this.layouts.put(layer.getId(), layer);

    save();
    reload();
  }

  @NotNull
  public LayoutLayer getById(String layer) {
    return getLayouts().getOrDefault(layer, defaultLayer);
  }

  private Map<String, LayoutLayer> defaultLayouts() {
    return Map.of(
        "default",
        LayoutLayer.builder()
            .id("default")
            .content(0, LayoutItem.getCachedLayoutItem("itemId"))
            .layoutSize(9)
            .layoutInventoryType(InventoryType.CHEST)
            .build());
  }
}
