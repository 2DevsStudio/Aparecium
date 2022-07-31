package com.ignitedev.aparecium.item.factory;

import com.ignitedev.aparecium.factory.Factory;
import com.ignitedev.aparecium.item.MagicItem;
import org.bukkit.inventory.ItemStack;

public interface MagicItemFactory extends Factory {

  ItemStack toItemStack(MagicItem magicItem, int amount);
}
