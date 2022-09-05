package com.ignitedev.aparecium.gui;

import com.ignitedev.aparecium.interfaces.Identifiable;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

@Getter
@SuperBuilder(toBuilder = true)
public abstract class AbstractLayoutLayer
    implements Cloneable, Identifiable, Comparable<AbstractLayoutLayer> {

  protected String id;

  /**
   * @implNote Weight of layer, it simply means layout place order, first placed layer index is 0.
   */
  protected int layerWeight;

  /**
   * @implNote <SLOT NUMBER, MAGIC ITEM ID>
   */
  @Builder.Default protected Map<Integer, Integer> content = new HashMap<>();

  /**
   * @implNote InventoryType to match layouts
   */
  @Builder.Default protected InventoryType layoutInventoryType = InventoryType.CHEST;

  @Override
  public int compareTo(@NotNull AbstractLayoutLayer compareTo) {
    // todo
    return 0;
  }

  @Override
  @SneakyThrows
  public AbstractLayoutLayer clone() {
    AbstractLayoutLayer clone = (AbstractLayoutLayer) super.clone();
    // todo
    clone.content = this.content;
    clone.layoutInventoryType = this.layoutInventoryType;
    clone.id = this.id;

    return clone;
  }
}
