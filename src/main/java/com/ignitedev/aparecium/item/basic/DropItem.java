/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.item.basic;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.util.MathUtility;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
  @Singular private Map<Material, Double> dropChancesForMaterials;

  public DropItem(
      @NotNull String id,
      @NotNull Material material,
      int amount,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      @Nullable Map<Enchantment, Integer> enchants,
      @Nullable List<ItemFlag> flags,
      double dropChance,
      Map<Material, Double> dropChancesForMaterials) {
    super(id, material, amount, itemType, rarity, name, description, tags, enchants, flags);
    this.dropChance = dropChance;
    this.dropChancesForMaterials = dropChancesForMaterials;

    if (this.dropChancesForMaterials == null) {
      this.dropChancesForMaterials = new HashMap<>();
    }
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
