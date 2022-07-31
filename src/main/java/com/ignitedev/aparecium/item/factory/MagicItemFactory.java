/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.item.factory;

import com.ignitedev.aparecium.factory.Factory;
import com.ignitedev.aparecium.item.MagicItem;
import org.bukkit.inventory.ItemStack;

public interface MagicItemFactory extends Factory {

  ItemStack toItemStack(MagicItem magicItem, int amount);
}
