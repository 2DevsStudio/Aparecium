/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.gui.repository;

import com.ignitedev.aparecium.gui.basic.Layout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InventoriesRepository {

  private final Map<Layout, List<Inventory>> cachedInventories = new HashMap<>();

  @NotNull
  public static InventoriesRepository getInstance() {
    return SingletonHelper.INSTANCE;
  }

  @Nullable
  public Layout findByInventory(Inventory inventory) {
    for (Entry<Layout, List<Inventory>> entry : this.cachedInventories.entrySet()) {
      if (entry.getValue().contains(inventory)) {
        return entry.getKey();
      }
    }
    return null;
  }

  public List<Inventory> getInventoriesByLayout(Layout layout) {
    return cachedInventories.getOrDefault(layout, new ArrayList<>());
  }

  public void add(Layout value, Inventory inventory) {
    List<Inventory> inventoriesByLayout = getInventoriesByLayout(value);

    if (inventoriesByLayout.isEmpty()) {
      this.cachedInventories.replace(value, new ArrayList<>(List.of(inventory)));
    } else {
      inventoriesByLayout.add(inventory);
      this.cachedInventories.replace(value, inventoriesByLayout);
    }
  }

  private static class SingletonHelper {
    private static final InventoriesRepository INSTANCE = new InventoriesRepository();
  }
}
