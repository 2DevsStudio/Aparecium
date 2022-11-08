/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

@UtilityClass
public class ParticleUtility {

  public void sendSphereParticle(Location location, Particle particle) {
    World world = location.getWorld();
    double r = 1.5;

    if (world == null) {
      return;
    }
    for (double phi = 0; phi <= Math.PI; phi += Math.PI / 15) {
      double y = r * Math.cos(phi) + 1.5;
      for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 30) {
        double x = r * Math.cos(theta) * Math.sin(phi);
        double z = r * Math.sin(theta) * Math.sin(phi);

        location.add(x, y, z);
        world.spawnParticle(particle, location, 1, 0F, 0F, 0F, 0.001);
        location.subtract(x, y, z);
      }
    }
  }
}
