package com.ignitedev.aparecium.item.factory.factories;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.item.basic.PatternItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PatternItemFactory extends DefaultMagicItemFactory<PatternItem> {

  public PatternItem createItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      double layoutItemInteractionId) {
    return new PatternItem(
        id,
        material,
        itemType,
        rarity,
        name,
        description,
        tags,
        layoutItemInteractionId,
        0,
        new ArrayList<>());
  }

  public PatternItem createItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      double layoutItemInteractionId,
      double dropChance) {
    return new PatternItem(
        id,
        material,
        itemType,
        rarity,
        name,
        description,
        tags,
        layoutItemInteractionId,
        dropChance,
        new ArrayList<>());
  }

  public PatternItem createItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      double dropChance,
      List<PatternItem> patterns) {
    return new PatternItem(
        id, material, itemType, rarity, name, description, tags, 0, dropChance, patterns);
  }

  public PatternItem createItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      double layoutItemInteractionId,
      double dropChance,
      List<PatternItem> patterns) {
    return new PatternItem(
        id,
        material,
        itemType,
        rarity,
        name,
        description,
        tags,
        layoutItemInteractionId,
        dropChance,
        patterns);
  }
}
