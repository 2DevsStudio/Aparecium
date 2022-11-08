/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.item.basic;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.item.MagicItem;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.inventory.ItemStack;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LayoutItem extends MagicItem {

  /**
   * @implNote <Layout ID -- Layout DisplayName>
   */
  private Map<String, List<String>> layoutsDisplayName;

  public LayoutItem(MagicItemBuilder<?, ?> builder) {
    super(builder);
    builder.itemType(ItemType.LAYOUT);
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
    return (LayoutItem) super.clone();
  }
}
