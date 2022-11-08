package com.ignitedev.aparecium.item.basic;

import com.ignitedev.aparecium.Aparecium;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.inventory.ItemStack;

@Data
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LayoutItem extends Item {

  /** Interaction id for interaction hook */
  private double layoutItemInteractionId;

  public LayoutItem(MagicItemBuilder<?, ?> builder) {
    super(builder);
  }

  @Override
  public ItemStack toItemStack(int amount) {
    return Aparecium.getFactoriesManager()
        .getMagicItemFactories()
        .getDefaultFactory()
        .toItemStack(this, amount);
  }

  @Override
  public LayoutItem clone() {
    LayoutItem clone = ((LayoutItem) super.clone());

    clone.layoutItemInteractionId = this.layoutItemInteractionId;

    return clone;
  }
}
