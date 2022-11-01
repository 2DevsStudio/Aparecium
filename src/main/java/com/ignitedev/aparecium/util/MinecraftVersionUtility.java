/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;

@SuppressWarnings("unused")
@UtilityClass
public class MinecraftVersionUtility {

  private final String stringVersion;
  private final int intVersion;

  static {
    String fullVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    stringVersion = StringUtils.replace(fullVersion, "_", " ");
    intVersion =
        Integer.parseInt(StringUtils.replace(fullVersion.split("_")[1].split("_R")[0], "v", ""));
  }

  public boolean is(int version2) {
    return intVersion == version2;
  }

  public boolean isAfter(int version2) {
    return intVersion > version2;
  }

  public boolean isBefore(int version2) {
    return intVersion < version2;
  }

  public boolean isOrAfter(int version2) {
    return intVersion == version2 || intVersion > version2;
  }

  public String getStringVersion() {
    return stringVersion;
  }

  public int getIntVersion() {
    return intVersion;
  }
}
