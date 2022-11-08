package com.ignitedev.aparecium.gui.layer;

import com.ignitedev.aparecium.config.ItemBase;
import com.ignitedev.aparecium.gui.AbstractLayoutLayer;
import com.ignitedev.aparecium.item.basic.PatternItem;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

@Data
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LayoutPatternLayer extends AbstractLayoutLayer {

  @Autowired // autowired fields HAS to be STATIC
  private static ItemBase itemBase;

  @Singular("pattern")
  private  Map<Integer, PatternItem> patternItemList;

  public LayoutPatternLayer(AbstractLayoutLayerBuilder<?, ?> builder) {
    super(builder);
  }

  public LayoutPatternLayer(
      String id,
      Map<Integer, PatternItem> content,
      InventoryType layoutInventoryType,
      int layoutSize) {
    super(id, content, layoutInventoryType, layoutSize);
  }

  public void refreshPattern() {
    for (Entry<Integer, PatternItem> entry : this.patternItemList.entrySet()) {
      PatternItem value = entry.getValue();

      if(value.tryLuck()){
        value.rollSet();
      }
    }
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
    refreshPattern();

    this.contents.forEach(
        (slot, magicItemID) -> inventory.setItem(slot, magicItemID.toItemStack(1)));
  }
}
