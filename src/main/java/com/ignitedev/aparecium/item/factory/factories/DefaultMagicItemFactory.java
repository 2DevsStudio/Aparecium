/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.item.factory.factories;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.item.basic.Item;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @implNote Default MagicItem Factory with implemented text assets
 */
public class DefaultMagicItemFactory<T extends Item> extends RawItemStackFactory {

  public <D extends Item> T from(D from) {
    return createItem(
        from.getId(),
        from.getMaterial(),
        from.getItemType(),
        from.getRarity(),
        from.getName(),
        from.getDescription(),
        from.getTags());
  }

  public T createItem(@NotNull String id, @NotNull Material material) {
    return createItem(id, material, null, null, null, null, new HashMap<>());
  }

  public T createItem(
      @NotNull String id, @NotNull Material material, @Nullable ApareciumComponent name) {
    return createItem(id, material, null, null, name, null, new HashMap<>());
  }

  public T createItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity) {
    return createItem(id, material, itemType, rarity, null, null, new HashMap<>());
  }

  public T createItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description) {
    return createItem(id, material, null, null, name, description, new HashMap<>());
  }

  public T createItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description) {
    return createItem(id, material, itemType, rarity, name, description, new HashMap<>());
  }

  public T createItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags) {
    return ((T) new Item(id, material, itemType, rarity, name, description, tags));
  }

  @Override
  public ItemStack buildLore(MagicItem magicItem, ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    ApareciumComponent description = magicItem.getDescription();

    if (description != null) {
      // PAPER CODE
      if (Aparecium.isUsingPaper()) {
        itemMeta.lore(description.getAsComponents());
      } else { // PAPER CODE END
        itemMeta.setLore(description.getAsStrings());
      }
    }
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  @Override
  public ItemStack buildName(MagicItem magicItem, ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    ApareciumComponent name = magicItem.getName();

    if (name != null) {
      // PAPER CODE
      if (Aparecium.isUsingPaper()) {
        itemMeta.displayName(name.getAsComponent());
      } else { // PAPER CODE END
        itemMeta.setDisplayName(name.getAsString());
      }
    }
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }
}
