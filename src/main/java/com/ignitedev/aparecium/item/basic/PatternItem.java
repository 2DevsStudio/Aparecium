/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.item.basic;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.util.MathUtility;
import java.util.ArrayList;
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
public class PatternItem extends LayoutItem {

  /**
   * @implNote Global drop chance
   */
  private double dropChance;

  @Singular("addPattern")
  private List<PatternItem> patterns;

  public PatternItem(
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
      double layoutItemInteractionId,
      double dropChance,
      List<PatternItem> patterns) {
    super(
        id,
        material,
        amount,
        itemType,
        rarity,
        name,
        description,
        tags,
        enchants,
        flags,
        layoutItemInteractionId);
    this.dropChance = dropChance;
    this.patterns = patterns;
  }

  /**
   * @return list of items which pass successful {{@link #tryLuck()}}
   */
  public List<PatternItem> roll() {
    List<PatternItem> positiveItems = new ArrayList<>();

    this.patterns.forEach(
        patterns -> {
          if (patterns.tryLuck()) {
            positiveItems.add(patterns.clone());
          }
        });
    return positiveItems;
  }

  /** Changes current item to one from patterns */
  public void rollSet() {
    for (PatternItem pattern : this.patterns) {
      PatternItem clone = pattern.clone();

      if (pattern.tryLuck()) {
        this.setAmount(clone.getAmount());
        this.setMaterial(clone.getMaterial());
        this.setId(clone.getId());
        this.setName(clone.getName());
        this.setDescription(clone.getDescription());
        this.setRarity(clone.getRarity());
        this.setDropChance(clone.getDropChance());
        this.setTags(clone.getTags());
        this.setEnchants(clone.getEnchants());
        this.setFlags(clone.getFlags());
        break;
      }
    }
  }

  /**
   * @return try your luck with {{{@link #dropChance}}}
   */
  public boolean tryLuck() {
    return MathUtility.getRandomPercent(dropChance);
  }

  @Override
  public PatternItem clone() {
    PatternItem clone = ((PatternItem) super.clone());

    clone.dropChance = this.dropChance;
    clone.patterns = this.patterns == null ? null : new ArrayList<>(this.patterns);

    return clone;
  }
}
