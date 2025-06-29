/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.util.text.TextUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class for manipulating and calculating locations in the Minecraft world.
 * Provides methods for distance calculations, conversions, and various location-based operations.
 */
@SuppressWarnings("unused")
@UtilityClass
public class LocationUtility {

  /** Array of axis-aligned block faces. */
  private final BlockFace[] axis = {
          BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST
  };

  /** Array of radial block faces (including diagonals). */
  private final BlockFace[] radial = {
          BlockFace.NORTH,
          BlockFace.NORTH_EAST,
          BlockFace.EAST,
          BlockFace.SOUTH_EAST,
          BlockFace.SOUTH,
          BlockFace.SOUTH_WEST,
          BlockFace.WEST,
          BlockFace.NORTH_WEST
  };

  /**
   * Calculates the 2D distance between two locations (X and Z axes only).
   *
   * @param firstLocation The first location.
   * @param secondLocation The second location.
   * @return The 2D distance between the locations.
   */
  public double calculateDistance2D(Location firstLocation, Location secondLocation) {
    return Math.sqrt(Math.pow(firstLocation.getX() - secondLocation.getX(), 2))
            + Math.sqrt(Math.pow(firstLocation.getZ() - secondLocation.getZ(), 2));
  }

  /**
   * Calculates the 3D distance between two locations.
   *
   * @param firstLocation The first location.
   * @param secondLocation The second location.
   * @return The 3D distance between the locations.
   */
  public double calculateDistance3D(Location firstLocation, Location secondLocation) {
    return firstLocation.distance(secondLocation);
  }

