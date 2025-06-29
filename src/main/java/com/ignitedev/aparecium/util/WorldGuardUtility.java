/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import com.ignitedev.aparecium.base.Cuboid;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class for interacting with WorldGuard regions.
 * Provides methods for retrieving regions, checking player presence in regions, and creating new regions.
 */
@UtilityClass
public class WorldGuardUtility {

  /** Container for managing WorldGuard regions. */
  private static final RegionContainer container =
          WorldGuard.getInstance().getPlatform().getRegionContainer();

  /**
   * Retrieves the applicable region at a specific location.
   *
   * @param location The location to check for regions.
   * @return The first applicable region at the location, or null if no region is found.
   */
  @Nullable
  public ProtectedRegion getRegionByLocation(Location location) {
    RegionManager regionManager = container.get(BukkitAdapter.adapt(location.getWorld()));

    if (regionManager == null) {
      return null;
    }
    return regionManager
            .getApplicableRegions(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
            .getRegions()
            .stream()
            .findFirst()
            .orElse(null);
  }

  /**
   * Retrieves the regions a player is currently in.
   *
   * @param playerUUID The UUID of the player.
   * @return A set of WorldGuard protected regions the player is currently in.
   */
  @Nonnull
  public Set<ProtectedRegion> getRegions(UUID playerUUID) {
    Player player = Bukkit.getPlayer(playerUUID);

    if (player == null || !player.isOnline()) {
      return Collections.emptySet();
    }
    return container
            .createQuery()
            .getApplicableRegions(BukkitAdapter.adapt(player.getLocation()))
            .getRegions();
  }

  /**
   * Retrieves the names of the regions a player is currently in.
   *
   * @param playerUUID The UUID of the player.
   * @return A set of strings containing the names of the regions the player is currently in.
   */
  @Nonnull
  public Set<String> getRegionsNames(UUID playerUUID) {
    return getRegions(playerUUID).stream().map(ProtectedRegion::getId).collect(Collectors.toSet());
  }

  /**
   * Checks whether a player is in all specified regions.
   *
   * @param playerUUID The UUID of the player.
   * @param regionNames A set of region names to check.
   * @return True if the player is in all specified regions, false otherwise.
   * @throws IllegalArgumentException If no regions are provided for checking.
   */
  public boolean isPlayerInAllRegions(UUID playerUUID, Set<String> regionNames) {
    Set<String> regions = getRegionsNames(playerUUID);

    if (regions.isEmpty()) {
      throw new IllegalArgumentException("You need to check for at least one region !");
    }
    return regions.containsAll(
            regionNames.stream().map(String::toLowerCase).collect(Collectors.toSet()));
  }

  /**
   * Checks whether a player is in any of the specified regions.
   *
   * @param playerUUID The UUID of the player.
   * @param regionNames A set of region names to check.
   * @return True if the player is in any of the specified regions, false otherwise.
   * @throws IllegalArgumentException If no regions are provided for checking.
   */
  public boolean isPlayerInAnyRegion(UUID playerUUID, Set<String> regionNames) {
    Set<String> regions = getRegionsNames(playerUUID);

    if (regions.isEmpty()) {
      throw new IllegalArgumentException("You need to check for at least one region !");
    }
    for (String region : regionNames) {
      if (regions.contains(region.toLowerCase())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks whether a player is in any of the specified regions.
   *
   * @param playerUUID The UUID of the player.
   * @param regionName A list of region names to check.
   * @return True if the player is in any of the specified regions, false otherwise.
   */
  public boolean isPlayerInAnyRegion(UUID playerUUID, String... regionName) {
    return isPlayerInAnyRegion(playerUUID, new HashSet<>(Arrays.asList(regionName)));
  }

  /**
   * Checks whether a player is in all specified regions.
   *
   * @param playerUUID The UUID of the player.
   * @param regionName A list of region names to check.
   * @return True if the player is in all specified regions, false otherwise.
   */
  public boolean isPlayerInAllRegions(UUID playerUUID, String... regionName) {
    return isPlayerInAllRegions(playerUUID, new HashSet<>(Arrays.asList(regionName)));
  }

  /**
   * Creates a new WorldGuard region based on a cuboid.
   *
   * @param cuboidId The ID of the new region.
   * @param cuboid The cuboid defining the region boundaries.
   * @param flags A map of flags to set for the region (optional).
   * @throws Exception If an error occurs while creating the region.
   */
  @SneakyThrows
  public void createRegion(
          @NotNull String cuboidId, @NotNull Cuboid cuboid, @Nullable Map<Flag<?>, Object> flags) {
    Location firstCorner = cuboid.getMinimum();
    Location secondCorner = cuboid.getMaximum();
    BlockVector3 minimum =
            BlockVector3.at(firstCorner.getX(), firstCorner.getY(), firstCorner.getZ());
    BlockVector3 maximum =
            BlockVector3.at(secondCorner.getX(), secondCorner.getY(), secondCorner.getZ());
    RegionManager regionManager = container.get(BukkitAdapter.adapt(firstCorner.getWorld()));

    if (regionManager == null || regionManager.hasRegion(cuboidId)) {
      return;
    }
    ProtectedRegion region = new ProtectedCuboidRegion(cuboidId, minimum, maximum);

    if (flags != null) {
      region.setFlags(flags);
    }
    regionManager.addRegion(region);
    regionManager.save();
  }
}