/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.util;

import com.ignitedev.aparecium.enums.ItemType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@UtilityClass
public class ItemUtility {

  /**
   * @implNote check if item has ItemMeta & display name & lore
   */
  public boolean isValid(ItemStack itemStack) {
    if (itemStack != null && itemStack.hasItemMeta()) {
      ItemMeta itemMeta = itemStack.getItemMeta();
      return itemMeta.hasDisplayName() && itemMeta.hasLore();
    }
    return false;
  }

  /**
   * @implNote adding all flags for items tack
   */
  public ItemStack normalizeItemStack(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();

    itemMeta.addItemFlags(ItemFlag.values());
    itemStack.setItemMeta(itemMeta);

    return itemStack;
  }

  public List<Enchantment> getCompatibleEnchantments(ItemStack itemStack) {
    List<Enchantment> compatible = new ArrayList<>();

    Arrays.stream(Enchantment.values())
        .forEach(
            enchantment -> {
              if (enchantment.getItemTarget().includes(itemStack)) {
                compatible.add(enchantment);
              }
            });

    return compatible;
  }

  @NotNull
  public static ItemStack getMobSpawnerItemStackFromBlock(Block block) {
    ItemStack spawner = new ItemStack(Material.SPAWNER);

    if (block == null || !(block.getState() instanceof CreatureSpawner)) {
      return spawner;
    }
    BlockStateMeta spawnerMeta = (BlockStateMeta) spawner.getItemMeta();
    CreatureSpawner spawnerState = (CreatureSpawner) spawnerMeta.getBlockState();

    spawnerState.setSpawnedType(((CreatureSpawner) block.getState()).getSpawnedType());
    spawnerMeta.setBlockState(spawnerState);
    spawner.setItemMeta(spawnerMeta);

    return spawner;
  }

  public ItemType getItemTypeByMaterial(Material material) {
    String name = material.name().toLowerCase();

    if (name.contains("sword") || name.contains("bow") || name.contains("crossbow")) {
      return ItemType.WEAPON;
    } else if (name.contains("boots")
        || name.contains("chestplate")
        || name.contains("leggings")
        || name.contains("helmet")
        || name.contains("shield")) {
      return ItemType.ARMOR;
    } else if (name.contains("shovel")
        || name.contains("hoe")
        || name.contains("axe")
        || name.contains("pickaxe")) {
      return ItemType.TOOL;
    } else if (name.contains("potion")) {
      return ItemType.POTION;
    }
    return ItemType.COMMON;
  }
}
