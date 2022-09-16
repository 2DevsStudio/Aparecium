package com.ignitedev.aparecium.gui.layer;

import com.ignitedev.aparecium.config.ItemBase;
import com.ignitedev.aparecium.gui.AbstractLayoutLayer;
import com.ignitedev.aparecium.item.basic.LayoutItem;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

@Data
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LayoutLayer extends AbstractLayoutLayer {

  @Autowired // autowired fields HAS to be STATIC
  private static ItemBase itemBase;

  public LayoutLayer(AbstractLayoutLayerBuilder<?, ?> builder) {
    super(builder);
  }

  public LayoutLayer(
      String id,
      Map<Integer, LayoutItem> content,
      InventoryType layoutInventoryType,
      int layoutSize) {
    super(id, content, layoutInventoryType, layoutSize);
  }

  @Override
  public void fill(Inventory inventory, boolean force) {
    if (!force) {
      if (inventory.getType() != this.layoutInventoryType) {
        return;
      }
      if (this.layoutInventoryType == InventoryType.CHEST
          && inventory.getSize() != this.layoutSize) {
        return;
      }
    }
    this.contents.forEach(
        (slot, magicItemID) -> inventory.setItem(slot, magicItemID.toItemStack(1)));
  }
}
