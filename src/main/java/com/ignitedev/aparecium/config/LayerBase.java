package com.ignitedev.aparecium.config;

import com.ignitedev.aparecium.gui.layer.LayoutLayer;
import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.interfaces.Configuration;
import java.util.Map;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryType;

@SuppressWarnings("FieldMayBeFinal") // simplejsonconfig is not supporting final fields yet
@Getter
@Configuration("layer-base.json")
public class LayerBase extends Config {

  private Map<String, LayoutLayer> layouts = defaultLayouts();

  private Map<String, LayoutLayer> defaultLayouts() {
    return Map.of(
        "default",
        LayoutLayer.builder()
            .id("default")
            .content(0, "itemId")
            .layoutSize(9)
            .layoutInventoryType(InventoryType.CHEST)
            .build());
  }
}
