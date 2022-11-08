/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.item.factory.factories;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.item.basic.DropItem;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DropItemFactory extends DefaultMagicItemFactory<DropItem> {

  public DropItem createItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      double dropChance) {
    return new DropItem(
        id, material, itemType, rarity, name, description, tags, dropChance, new HashMap<>());
  }

  public DropItem createItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      double dropChance,
      Map<Material, Double> dropChancesForMaterials) {
    return new DropItem(
        id,
        material,
        itemType,
        rarity,
        name,
        description,
        tags,
        dropChance,
        dropChancesForMaterials);
  }

  @Override
  public DropItem createItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags) {
    return new DropItem(
        id, material, itemType, rarity, name, description, tags, 0, new HashMap<>());
  }
}
