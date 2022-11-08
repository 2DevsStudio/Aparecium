package com.ignitedev.aparecium.gui;

import com.ignitedev.aparecium.interfaces.Identifiable;
import com.ignitedev.aparecium.item.basic.LayoutItem;
import java.time.Instant;
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

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public abstract class AbstractLayoutLayer
    implements Cloneable, Identifiable, Comparable<AbstractLayoutLayer> {

  /**
   * @implNote Item creation Instant
   */
  protected final Instant layerSaveInstant = Instant.now();

  protected String id;

  /**
   * @implNote <SLOT NUMBER, MAGIC ITEM ID>
   */
  @Singular("content")
  protected Map<Integer, ? extends LayoutItem> contents;

  /**
   * @implNote InventoryType to match layouts
   */
  @Builder.Default protected InventoryType layoutInventoryType = InventoryType.CHEST;

  /**
   * @implNote a multiple of 9 ( only applicable for InventoryType = CHEST )
   */
  @Builder.Default protected int layoutSize = 27;

  public AbstractLayoutLayer(String id, InventoryType layoutInventoryType) {
    this.id = id;
    this.layoutInventoryType = layoutInventoryType;
  }

  public AbstractLayoutLayer(String id, int layoutSize) {
    this.id = id;
    this.layoutSize = layoutSize;
  }

  public AbstractLayoutLayer(
      String id, Map<Integer, ? extends LayoutItem> contents, int layoutSize) {
    this.id = id;
    this.contents = contents;
    this.layoutSize = layoutSize;
  }

  public abstract void fill(Inventory inventory, boolean force);

  @Override
  public int compareTo(@NotNull AbstractLayoutLayer compareTo) {
    int compare =
        Long.compare(
            layerSaveInstant.getEpochSecond(), compareTo.getLayerSaveInstant().getEpochSecond());

    if (compare != 0) {
      return compare;
    }
    return layerSaveInstant.getNano() - compareTo.getLayerSaveInstant().getNano();
  }

  @Override
  @SneakyThrows
  public AbstractLayoutLayer clone() {
    AbstractLayoutLayer clone = (AbstractLayoutLayer) super.clone();

    clone.layoutSize = this.layoutSize;
    clone.contents = this.contents;
    clone.layoutInventoryType = this.layoutInventoryType;
    clone.id = this.id;

    return clone;
  }
}
