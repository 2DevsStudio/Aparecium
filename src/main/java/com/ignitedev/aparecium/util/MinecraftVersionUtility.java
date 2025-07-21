/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

/**
 * Utility class for handling Minecraft server version information. Provides methods to compare and
 * retrieve the server version.
 */
@SuppressWarnings("unused")
@UtilityClass
public class MinecraftVersionUtility {

  /** The string representation of the Minecraft server version. */
  private final String stringVersion;

  /** The integer representation of the Minecraft server version. */
  private final int intVersion;

  static {
    // Initialize the version information based on the server's package name.
    String fullVersion = Bukkit.getServer().getVersion().split("-")[0];

    intVersion = Integer.parseInt(fullVersion.split("\\.")[1]);
    stringVersion = fullVersion;
  }

  /**
   * Checks if the current server version matches the specified version.
   *
   * @param version2 The version to compare against.
   * @return True if the current version matches, false otherwise.
   */
  public boolean is(int version2) {
    return intVersion == version2;
  }

  /**
   * Checks if the current server version is after the specified version.
   *
   * @param version2 The version to compare against.
   * @return True if the current version is after, false otherwise.
   */
  public boolean isAfter(int version2) {
    return intVersion > version2;
  }

  /**
   * Checks if the current server version is before the specified version.
   *
   * @param version2 The version to compare against.
   * @return True if the current version is before, false otherwise.
   */
  public boolean isBefore(int version2) {
    return intVersion < version2;
  }

  /**
   * Checks if the current server version matches or is after the specified version.
   *
   * @param version2 The version to compare against.
   * @return True if the current version matches or is after, false otherwise.
   */
  public boolean isOrAfter(int version2) {
    return intVersion == version2 || intVersion > version2;
  }

  /**
   * Retrieves the string representation of the Minecraft server version.
   *
   * @return The string version of the server.
   */
  public String getStringVersion() {
    return stringVersion;
  }

  /**
   * Retrieves the integer representation of the Minecraft server version.
   *
   * @return The integer version of the server.
   */
  public int getIntVersion() {
    return intVersion;
  }
}
