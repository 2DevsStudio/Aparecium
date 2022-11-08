package com.ignitedev.aparecium.gui;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.gui.basic.Layout;
import com.ignitedev.aparecium.gui.layer.LayoutLayer;
import com.ignitedev.aparecium.interfaces.Identifiable;
import com.ignitedev.aparecium.item.basic.LayoutItem;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @implNote abstraction of Layout, if you want to create your own implementation you can extend
 *     that class look at {@link Layout} it is simple implementation of that class
 */
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public abstract class AbstractLayout
    implements Cloneable, Identifiable, Comparable<AbstractLayout> {

  protected String id;

  @Nullable protected ApareciumComponent layoutTitle;

  /**
   * @implNote a multiple of 9 ( only applicable for InventoryType = CHEST )
   */
  @Builder.Default protected int layoutSize = 27;

  @Builder.Default protected InventoryType inventoryType = InventoryType.CHEST;

  @Nullable protected LayoutLayer layoutBackgroundLayer;

  /**
   * @implNote <LAYER WEIGHT (LOWER = HIGHER PRIORITY), LAYER ID>
   */
  @Singular("layer")
  protected Map<Integer, String> layers = new HashMap<>();

  /**
   * @implNote first applied is {{@link #layers}} then content
   * @implNote <SLOT NUMBER, LayoutItem>
   */
  @Singular("content")
  protected Map<Integer, LayoutItem> contents = new HashMap<>();

  public AbstractLayout(String id, int layoutSize) {
    this.id = id;
    this.layoutSize = layoutSize;
  }

  public AbstractLayout(
      String id, @Nullable ApareciumComponent layoutTitle, InventoryType inventoryType) {
    this.id = id;
    this.layoutTitle = layoutTitle;
    this.inventoryType = inventoryType;
  }

  public abstract void fill(Inventory inventory, boolean fillBackground, boolean force);

  public abstract void fillBackground(Inventory inventory);

  public abstract Inventory createLayout();

  @Override
  public int compareTo(@NotNull AbstractLayout compareTo) {
    // todo
    return 0;
  }

  @Override
  @SneakyThrows
  public AbstractLayout clone() {
    AbstractLayout clone = (AbstractLayout) super.clone();

    clone.id = this.id;
    clone.layoutTitle = this.layoutTitle;
    clone.layoutSize = this.layoutSize;
    clone.inventoryType = this.inventoryType;
    clone.layoutBackgroundLayer = this.layoutBackgroundLayer;
    clone.layers = this.layers;
    clone.contents = this.contents;

    return clone;
  }
}
