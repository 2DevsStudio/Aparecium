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
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class WorldGuardUtility {

  private static final RegionContainer container =
      WorldGuard.getInstance().getPlatform().getRegionContainer();

  /**
   * Gets the regions a player is currently in.
   *
   * @param playerUUID UUID of the player
   * @return Set of WorldGuard protected regions that the player is currently in.
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
   * Gets the regions names a player is currently in.
   *
   * @param playerUUID UUID of the player
   * @return Set of Strings with the names of the regions the player is currently in.
   */
  @Nonnull
  public Set<String> getRegionsNames(UUID playerUUID) {
    return getRegions(playerUUID).stream().map(ProtectedRegion::getId).collect(Collectors.toSet());
  }

  /**
   * Checks whether a player is in one or several regions
   *
   * @param playerUUID UUID of the player
   * @param regionNames Set of regions to check.
   * @return True if the player is in (all) the named region(s).
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
   * Checks whether a player is in one or several regions
   *
   * @param playerUUID UUID of the player.
   * @param regionNames Set of regions to check.
   * @return True if the player is in (any of) the named region(s).
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
   * Checks whether a player is in one or several regions
   *
   * @param playerUUID UUID of the player
   * @param regionName List of regions to check.
   * @return True if the player is in (any of) the named region(s).
   */
  public boolean isPlayerInAnyRegion(UUID playerUUID, String... regionName) {
    return isPlayerInAnyRegion(playerUUID, new HashSet<>(Arrays.asList(regionName)));
  }

  /**
   * Checks whether a player is in one or several regions
   *
   * @param playerUUID UUID of the player
   * @param regionName List of regions to check.
   * @return True if the player is in (any of) the named region(s).
   */
  public boolean isPlayerInAllRegions(UUID playerUUID, String... regionName) {
    return isPlayerInAllRegions(playerUUID, new HashSet<>(Arrays.asList(regionName)));
  }

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
