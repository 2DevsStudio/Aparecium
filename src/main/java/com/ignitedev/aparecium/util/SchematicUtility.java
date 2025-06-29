/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import com.fastasyncworldedit.core.FaweAPI;
import com.ignitedev.aparecium.engine.ApareciumMain;
import com.ignitedev.aparecium.logging.HedwigLogger;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.AbstractDelegateExtent;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class for handling schematic operations in Minecraft.
 * Provides methods for pasting schematics asynchronously, checking if a location is empty, and more.
 */
@SuppressWarnings("unused")
@UtilityClass
public class SchematicUtility {

  // Cache for loaded clipboards to avoid reloading the same schematic multiple times
  private final Map<String, Clipboard> clipboardCache = new ConcurrentHashMap<>();

  /**
   * Asynchronously pastes a schematic at the specified location.
   * Executes a callback after the paste operation is completed.
   *
   * @param schematic The name of the schematic file.
   * @param location The location where the schematic will be pasted.
   * @param callback A callback to execute after the paste operation.
   * @param copyBiomes Whether to copy biomes from the schematic.
   * @param copyEntities Whether to copy entities from the schematic.
   * @param ignoreAirBlocks Whether to ignore air blocks during the paste operation.
   */
  public void asyncPaste(
          String schematic,
          Location location,
          Runnable callback,
          boolean copyBiomes,
          boolean copyEntities,
          boolean ignoreAirBlocks) {
    asyncPaste(
            schematic,
            location,
            callback,
            ApareciumMain.getInstance(),
            copyBiomes,
            copyEntities,
            ignoreAirBlocks);
  }

  /**
   * Asynchronously pastes a schematic at the specified location using a specific plugin instance.
   * Executes a callback after the paste operation is completed.
   *
   * @param schematic The name of the schematic file.
   * @param location The location where the schematic will be pasted.
   * @param callback A callback to execute after the paste operation.
   * @param yourPlugin The plugin instance to use for scheduling tasks.
   * @param copyBiomes Whether to copy biomes from the schematic.
   * @param copyEntities Whether to copy entities from the schematic.
   * @param ignoreAirBlocks Whether to ignore air blocks during the paste operation.
   */
  public void asyncPaste(
          String schematic,
          Location location,
          Runnable callback,
          Plugin yourPlugin,
          boolean copyBiomes,
          boolean copyEntities,
          boolean ignoreAirBlocks) {
    BukkitScheduler scheduler = Bukkit.getScheduler();
    scheduler.runTaskAsynchronously(yourPlugin, () -> {
      paste(schematic, location, copyBiomes, copyEntities, ignoreAirBlocks);
      if (callback != null) {
        scheduler.runTask(yourPlugin, callback);
      }
    });
  }

  /**
   * Pastes a schematic at the specified location.
   *
   * @param schematic The name of the schematic file.
   * @param location The location where the schematic will be pasted.
   * @param copyBiomes Whether to copy biomes from the schematic.
   * @param copyEntities Whether to copy entities from the schematic.
   * @param ignoreAirBlocks Whether to ignore air blocks during the paste operation.
   * @return The BlockVector3 representing the paste location, or null if the operation fails.
   */
  @Nullable
  public BlockVector3 paste(
          String schematic,
          Location location,
          boolean copyBiomes,
          boolean copyEntities,
          boolean ignoreAirBlocks) {
    return paste(
            schematic,
            location,
            ApareciumMain.getInstance(),
            copyBiomes,
            copyEntities,
            ignoreAirBlocks);
  }

  /**
   * Pastes a schematic at the specified location using a specific plugin instance.
   *
   * @param schematic The name of the schematic file.
   * @param location The location where the schematic will be pasted.
   * @param yourPlugin The plugin instance to use for logging and file management.
   * @param copyBiomes Whether to copy biomes from the schematic.
   * @param copyEntities Whether to copy entities from the schematic.
   * @param ignoreAirBlocks Whether to ignore air blocks during the paste operation.
   * @return The BlockVector3 representing the paste location, or null if the operation fails.
   */
  @SneakyThrows
  @Nullable
  public BlockVector3 paste(
          String schematic,
          Location location,
            Plugin yourPlugin,
          boolean copyBiomes,
          boolean copyEntities,
          boolean ignoreAirBlocks) {
    File file = new File(yourPlugin.getDataFolder(), "schematics/" + schematic);

    if (!file.exists()) {
      HedwigLogger.getMainLogger().warning("File with schematic does not exists: " + schematic);
      return null;
    }

    Clipboard clipboard =
        clipboardCache.computeIfAbsent(
            schematic,
            key -> {
              ClipboardFormat byFile = ClipboardFormats.findByFile(file);

                try {
                    if (byFile != null) {
                        return byFile.getReader(Files.newInputStream(file.toPath())).read();
                    }
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
                return null;
            });
    if (clipboard == null) {
      HedwigLogger.getMainLogger().warning("Error on loading schematic: " + schematic);
      return null;
    }

    World world = FaweAPI.getWorld(location.getWorld().getName());
    Validate.notNull(world, "World cannot be null");

    BlockVector3 vector = BlockVector3.at(location.getX(), location.getY(), location.getZ());
    try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
      Operation operation = new ClipboardHolder(clipboard)
              .createPaste(editSession)
              .to(vector)
              .copyBiomes(copyBiomes)
              .copyEntities(copyEntities)
              .ignoreAirBlocks(ignoreAirBlocks)
              .build();
      Operations.complete(operation);
    }
    return vector;
  }

  /**
   * Checks if a location is empty based on the clipboard content.
   *
   * @param world The WorldEdit world to check.
   * @param blockVector3 The BlockVector3 representing the location to check.
   * @param clipboard The clipboard containing the schematic data.
   * @return True if the location is empty or contains fewer than 15 blocks, false otherwise.
   */
  public boolean isLocationEmpty(World world, BlockVector3 blockVector3, Clipboard clipboard) {
    Set<Block> set = new HashSet<>();
    org.bukkit.World bukkitWorld = Bukkit.getWorld(world.getName());

    Validate.notNull(bukkitWorld, "World cannot be null");

    try (EditSession editSession =
                 WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1)) {
      Operation testOperation =
              new ClipboardHolder(clipboard)
                      .createPaste(
                              new AbstractDelegateExtent(editSession) {
                                @Override
                                public <T extends BlockStateHolder<T>> boolean setBlock(
                                        BlockVector3 location, T block) {
                                  Block blockAt =
                                          bukkitWorld.getBlockAt(
                                                  new Location(
                                                          Bukkit.getWorld(world.getName()),
                                                          location.getX(),
                                                          location.getY(),
                                                          location.getZ()));

                                  if (!blockAt.getType().isAir()) {
                                    set.add(blockAt);
                                  }
                                  return true;
                                }
                              })
                      .to(blockVector3)
                      .copyBiomes(false)
                      .copyEntities(false)
                      .ignoreAirBlocks(true)
                      .build();
      Operations.complete(testOperation);
    } catch (WorldEditException exception) {
      exception.printStackTrace();
    }
    return set.isEmpty() || set.size() <= 15;
  }
}