/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
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

@SuppressWarnings("unused")
@UtilityClass
public class LocationUtility {

  private final BlockFace[] axis = {
    BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST
  };
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

  public double calculateDistance2D(Location firstLocation, Location secondLocation) {
    return Math.sqrt(Math.pow(firstLocation.getX() - secondLocation.getX(), 2))
        + Math.sqrt(Math.pow(firstLocation.getZ() - secondLocation.getZ(), 2));
  }

  public double calculateDistance3D(Location firstLocation, Location secondLocation) {
    return firstLocation.distance(secondLocation);
  }

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

  @Nullable
  public Location fromString(String string) {
    return fromString(string, ",");
  }

  public String toString(Location location, String splitter) {
    return location.getWorld().getName()
        + splitter
        + location.getBlockX()
        + splitter
        + location.getBlockY()
        + splitter
        + location.getBlockZ();
  }

  public String toString(Location location) {
    return toString(location, ",");
  }

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

  public boolean isWithinRadius(
      @NotNull Location firstLocation, double radius, @NotNull Location secondLocation) {

    if (firstLocation.getWorld() != secondLocation.getWorld()) {
      return false;
    } else {
      double dis = firstLocation.distanceSquared(secondLocation);
      return dis <= (radius * radius);
    }
  }

  public boolean isLandSafeLocation(@NotNull Location location) {
    return isSafeLocation(location, Material.AIR);
  }

  public boolean isLiquidSafeLocation(@NotNull Location location) {
    return isSafeLocation(location, Material.WATER);
  }

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

  public boolean isSafeMaterial(@NotNull Location location, @NotNull Material safeMaterial) {
    return location.getBlock().getType() != safeMaterial;
  }

  public boolean isSameLocation(Location firstLocation, Location secondLocation) {
    return firstLocation.getX() == secondLocation.getX()
        && firstLocation.getY() == secondLocation.getY()
        && firstLocation.getZ() == secondLocation.getZ();
  }

  @NotNull
  public Location decideLocation(
      World world, int baseX, int baseZ, int radius, int minY, int maxY) {
    return decideLocation(world, baseX, radius, baseZ, radius, minY, maxY);
  }

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

  @NotNull
  public Location decideLocation(
      World world, int minX, int maxX, int minZ, int maxZ, int minY, int maxY) {
    return decideLocation(world, minX, maxX, minZ, maxZ, minY, maxY, false);
  }

  public int adjustedRandom(int base, int max) {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    int maxOpp = -max; // get the negative value of max
    int baseOpp = -base; // same
    int randomNum = random.nextInt(maxOpp, max);

    if (randomNum < base && randomNum >= baseOpp) {
      return randomNum <= 0 ? randomNum - base : randomNum + base;
    }
    return randomNum;
  }

  public BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
    if (useSubCardinalDirections) {
      return radial[Math.round(yaw / 45f) & 0x7].getOppositeFace();
    }
    return axis[Math.round(yaw / 90f) & 0x3].getOppositeFace();
  }

  @Nullable
  public World getFirstWorldByEnvironment(Environment environment) {
    for (World world : Bukkit.getWorlds()) {
      if (world.getEnvironment() == environment) {
        return world;
      }
    }
    return null;
  }

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

  public Location getLocationBehindPlayer(Player player, Number distance) {
    return player
        .getLocation()
        .add(player.getLocation().getDirection().multiply(distance.doubleValue() * (-1d)));
  }

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

  public List<Block> getBlocksAround(Location center, double radius) {
    int offset = (int) Math.floor(radius);
    return getBlocksAround(center, radius, offset, offset, offset);
  }

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
