/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.item.factory.factories;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.config.ItemBase;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.item.basic.Item;
import com.ignitedev.aparecium.item.factory.MagicItemFactory;
import com.twodevsstudio.simplejsonconfig.api.Config;
import de.tr7zw.nbtapi.NBTItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/** Raw ItemStack Factory without implemented text assets; lore and name */
public abstract class RawItemStackFactory implements MagicItemFactory {

  /**
   * @param magicItem item to convert
   * @return built itemstack with all values in MagicItem
   */
  @Override
  public ItemStack toItemStack(MagicItem magicItem) {
    ItemStack itemStack = new ItemStack(magicItem.getMaterial());
    ItemMeta itemMeta = itemStack.getItemMeta();

    buildLore(magicItem, itemMeta);
    buildName(magicItem, itemMeta);

    itemStack.setItemMeta(itemMeta);

    List<ItemFlag> flags = magicItem.getFlags();
    Map<Enchantment, Integer> enchants = magicItem.getEnchants();

    if (flags != null && !flags.isEmpty()) {
      flags.forEach(itemStack::addItemFlags);
    }
    if (enchants != null && !enchants.isEmpty()) {
      enchants.forEach(itemStack::addUnsafeEnchantment);
    }
    itemMeta = itemStack.getItemMeta(); // refresh enchants and flags for item meta

    itemStack.setItemMeta(itemMeta);
    itemStack.setAmount(magicItem.getAmount());
    itemStack = applyTags(magicItem, new NBTItem(itemStack, true));

    return itemStack;
  }

  @Override
  public MagicItem fromItemStack(ItemStack itemStack) {
    MagicItem cachedMagicItem = Config.getConfig(ItemBase.class).findByItemStack(itemStack);

    if (cachedMagicItem != null) {
      return cachedMagicItem;
    }
    ItemMeta itemMeta = itemStack.getItemMeta();
    NBTItem nbtItem = new NBTItem(itemStack);
    Map<String, Object> tags = new HashMap<>();

    for (String key : nbtItem.getKeys()) {
      tags.put(key, nbtItem.getObject(key, Object.class));
    }
    return Item.builder()
        .id(itemStack.toString())
        .material(itemStack.getType())
        .name(new ApareciumComponent(itemMeta.displayName()))
        .description(new ApareciumComponent(() -> itemMeta.lore()))
        .tags(tags)
        .enchants(itemStack.getEnchantments())
        .flags(itemStack.getItemFlags())
        .build();
  }

  /**
   * @param magicItem MagicItem instance for assets
   * @param itemStack itemstack to apply changes
   */
  public void updateItemStack(MagicItem magicItem, ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();

    buildName(magicItem, itemMeta);
    buildLore(magicItem, itemMeta);

    itemStack.setItemMeta(itemMeta);
  }

  /**
   * @param magicItem MagicItem instance for applying NBT-TAGS
   * @param nbtItem item to apply nbt changes
   */
  public ItemStack applyTags(MagicItem magicItem, NBTItem nbtItem) {
    nbtItem.setString("id", magicItem.getId());
    Map<String, Object> tags = magicItem.getTags();

    if (tags != null && !tags.isEmpty()) {
      tags.forEach(nbtItem::setObject);
    }
    return nbtItem.getItem();
  }

  /**
   * @param magicItem MagicItem instance to apply changes
   */
  public abstract void buildLore(MagicItem magicItem, ItemMeta itemMeta);

  /**
   * @param magicItem MagicItem instance to apply changes
   */
  public abstract void buildName(MagicItem magicItem, ItemMeta itemMeta);
}
