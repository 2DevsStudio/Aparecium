/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.item.factory.factories;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.item.factory.RawItemStackFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @implNote Default MagicItem Factory with implemented text assets
 */
public class DefaultMagicItemFactory extends RawItemStackFactory {

  @Override
  public ItemStack buildLore(MagicItem magicItem, ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();

    // PAPER CODE
    if (Aparecium.isUsingPaper()) {
      itemMeta.lore(magicItem.getDescription().getAsComponents());
    } else {  // PAPER CODE END
      itemMeta.setLore(magicItem.getDescription().getAsStrings());
    }
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  @Override
  public ItemStack buildName(MagicItem magicItem, ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();

    // PAPER CODE
    if (Aparecium.isUsingPaper()) {
      itemMeta.displayName(magicItem.getName().getAsComponent());
    } else { // PAPER CODE END
      itemMeta.setDisplayName(magicItem.getName().getAsString());
    }
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }
}
