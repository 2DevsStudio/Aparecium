/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import com.fastasyncworldedit.core.FaweAPI;
import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.engine.ApareciumMain;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.AbstractDelegateExtent;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
@UtilityClass
public class SchematicUtility {

  public void asyncPaste(
      String schematic,
      Location location,
      Consumer<Void> callback,
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

  public void asyncPaste(
      String schematic,
      Location location,
      Consumer<Void> callback,
      Aparecium yourPlugin,
      boolean copyBiomes,
      boolean copyEntities,
      boolean ignoreAirBlocks) {
    new BukkitRunnable() {
      @Override
      public void run() {
        paste(schematic, location, yourPlugin, copyBiomes, copyEntities, ignoreAirBlocks);

        if (callback == null) {
          return;
        }
        // run sync
        new BukkitRunnable() {
          @Override
          public void run() {
            callback.accept(null);
          }
        }.runTask(yourPlugin);
      }
    }.runTaskLaterAsynchronously(yourPlugin, 20);
  }

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

  @SneakyThrows
  @Nullable
  public BlockVector3 paste(
      String schematic,
      Location location,
      Aparecium yourPlugin,
      boolean copyBiomes,
      boolean copyEntities,
      boolean ignoreAirBlocks) {
    File file = new File(yourPlugin.getDataFolder(), "schematics/" + schematic);

    if (file.mkdirs()) {
      Bukkit.getLogger().log(Level.INFO, "Created Schematics directory");
    }
    if (!file.exists()) {
      Bukkit.getLogger()
          .log(
              Level.WARNING,
              "Cannot paste schematic, {0} because the file is not in the Schematics " + "folder!",
              schematic);
      return null;
    }
    World world = FaweAPI.getWorld(location.getWorld().getName());
    BlockVector3 vector = BlockVector3.at(location.getX(), location.getY(), location.getZ());
    Clipboard clipboard = FaweAPI.load(file);

    Validate.notNull(world, "World cannot be null");

    try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
      Operation operation =
          new ClipboardHolder(clipboard)
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
