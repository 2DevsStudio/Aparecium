package com.ignitedev.aparecium.gui;

import com.ignitedev.aparecium.interfaces.Identifiable;
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

  protected String id;

  /**
   * @implNote <SLOT NUMBER, MAGIC ITEM ID>
   */
  @Singular("content")
  protected Map<Integer, String> contents;

  /**
   * @implNote InventoryType to match layouts
   */
  @Builder.Default protected InventoryType layoutInventoryType = InventoryType.CHEST;

  /**
   * @implNote a multiple of 9 ( only applicable for InventoryType = CHEST )
   */
  @Builder.Default protected int layoutSize = 27;

  public abstract void fill(Inventory inventory, boolean force);

  @Override
  public int compareTo(@NotNull AbstractLayoutLayer compareTo) {
    // todo
    return 0;
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
