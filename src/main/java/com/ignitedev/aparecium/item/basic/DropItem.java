/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.item.basic;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.util.MathUtility;
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

  /**
   * @implNote Global drop chance if not included in {{@link #dropChancesForMaterials}}
   */
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

  /**
   * @return try your luck with {{{@link #dropChance}}}
   */
  public boolean tryLuck() {
    return MathUtility.getRandomPercent(dropChance);
  }

  @Override
  public DropItem clone() {
    DropItem clone = ((DropItem) super.clone());

    clone.dropChance = this.dropChance;
    clone.dropChancesForMaterials = this.dropChancesForMaterials;

    return clone;
  }
}
