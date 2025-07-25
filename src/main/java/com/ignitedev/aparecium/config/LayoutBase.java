/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.config;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.gui.basic.Layout;
import com.ignitedev.aparecium.gui.repository.InventoriesRepository;
import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.interfaces.Configuration;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("FieldMayBeFinal") // simplejsonconfig is not supporting final fields yet
@Getter
@Configuration(value = "layout-base.json", configPath = "ApareciumConfiguration/")
public class LayoutBase extends Config {

  private Map<String, Layout> savedLayouts = exampleLayouts();

  private Layout defaultLayout =
      Layout.builder()
          .id("default")
          .layoutSize(9)
          .inventoryType(InventoryType.CHEST)
          .layoutTitle(ApareciumComponent.of("default"))
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
  public Layout getByIdOrDefault(String layoutId) {
    return this.savedLayouts.getOrDefault(layoutId, defaultLayout);
  }

  @Nullable
  public Layout getById(String layoutId) {
    return this.savedLayouts.get(layoutId);
  }

  @Nullable
  public Layout getByInventory(Inventory inventory) {
    return InventoriesRepository.getInstance().findByInventory(inventory);
  }

  public boolean exists(String id) {
    return savedLayouts.containsKey(id);
  }

  private Map<String, Layout> exampleLayouts() {
    return new HashMap<>(
        Map.of(
            "dadadadadada",
            Layout.builder()
                .id("dadadadadada")
                .layoutSize(9)
                .inventoryType(InventoryType.CHEST)
                .layoutTitle(ApareciumComponent.of("dadadada"))
                .build()));
  }
}
