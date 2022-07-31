package com.ignitedev.aparecium.base;

/*
 * “We are only as strong as we are united, as weak as we are divided.”
 *
 *  ~~ Albus Dumbledore, Harry Potter and the Goblet of Fire, Chapter 37
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @implNote Cuboid class represent cube with minimum starting location and maximum starting
 *     location, keep in mind that minimum location coordinates should be lower than maximum
 *     coordinates
 */
@SuppressWarnings("ClassCanBeRecord")
@Data
public final class Cuboid implements Cloneable {

  private final Location minimum;
  private final Location maximum;

  /**
   * @implNote Cuboid class represent cube with minimum starting location and maximum starting
   *     location, keep in mind that minimum location coordinates should be lower than maximum
   *     coordinates
   */
  public Cuboid(@NotNull Location minimum, @NotNull Location maximum) {
    if (minimum.getX() > maximum.getX()) {
      double x = minimum.getX();
      minimum.setX(maximum.getX());
      maximum.setX(x);
    }
    if (minimum.getY() > maximum.getY()) {
      double y = minimum.getY();
      minimum.setY(maximum.getY());
      maximum.setY(y);
    }
    if (minimum.getZ() > maximum.getZ()) {
      double z = minimum.getZ();
      minimum.setZ(maximum.getZ());
      maximum.setZ(z);
    }
    this.minimum = minimum;
    this.maximum = maximum;
  }

  /**
   * @return if set location is inside this Cuboid bounds then it's true
   */
  public boolean isInside(@NotNull Location location) {
    int minX = minimum.getBlockX();
    int maxX = maximum.getBlockX();
    int x = location.getBlockX();

    int minY = minimum.getBlockY();
    int maxY = maximum.getBlockY();
    int y = location.getBlockY();

    int minZ = minimum.getBlockZ();
    int maxZ = maximum.getBlockZ();
    int z = location.getBlockZ();
    return ((x >= minX) && (x <= maxX))
        && ((y >= minY) && (y <= maxY))
        && ((z >= minZ) && (z <= maxZ));
  }

  /**
   * @param countAir if true it will count air(empty) blocks as well
   * @return number of blocks in cuboid bounds
   */
  public int getBlockCount(boolean countAir) {
    Location copy = minimum.clone();
    int count = 0;

    for (int x = minimum.getBlockX(); x <= maximum.getBlockX(); x++) {
      for (int z = minimum.getBlockZ(); z <= maximum.getBlockZ(); z++) {
        for (int y = minimum.getBlockY(); y <= maximum.getBlockY(); y++) {
          copy.setX(x);
          copy.setY(y);
          copy.setZ(z);
          copy.getBlock();

          if (copy.getBlock().getType() == Material.AIR) {
            if (countAir) {
              count++;
            }
          } else {
            count++;
          }
        }
      }
    }
    return count;
  }

  /**
   * @return collection of blocks inside cuboid bounds
   */
  public List<Block> getBlocks() {
    List<Block> blockList = new ArrayList<>();

    for (int x = minimum.getBlockX(); x <= maximum.getBlockX(); x++) {
      for (int y = minimum.getBlockY(); y <= maximum.getBlockY(); y++) {
        for (int z = minimum.getBlockZ(); z <= maximum.getBlockZ(); z++) {
          Location location = new Location(minimum.getWorld(), x, y, z);
          Block block = location.getBlock();
          blockList.add(block);
        }
      }
    }
    return blockList;
  }

  /**
   * @implNote set all blocks in cuboid bounds to AIR
   */
  public void clearSpace() {
    List<Block> cuboidBLocks = getBlocks();
    for (Block block : cuboidBLocks) {
      block.setType(Material.AIR);
    }
  }

  /**
   * @implNote send to each block on cuboid particle
   */
  public void sendParticles(Particle particle) {
    for (int x = minimum.getBlockX(); x <= maximum.getBlockX(); x++) {
      for (int y = minimum.getBlockY(); y <= maximum.getBlockY(); y++) {
        for (int z = minimum.getBlockZ(); z <= maximum.getBlockZ(); z++) {
          Location location = new Location(minimum.getWorld(), x, y, z);
          location.getWorld().spawnParticle(particle, location, 1);
        }
      }
    }
  }

  /**
   * @return center of cuboid cube
   */
  public Location getCenter() {
    int minX = minimum.getBlockX();
    int minY = minimum.getBlockY();
    int minZ = minimum.getBlockZ();
    int x1 = maximum.getBlockX() + 1;
    int y1 = maximum.getBlockY() + 1;
    int z1 = maximum.getBlockZ() + 1;

    return new Location(
        minimum.getWorld(),
        minX + (x1 - minX) / 2.0D,
        minY + (y1 - minY) / 2.0D,
        minZ + (z1 - minZ) / 2.0D);
  }

  /**
   * @return get List of all dropped items on cuboid bounds
   */
  @NotNull
  public List<Item> getItemsOnGround() {
    return minimum
        .getWorld()
        .getNearbyEntities(
            getCenter(),
            minimum.distance(maximum) / 2,
            minimum.distance(maximum) / 2,
            minimum.distance(maximum) / 2)
        .stream()
        .filter(entity -> entity instanceof Item)
        .map(entity -> (Item) entity)
        .collect(Collectors.toList());
  }

  /**
   * @implNote check if location is safe for player-like entity spawning
   */
  public boolean isSafe(Location location) {
    return location.getBlock().getType().isAir()
        && location.clone().subtract(0, 1, 0).getBlock().getType().isAir()
        && location.clone().add(0, 1, 0).getBlock().getType().isAir();
  }

  /**
   * @return safe location for spawning entity on cuboid cube ground
   */
  @Nullable
  public Location findSafePlace() {
    Location center = getCenter();

    if (isSafe(center)) {
      return center.getWorld().getHighestBlockAt(center).getLocation();
    }
    for (int x = minimum.getBlockX(); x <= maximum.getBlockX(); x++) {
      for (int z = minimum.getBlockZ(); z <= maximum.getBlockZ(); z++) {
        Block highestBlockAt = minimum.getWorld().getHighestBlockAt(x, z);

        if (isSafe(highestBlockAt.getLocation())) {
          return highestBlockAt.getLocation();
        }
      }
    }
    return null;
  }

  @Override
  public Cuboid clone() throws CloneNotSupportedException {
    return ((Cuboid) super.clone());
  }
}
