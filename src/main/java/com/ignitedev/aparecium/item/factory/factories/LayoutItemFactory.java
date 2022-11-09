/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.item.factory.factories;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.item.basic.LayoutItem;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LayoutItemFactory extends DefaultMagicItemFactory<LayoutItem> {

  public LayoutItem createItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      @Nullable Map<Enchantment, Integer> enchants,
      @Nullable List<ItemFlag> flags,
      double layoutItemInteractionId) {
    return new LayoutItem(
        id,
        material,
        itemType,
        rarity,
        name,
        description,
        tags,
        enchants,
        flags,
        layoutItemInteractionId);
  }

  @Override
  public LayoutItem createItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      @Nullable Map<Enchantment, Integer> enchants,
      @Nullable List<ItemFlag> flags) {
    return new LayoutItem(
        id, material, itemType, rarity, name, description, tags, enchants, flags, 0);
  }
}
