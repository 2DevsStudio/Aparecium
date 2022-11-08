package com.ignitedev.aparecium.item.basic;

import com.ignitedev.aparecium.Aparecium;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.inventory.ItemStack;

@Data
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PatternItem extends DropItem {

  @Singular("addPattern")
  private List<DropItem> patterns;

  public PatternItem(MagicItemBuilder<?, ?> builder) {
    super(builder);
  }

  /**
   * @return list of items which pass successful {{{@link #tryLuck()}}}
   */
  public List<DropItem> roll() {
    List<DropItem> positiveItems = new ArrayList<>();

    this.patterns.forEach(
        patterns -> {
          if (patterns.tryLuck()) {
            positiveItems.add(patterns.clone());
          }
        });
    return positiveItems;
  }

  @Override
  public ItemStack toItemStack(int amount) {
    return Aparecium.getFactoriesManager()
        .getMagicItemFactories()
        .getDefaultFactory()
        .toItemStack(this, amount);
  }

  @Override
  public PatternItem clone() {
    PatternItem clone = ((PatternItem) super.clone());

    clone.patterns = this.patterns;

    return clone;
  }
}
