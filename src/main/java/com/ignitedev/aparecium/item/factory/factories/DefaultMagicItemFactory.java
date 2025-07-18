/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.item.factory.factories;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.item.basic.DropItem;
import com.ignitedev.aparecium.item.basic.Item;
import com.ignitedev.aparecium.item.basic.LayoutItem;
import com.ignitedev.aparecium.item.basic.PatternItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @implNote Default MagicItem Factory with implemented text assets
 */
public class DefaultMagicItemFactory<T extends Item> extends RawItemStackFactory {

  public static DefaultMagicItemFactory<?> getByClass(Class<?> castClass) {
    DefaultMagicItemFactory<?> factory;

    if (castClass.getSimpleName().equals(LayoutItem.class.getSimpleName())) {
      factory = Aparecium.getFactoriesManager().getLayoutItemFactory();
    } else if (castClass.getSimpleName().equals(DropItem.class.getSimpleName())) {
      factory = Aparecium.getFactoriesManager().getDropItemFactory();
    } else if (castClass.getSimpleName().equals(PatternItem.class.getSimpleName())) {
      factory = Aparecium.getFactoriesManager().getPatternItemFactory();
    } else {
      factory =
          (DefaultMagicItemFactory<Item>)
              Aparecium.getFactoriesManager().getMagicItemFactories().getDefaultFactory();
    }
    return factory;
  }

  public <D extends Item> T from(D from) {
    return createItem(
        from.getId(),
        from.getMaterial(),
        from.getAmount(),
        from.getItemType(),
        from.getRarity(),
        from.getName(),
        from.getDescription(),
        from.getTags(),
        from.getEnchants(),
        from.getFlags());
  }

  public T createItem(@NotNull String id, @NotNull Material material) {
    return createItem(id, material, 1, null, null, null, null, new HashMap<>());
  }

  public T createItem(@NotNull Material material) {
    return createItem(
        UUID.randomUUID().toString(), material, 1, null, null, null, null, new HashMap<>());
  }

  public T createItem(@NotNull Material material, int amount) {
    return createItem(
        UUID.randomUUID().toString(), material, amount, null, null, null, null, new HashMap<>());
  }

  public T createItem(@NotNull String id, int amount, @NotNull Material material) {
    return createItem(id, material, amount, null, null, null, null, new HashMap<>());
  }

  public T createItem(
      @NotNull String id,
      @NotNull Material material,
      int amount,
      @Nullable ApareciumComponent name) {
    return createItem(id, material, amount, null, null, name, null, new HashMap<>());
  }

  public T createItem(
      @NotNull String id,
      @NotNull Material material,
      int amount,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity) {
    return createItem(id, material, amount, itemType, rarity, null, null, new HashMap<>());
  }

  public T createItem(
      @NotNull String id,
      @NotNull Material material,
      int amount,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description) {
    return createItem(id, material, amount, null, null, name, description, new HashMap<>());
  }

  public T createItem(
      @NotNull String id,
      @NotNull Material material,
      int amount,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description) {
    return createItem(id, material, amount, itemType, rarity, name, description, new HashMap<>());
  }

  public T createItem(
      @NotNull String id,
      @NotNull Material material,
      int amount,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<Enchantment, Integer> enchants) {
    return createItem(
        id, material, amount, itemType, rarity, name, description, null, enchants, null);
  }

  public T createItem(
      @NotNull String id,
      @NotNull Material material,
      int amount,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      @Nullable Map<Enchantment, Integer> enchants) {
    return createItem(
        id, material, amount, itemType, rarity, name, description, tags, enchants, null);
  }

  public T createItem(
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
    return ((T)
        new Item(id, material, amount, itemType, rarity, name, description, tags, enchants, flags));
  }

  @Override
  public void buildLore(MagicItem magicItem, ItemMeta itemMeta) {
    ApareciumComponent description = magicItem.getDescription();

    if (description != null) {
      // PAPER CODE
      if (Aparecium.isUsingPaper()) {
        itemMeta.lore(description.getAsComponents());
      } else { // PAPER CODE END
        itemMeta.setLore(description.getAsStrings());
      }
    }
  }

  @Override
  public void buildName(MagicItem magicItem, ItemMeta itemMeta) {
    ApareciumComponent name = magicItem.getName();

    if (name != null) {
      // PAPER CODE
      if (Aparecium.isUsingPaper()) {
        itemMeta.displayName(name.getAsComponent());
      } else { // PAPER CODE END
        itemMeta.setDisplayName(name.getAsString());
      }
    }
  }
}
