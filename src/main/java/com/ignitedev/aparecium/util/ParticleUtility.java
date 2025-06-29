/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

/**
 * Utility class for handling particle effects in Minecraft.
 * Provides methods to create and display various particle effects.
 */
@UtilityClass
public class ParticleUtility {

  /**
   * Sends a spherical particle effect at the specified location.
   * The sphere is generated using mathematical calculations for its coordinates.
   *
   * @param location The center location of the sphere.
   * @param particle The type of particle to display.
   */
  public void sendSphereParticle(Location location, Particle particle) {
    World world = location.getWorld();
    double r = 1.5; // Radius of the sphere

    // Check if the world is null to avoid errors
    if (world == null) {
      return;
    }

    // Generate the sphere using spherical coordinates
    for (double phi = 0; phi <= Math.PI; phi += Math.PI / 15) {
      double y = r * Math.cos(phi) + 1.5; // Calculate the Y coordinate
      for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 30) {
        double x = r * Math.cos(theta) * Math.sin(phi); // Calculate the X coordinate
        double z = r * Math.sin(theta) * Math.sin(phi); // Calculate the Z coordinate

        // Add the calculated coordinates to the location
        location.add(x, y, z);
        // Spawn the particle at the calculated location
        world.spawnParticle(particle, location, 1, 0F, 0F, 0F, 0.001);
        // Subtract the coordinates to reset the location
        location.subtract(x, y, z);
      }
    }
  }
}