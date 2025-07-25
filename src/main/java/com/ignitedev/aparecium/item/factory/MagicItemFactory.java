/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.item.factory;

import com.ignitedev.aparecium.factory.Factory;
import com.ignitedev.aparecium.item.MagicItem;
import org.bukkit.inventory.ItemStack;

public interface MagicItemFactory extends Factory {

  ItemStack toItemStack(MagicItem magicItem);

  MagicItem fromItemStack(ItemStack itemStack);
}
