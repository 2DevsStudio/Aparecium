package com.ignitedev.aparecium.gui;

import com.ignitedev.aparecium.gui.basic.Layout;
import com.ignitedev.aparecium.gui.layer.LayoutLayer;
import com.ignitedev.aparecium.interfaces.Identifiable;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @implNote abstraction of Layout, if you want to create your own implementation you can extend
 *     that class look at {@link Layout} it is simple implementation of that class
 */
@Data
@SuperBuilder(toBuilder = true)
public abstract class AbstractLayout
    implements Cloneable, Identifiable, Comparable<AbstractLayout> {

  protected String id;

  @Nullable protected String layoutTitle;

  /**
   * @implNote a multiple of 9 ( only applicable for InventoryType = CHEST )
   */
  @Builder.Default protected int layoutSize = 27;

  @Builder.Default protected InventoryType inventoryType = InventoryType.CHEST;

  @Nullable protected LayoutLayer layoutBackgroundLayer;

  /**
   * @implNote <LAYER WEIGHT, LAYER ID>
   */
  @Nullable protected Map<Integer, Integer> layers;

  /**
   * @implNote first applied is {{@link #layers}} then content
   * @implNote <SLOT NUMBER, MAGIC ITEM ID>
   */
  @Builder.Default protected Map<Integer, Integer> content = new HashMap<>();

  @Override
  public int compareTo(@NotNull AbstractLayout compareTo) {
    // todo
    return 0;
  }

  @Override
  @SneakyThrows
  public AbstractLayout clone() {
    AbstractLayout clone = (AbstractLayout) super.clone();
    // todo
    clone.id = this.id;

    return clone;
  }
}
