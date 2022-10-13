package com.ignitedev.aparecium.item.factory.factories;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.item.basic.LayoutItem;
import java.util.Map;
import org.bukkit.Material;
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
      double layoutItemInteractionId) {
    return new LayoutItem(
        id, material, itemType, rarity, name, description, tags, layoutItemInteractionId);
  }

  @Override
  public LayoutItem createItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags) {
    return new LayoutItem(
        id, material, itemType, rarity, name, description, tags, 0);
  }
}
