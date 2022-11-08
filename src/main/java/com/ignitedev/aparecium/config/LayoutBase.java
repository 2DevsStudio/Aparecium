package com.ignitedev.aparecium.config;

import com.ignitedev.aparecium.gui.basic.Layout;
import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.interfaces.Configuration;
import java.util.Map;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("FieldMayBeFinal") // simplejsonconfig is not supporting final fields yet
@Getter
@Configuration("layout-base.json")
public class LayoutBase extends Config {

  private Map<String, Layout> savedLayouts = exampleLayouts();

  private Layout defaultLayout =
      Layout.builder()
          .id("default")
          .layoutSize(9)
          .inventoryType(InventoryType.CHEST)
          .layoutTitle("default")
          .content(Map.of(0, "default"))
          .build();

  /**
   * @implNote please note that this method save and reload config, if you have any pending changes
   *     in your config file then it might be overridden
   * @param layout layout to save
   */
  public void saveLayout(Layout layout) {
    this.savedLayouts.put(layout.getId(), layout);

    save();
    reload();
  }

  @NotNull
  public Layout getById(String layoutId) {
    return this.savedLayouts.getOrDefault(layoutId, defaultLayout);
  }

  private Map<String, Layout> exampleLayouts() {
    return Map.of(
        "default",
        Layout.builder()
            .id("default")
            .layoutSize(9)
            .inventoryType(InventoryType.CHEST)
            .layoutTitle("default")
            .content(Map.of(0, "default"))
            .build());
  }
}
