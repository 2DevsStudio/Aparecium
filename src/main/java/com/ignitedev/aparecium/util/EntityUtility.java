/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
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

@SuppressWarnings("unused")
@UtilityClass
public class EntityUtility {

  public void pushEntityForward(@NotNull Entity entity, double velocity) {
    entity.setVelocity(entity.getLocation().getDirection().multiply(velocity));
  }

  public boolean canSpawnAtCurrentWorldTime(@NotNull DayCycle dayCycle, @NotNull World world) {
    long worldTime = world.getTime();

    return worldTime > dayCycle.getMinTime() && worldTime < dayCycle.getMaxTime();
  }

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
