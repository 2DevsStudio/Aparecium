/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

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

/**
 * Utility class providing helper methods for item manipulation
 */
@SuppressWarnings("unused")
@UtilityClass
public class ItemUtility {

  /**
   * Checks if the given item stack is valid.
   * An item stack is considered valid if it has item metadata, a display name, and lore.
   *
   * @param itemStack The item stack to check.
   * @return True if the item stack is valid, false otherwise.
   * @implNote This method checks for the presence of item metadata, display name, and lore.
   */
  public boolean isValid(ItemStack itemStack) {
    if (itemStack != null && itemStack.hasItemMeta()) {
      ItemMeta itemMeta = itemStack.getItemMeta();
      return itemMeta.hasDisplayName() && itemMeta.hasLore();
    }
    return false;
  }

  /**
   * Normalizes the given item stack by adding all available item flags.
   *
   * @param itemStack The item stack to normalize.
   * @return The normalized item stack with all item flags added.
   * @implNote This method modifies the item metadata to include all item flags.
   */
  public ItemStack normalizeItemStack(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();

    itemMeta.addItemFlags(ItemFlag.values());
    itemStack.setItemMeta(itemMeta);

    return itemStack;
  }

  /**
   * Retrieves a list of enchantments compatible with the given item stack.
   *
   * @param itemStack The item stack to check for compatible enchantments.
   * @return A list of compatible enchantments for the item stack.
   */
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

  /**
   * Creates an item stack representing a mob spawner based on the given block.
   * If the block is a creature spawner, its spawn type is copied to the item stack.
   *
   * @param block The block to create the mob spawner item stack from.
   * @return An item stack representing a mob spawner.
   * @implNote If the block is not a creature spawner, a default spawner item stack is returned.
   */
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
}