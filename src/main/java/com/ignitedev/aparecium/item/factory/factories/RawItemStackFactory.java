/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
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
import java.util.Map;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

/** Raw ItemStack Factory without implemented text assets; lore and name */
public abstract class RawItemStackFactory implements MagicItemFactory {

  /**
   * @param magicItem item to convert
   * @param amount itemstack quantity
   * @return built itemstack with all values in MagicItem
   */
  @Override
  public ItemStack toItemStack(MagicItem magicItem, int amount) {
    ItemStack itemStack = new ItemStack(magicItem.getMaterial());

    applyTags(magicItem, new NBTItem(itemStack, true));

    Damageable itemMeta = (Damageable) itemStack.getItemMeta();

    itemStack = buildName(magicItem, itemStack);
    itemStack = buildLore(magicItem, itemStack);

    itemMeta.addItemFlags(ItemFlag.values());

    itemStack.setItemMeta(itemMeta);
    itemStack.setAmount(amount);

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
        .build();
  }

  /**
   * @param magicItem MagicItem instance for assets
   * @param itemStack itemstack to apply changes
   */
  public void updateItemStack(MagicItem magicItem, ItemStack itemStack) {
    Damageable itemMeta = (Damageable) itemStack.getItemMeta();

    itemStack = buildName(magicItem, itemStack);
    itemStack = buildLore(magicItem, itemStack);

    itemStack.setItemMeta(itemMeta);
  }

  /**
   * @param magicItem MagicItem instance for applying NBT-TAGS
   * @param nbtItem item to apply nbt changes
   */
  public void applyTags(MagicItem magicItem, NBTItem nbtItem) {
    nbtItem.setString("id", magicItem.getId());

    magicItem.getTags().forEach(nbtItem::setObject);
  }

  /**
   * @param magicItem MagicItem instance to apply changes
   * @return built lore with specific implementation
   */
  public abstract ItemStack buildLore(MagicItem magicItem, ItemStack itemStack);

  /**
   * @param magicItem MagicItem instance to apply changes
   * @return built name with specific implementation
   */
  public abstract ItemStack buildName(MagicItem magicItem, ItemStack itemStack);
}
