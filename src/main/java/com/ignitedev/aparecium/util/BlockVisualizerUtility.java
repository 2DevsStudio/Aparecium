/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.util.text.TextUtility;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Utility class for visualizing blocks in the Minecraft world by spawning glowing, named falling blocks.
 * Useful for highlighting or previewing blocks to players.
 */
@UtilityClass
public class BlockVisualizerUtility {

  /**
   * Stores the locations of visualized blocks and their corresponding falling block objects.
   */
  private final Map<Location, Object> visualizedBlocks = new HashMap<>();

  /**
   * Visualizes a block by replacing it with a glowing, named falling block for all players.
   *
   * @param block The block to visualize.
   * @param blockData The block data to use for the falling block.
   * @param blockName The name to display for the falling block.
   * @param aparecium The plugin instance used for scheduling tasks.
   */
  public void visualize(
          @NonNull Block block, BlockData blockData, String blockName, Aparecium aparecium) {
    Location location = block.getLocation();
    FallingBlock falling = spawnFallingBlock(location, blockData, blockName);

    for (Player player : block.getWorld().getPlayers()) {
      Bukkit.getScheduler()
              .runTaskLater(
                      aparecium,
                      () -> player.sendBlockChange(block.getLocation(), block.getBlockData()),
                      2);
    }
    visualizedBlocks.put(location, falling);
  }

  /**
   * Spawns a falling block at the specified location with custom properties.
   *
   * @param location The location to spawn the falling block.
   * @param blockData The block data to use for the falling block.
   * @param blockName The name to display for the falling block.
   * @return The spawned falling block.
   */
  private FallingBlock spawnFallingBlock(Location location, BlockData blockData, String blockName) {
    FallingBlock falling =
            location.getWorld().spawnFallingBlock(location.clone().add(0.5, 0, 0.5), blockData);

    falling.setDropItem(false);
    falling.setVelocity(new Vector(0, 0, 0));
    falling.setCustomNameVisible(true);
    falling.customName(TextUtility.colorizeToComponent(blockName));
    falling.setGlowing(true);
    falling.setGravity(false);

    return falling;
  }

  /**
   * Stops visualizing a specific block and restores its original appearance for all players.
   *
   * @param block The block to stop visualizing.
   * @param aparecium The plugin instance used for scheduling tasks.
   */
  public void stopVisualizing(@NonNull Block block, Aparecium aparecium) {
    Object fallingBlock = visualizedBlocks.remove(block.getLocation());

    if (fallingBlock instanceof FallingBlock) {
      ((FallingBlock) fallingBlock).remove();
    }
    for (Player player : block.getWorld().getPlayers()) {
      Bukkit.getScheduler()
              .runTaskLater(
                      aparecium,
                      () -> player.sendBlockChange(block.getLocation(), block.getBlockData()),
                      1);
    }
  }

  /**
   * Stops visualizing all currently visualized blocks.
   *
   * @param aparecium The plugin instance used for scheduling tasks.
   */
  public void stopAll(Aparecium aparecium) {
    for (Location location : new HashSet<>(visualizedBlocks.keySet())) {
      Block block = location.getBlock();

      if (isVisualized(block)) {
        stopVisualizing(block, aparecium);
      }
    }
  }

  /**
   * Checks if a block is currently being visualized.
   *
   * @param block The block to check.
   * @return True if the block is visualized, false otherwise.
   */
  public boolean isVisualized(@NonNull Block block) {
    return visualizedBlocks.containsKey(block.getLocation());
  }
}