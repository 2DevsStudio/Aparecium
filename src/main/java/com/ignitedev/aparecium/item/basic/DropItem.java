/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.item.basic;

import com.ignitedev.aparecium.Aparecium;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Data
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DropItem extends Item {

  private double dropChance;

  /**
   * @implNote Useful for example for block drop system, different chances per types
   */
  private Map<Material, Double> dropChancesForMaterials;

  public DropItem(MagicItemBuilder<?, ?> builder) {
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
  public DropItem clone() {
    return (DropItem) super.clone();
  }
}
