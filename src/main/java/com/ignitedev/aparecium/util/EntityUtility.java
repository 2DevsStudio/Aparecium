/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import com.ignitedev.aparecium.util.enums.DayCycle;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class providing helper methods for entity manipulation.
 */
@SuppressWarnings("unused")
@UtilityClass
public class EntityUtility {

  /**
   * Pushes an entity forward based on its current direction and the specified velocity.
   *
   * @param entity The entity to push forward.
   * @param velocity The velocity to apply to the entity.
   */
  public void pushEntityForward(@NotNull Entity entity, double velocity) {
    entity.setVelocity(entity.getLocation().getDirection().multiply(velocity));
  }

  /**
   * Checks if entities can spawn in the current world time based on the specified day cycle.
   *
   * @param dayCycle The day cycle defining the time range for spawning.
   * @param world The world to check the time in.
   * @return True if the current world time is within the specified day cycle, false otherwise.
   */
  public boolean canSpawnAtCurrentWorldTime(@NotNull DayCycle dayCycle, @NotNull World world) {
    long worldTime = world.getTime();

    return worldTime > dayCycle.getMinTime() && worldTime < dayCycle.getMaxTime();
  }

  /**
   * Finds a living entity by its UUID across all worlds.
   *
   * @param entityUUID The UUID of the entity to find.
   * @return The living entity if found, or null if not found.
   */
  @Nullable
  public LivingEntity findLivingEntityInAllWorlds(UUID entityUUID) {
    for (World world : Bukkit.getWorlds()) {
      Entity entity = world.getEntity(entityUUID);

      if (entity instanceof LivingEntity) {
        return ((LivingEntity) entity);
      }
    }
    return null;
  }

  /**
   * Finds an entity by its UUID across all worlds.
   *
   * @param entityUUID The UUID of the entity to find.
   * @return The entity if found, or null if not found.
   */
  @Nullable
  public Entity findEntityInAllWorlds(UUID entityUUID) {
    for (World world : Bukkit.getWorlds()) {
      Entity entity = world.getEntity(entityUUID);

      if (entity != null) {
        return entity;
      }
    }
    return null;
  }
}