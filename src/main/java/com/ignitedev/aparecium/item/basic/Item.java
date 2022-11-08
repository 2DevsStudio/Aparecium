package com.ignitedev.aparecium.item.basic;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.item.MagicItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.inventory.ItemStack;

/**
 * @implNote This is default implementation of MagicItem, you should use it if you don't need any additional type of
 * custom implementation
 */
@Data
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Item extends MagicItem {

  public Item(MagicItemBuilder<?, ?> builder) {
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
  public Item clone() {
    return (Item) super.clone();
  }
}
