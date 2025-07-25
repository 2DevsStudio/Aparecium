/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.item.factory.factories;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.item.basic.PatternItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PatternItemFactory extends DefaultMagicItemFactory<PatternItem> {

  public PatternItem createItem(
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
      double layoutItemInteractionId) {
    return new PatternItem(
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
        layoutItemInteractionId,
        0,
        new ArrayList<>());
  }

  public PatternItem createItem(
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
      double dropChance) {
    return new PatternItem(
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
        layoutItemInteractionId,
        dropChance,
        new ArrayList<>());
  }

  public PatternItem createItem(
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
      List<PatternItem> patterns) {
    return new PatternItem(
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
        0,
        dropChance,
        patterns);
  }

  public PatternItem createItem(
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
    return new PatternItem(
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
        layoutItemInteractionId,
        dropChance,
        patterns);
  }

  @Override
  public PatternItem createItem(
      @NotNull String id,
      @NotNull Material material,
      int amount,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      @Nullable Map<Enchantment, Integer> enchants,
      @Nullable List<ItemFlag> flags) {
    return new PatternItem(
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
        0,
        0,
        new ArrayList<>());
  }
}