  /**
   * Converts a string to a Location object using a custom splitter.
   *
   * @param string The string representing the location.
   * @param splitter The delimiter used in the string.
   * @return The Location object, or null if conversion fails.
   */
  @Nullable
  public Location fromString(String string, String splitter) {
    String[] split = string.split(splitter);
    World world = Bukkit.getWorld(split[0]);

    if (world == null) {
      return null;
    }

    String x = split[1];
    String y = split[2];
    String z = split[3];

    if (!StringUtils.isNumeric(x) || !StringUtils.isNumeric(y) || !StringUtils.isNumeric(z)) {
      return null;
    }

    return new Location(world, Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z));
  }

  /**
   * Converts a string to a Location object using the default splitter ",".
   *
   * @param string The string representing the location.
   * @return The Location object, or null if conversion fails.
   */
  @Nullable
  public Location fromString(String string) {
    return fromString(string, ",");
  }

  /**
   * Converts a Location object to a string using a custom splitter.
   *
   * @param location The location to convert.
   * @param splitter The delimiter to use in the string.
   * @return The string representation of the location.
   */
  public String toString(Location location, String splitter) {
    return location.getWorld().getName()
            + splitter
            + location.getBlockX()
            + splitter
            + location.getBlockY()
            + splitter
            + location.getBlockZ();
  }

  /**
   * Converts a Location object to a string using the default splitter ",".
   *
   * @param location The location to convert.
   * @return The string representation of the location.
   */
  public String toString(Location location) {
    return toString(location, ",");
  }

  /**
   * Converts a Location object to a more readable string.
   *
   * @param location The location to convert.
   * @param withWorld Whether to include the world name in the output.
   * @return A readable string representation of the location.
   */
  public String toFancyString(Location location, boolean withWorld) {
    String output = "";

    if (withWorld) {
      output = location.getWorld().getName() + " ";
    }
    return (output
            + "X: "
            + location.getBlockX()
            + " Y: "
            + location.getBlockY()
            + " Z: "
            + location.getBlockZ());
  }

  /**
   * Checks if the second location is within a radius of the first location.
   *
   * @param firstLocation The first location.
   * @param radius The radius.
   * @param secondLocation The second location.
   * @return True if the second location is within the radius, false otherwise.
   */
  public boolean isWithinRadius(
          @NotNull Location firstLocation, double radius, @NotNull Location secondLocation) {

    if (firstLocation.getWorld() != secondLocation.getWorld()) {
      return false;
    } else {
      double dis = firstLocation.distanceSquared(secondLocation);
      return dis <= (radius * radius);
    }
  }

  /**
   * Checks if the given location is safe for landing (e.g., not air).
   *
   * @param location The location to check.
   * @return True if the location is safe, false otherwise.
   */
  public boolean isLandSafeLocation(@NotNull Location location) {
    return isSafeLocation(location, Material.AIR);
  }

  /**
   * Checks if the given location is safe for landing on liquid (e.g., water).
   *
   * @param location The location to check.
   * @return True if the location is safe, false otherwise.
   */
  public boolean isLiquidSafeLocation(@NotNull Location location) {
    return isSafeLocation(location, Material.WATER);
  }

  /**
   * Checks if the location is safe with respect to the given material.
   *
   * @param location The location to check.
   * @param safeMaterial The material considered safe.
   * @return True if the location is safe, false otherwise.
   */
  public boolean isSafeLocation(@NotNull Location location, @NotNull Material safeMaterial) {
    for (int x = -1; x <= 1; x++) {
      for (int y = 0; y <= 1; y++) {
        for (int z = -1; z <= 1; z++) {
          Location checkLocation = location.clone();
          checkLocation.add(x, y, z);
          if (isSafeMaterial(location, safeMaterial)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Checks if the block at the given location is not the specified material.
   *
   * @param location The location to check.
   * @param safeMaterial The material considered safe.
   * @return True if the block is not the material, false otherwise.
   */
  public boolean isSafeMaterial(@NotNull Location location, @NotNull Material safeMaterial) {
    return location.getBlock().getType() != safeMaterial;
  }

  /**
   * Checks if two locations have the same coordinates.
   *
   * @param firstLocation The first location.
   * @param secondLocation The second location.
   * @return True if the locations are identical, false otherwise.
   */
  public boolean isSameLocation(Location firstLocation, Location secondLocation) {
    return firstLocation.getX() == secondLocation.getX()
            && firstLocation.getY() == secondLocation.getY()
            && firstLocation.getZ() == secondLocation.getZ();
  }

  /**
   * Picks a random location within a given radius and Y range.
   *
   * @param world The world to search in.
   * @param baseX The base X coordinate.
   * @param baseZ The base Z coordinate.
   * @param radius The search radius.
   * @param minY The minimum Y value.
   * @param maxY The maximum Y value.
   * @return A random location matching the criteria.
   */
  @NotNull
  public Location decideLocation(
          World world, int baseX, int baseZ, int radius, int minY, int maxY) {
    return decideLocation(world, baseX, radius, baseZ, radius, minY, maxY);
  }

  /**
   * Picks a random location within a given coordinate and Y range, with optional safety check.
   *
   * @param world The world to search in.
   * @param minX The minimum X value.
   * @param maxX The maximum X value.
   * @param minZ The minimum Z value.
   * @param maxZ The maximum Z value.
   * @param minY The minimum Y value.
   * @param maxY The maximum Y value.
   * @param safeCheck Whether to check for a safe location.
   * @return A random location matching the criteria.
   */
  @NotNull
  public Location decideLocation(
          World world, int minX, int maxX, int minZ, int maxZ, int minY, int maxY, boolean safeCheck) {
    while (true) {
      int ranX = adjustedRandom(minX, maxX);
      int ranZ = adjustedRandom(minZ, maxZ);

      if (world.isChunkLoaded(ranX, ranZ)) {
        @NotNull Block highestY = world.getHighestBlockAt(ranX, ranZ);
        int highestBlockYAt = world.getHighestBlockYAt(ranX, ranZ);

        if (!highestY.getType().isAir() && highestBlockYAt >= minY && highestBlockYAt <= maxY) {
          Location location = new Location(world, ranX, highestY.getLocation().getBlockY(), ranZ);

          if (safeCheck) {
            if (isLandSafeLocation(location)) {
              return location;
            }
          }
        }
      }
    }
  }

  /**
   * Picks a random location within a given coordinate and Y range.
   *
   * @param world The world to search in.
   * @param minX The minimum X value.
   * @param maxX The maximum X value.
   * @param minZ The minimum Z value.
   * @param maxZ The maximum Z value.
   * @param minY The minimum Y value.
   * @param maxY The maximum Y value.
   * @return A random location matching the criteria.
   */
  @NotNull
  public Location decideLocation(
          World world, int minX, int maxX, int minZ, int maxZ, int minY, int maxY) {
    return decideLocation(world, minX, maxX, minZ, maxZ, minY, maxY, false);
  }

  /**
   * Returns a random integer in the range from -max to max, with base offset.
   *
   * @param base The base value.
   * @param max The maximum value.
   * @return A random integer.
   */
  public int adjustedRandom(int base, int max) {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    int maxOpp = -max;
    int baseOpp = -base;
    int randomNum = random.nextInt(maxOpp, max);

    if (randomNum < base && randomNum >= baseOpp) {
      return randomNum <= 0 ? randomNum - base : randomNum + base;
    }
    return randomNum;
  }

  /**
   * Converts a yaw value to a BlockFace direction.
   *
   * @param yaw The yaw value.
   * @param useSubCardinalDirections Whether to use sub-cardinal directions.
   * @return The corresponding BlockFace.
   */
  public BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
    if (useSubCardinalDirections) {
      return radial[Math.round(yaw / 45f) & 0x7].getOppositeFace();
    }
    return axis[Math.round(yaw / 90f) & 0x3].getOppositeFace();
  }

  /**
   * Returns the first world with the given environment type.
   *
   * @param environment The environment type (e.g., NORMAL, NETHER, THE_END).
   * @return The first found world or null if not found.
   */
  @Nullable
  public World getFirstWorldByEnvironment(Environment environment) {
    for (World world : Bukkit.getWorlds()) {
      if (world.getEnvironment() == environment) {
        return world;
      }
    }
    return null;
  }

  /**
   * Generates a list of locations forming a sphere of the given radius around the center.
   *
   * @param center The center of the sphere.
   * @param radius The radius of the sphere.
   * @return List of locations forming the sphere.
   */
  public List<Location> getSphere(Location center, Integer radius) {
    List<Location> sphereLocations = new ArrayList<>();

    for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
      double circleRadius = Math.sin(radius);
      double y = Math.cos(i);

      for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
        sphereLocations.add(
                new Location(
                        center.getWorld(), (Math.cos(a) * circleRadius), y, (Math.sin(a) * circleRadius)));
      }
    }
    return sphereLocations;
  }

  /**
   * Generates a list of locations forming a circle of the given radius and number of points.
   *
   * @param center The center of the circle.
   * @param radius The radius of the circle.
   * @param amount The number of points on the circle.
   * @return List of locations on the circle.
   */
  public List<Location> getCircle(Location center, double radius, int amount) {
    double increment = (2 * Math.PI) / amount;
    List<Location> locations = new ArrayList<>();

    for (int i = 0; i < amount; i++) {
      double angle = i * increment;
      double x = center.getX() + (radius * Math.cos(angle));
      double z = center.getZ() + (radius * Math.sin(angle));

      locations.add(new Location(center.getWorld(), x, center.getY(), z));
    }
    return locations;
  }

  /**
   * Returns the block behind the player at a given distance.
   *
   * @param player The player.
   * @param distance The distance behind the player.
   * @return The block behind the player or null if it is air.
   */
  @Nullable
  public Block getBlockBehindPlayer(Player player, Number distance) {
    Block block =
            player
                    .getLocation()
                    .add(
                            player
                                    .getLocation()
                                    .getDirection()
                                    .normalize()
                                    .multiply(distance.doubleValue() * (-1d)))
                    .getBlock();

    if (block.getType().isAir()) {
      return null;
    }
    return block;
  }

  /**
   * Returns the location behind the player at a given distance.
   *
   * @param player The player.
   * @param distance The distance behind the player.
   * @return The location behind the player.
   */
  public Location getLocationBehindPlayer(Player player, Number distance) {
    return player
            .getLocation()
            .add(player.getLocation().getDirection().multiply(distance.doubleValue() * (-1d)));
  }

  /**
   * Teleports a player to a location, loading the chunk asynchronously if necessary.
   *
   * @param player The player to teleport.
   * @param location The target location.
   * @param aparecium The plugin instance for scheduling tasks.
   */
  public void teleportWithChunkLoad(Player player, Location location, Aparecium aparecium) {
    int x = location.getBlockX() >> 4;
    int z = location.getBlockZ() >> 4;

    if (location.getWorld().isChunkLoaded(x, z)) {
      player.teleport(location);
    } else {
      CompletableFuture<Chunk> chunkAtAsync = location.getWorld().getChunkAtAsync(location);

      chunkAtAsync.thenAccept(
              chunk -> Bukkit.getScheduler().runTask(aparecium, () -> player.teleport(location)));
    }
  }

  /**
   * Returns a graphical direction arrow between two locations.
   *
   * @param from The starting location.
   * @param to The target location.
   * @return The direction arrow as a String.
   */
  public String getDirectionArrow(Location from, Location to) {
    if (from == null || to == null || !from.getWorld().equals(to.getWorld())) {
      return "?";
    }
    from = from.clone();
    to = to.clone();

    if (!from.getWorld().equals(to.getWorld())) {
      return "?";
    }
    from.setY(0);
    to.setY(0);

    Vector d = from.getDirection();
    Vector v = to.subtract(from).toVector().normalize();
    double a = Math.toDegrees(Math.atan2(d.getX(), d.getZ()));
    a -= Math.toDegrees(Math.atan2(v.getX(), v.getZ()));
    a = (int) (a + 22.5) % 360;

    if (a < 0) {
      a += 360;
    }
    return TextUtility.colorize("&l" + "⬆⬈➡⬊⬇⬋⬅⬉".charAt((int) a / 45));
  }

  /**
   * Returns a list of blocks around a given location within a specified radius.
   *
   * @param center The center location.
   * @param radius The radius.
   * @return List of blocks around the center.
   */
  public List<Block> getBlocksAround(Location center, double radius) {
    int offset = (int) Math.floor(radius);
    return getBlocksAround(center, radius, offset, offset, offset);
  }

  /**
   * Returns a list of blocks around a given location with specified offsets.
   *
   * @param center The center location.
   * @param radius The radius.
   * @param offsetX The X offset.
   * @param offsetY The Y offset.
   * @param offsetZ The Z offset.
   * @return List of blocks around the center.
   */
  public List<Block> getBlocksAround(
          Location center, double radius, int offsetX, int offsetY, int offsetZ) {
    List<Block> result = new ArrayList<>();

    for (int x = -offsetX; x <= offsetX; x++) {
      for (int z = -offsetY; z <= offsetY; z++) {
        for (int y = -offsetZ; y <= offsetZ; y++) {
          Location location =
                  new Location(
                          center.getWorld(), center.getX() + x, center.getY() + y, center.getZ() + z);

          if (distance(location, center) <= radius) {
            result.add(location.getBlock());
          }
        }
      }
    }
    return result;
  }

  /**
   * Calculates the distance between two locations, returns -1 if locations are invalid or in different worlds.
   *
   * @param firstLocation The first location.
   * @param secondLocation The second location.
   * @return The distance or -1 if it cannot be calculated.
   */
  public static double distance(Location firstLocation, Location secondLocation) {
    return firstLocation == null
            || secondLocation == null
            || firstLocation.getWorld() == null
            || secondLocation.getWorld() == null
            || !firstLocation.getWorld().equals(secondLocation.getWorld())
            ? -1.0D
            : firstLocation.distance(secondLocation);
  }
}