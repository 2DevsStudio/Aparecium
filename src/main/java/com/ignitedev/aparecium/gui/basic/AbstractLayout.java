package com.ignitedev.aparecium.gui.basic;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.interfaces.Identifiable;
import com.ignitedev.aparecium.item.MagicItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @implNote abstraction of Layout, if you want to create your own implementation you can extend
 *     that class look at {@link com.ignitedev.aparecium.gui.Layout} it is simple implementation of
 *     that class
 */
@Getter
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

  @Nullable protected Material layoutFillMaterial;

  @Builder.Default protected Map<Integer, MagicItem> content = new HashMap<>();

  @Override
  public int compareTo(@NotNull AbstractLayout compareTo) {
    // todo
    new ApareciumComponent(() -> List.of("test", "test"));
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
