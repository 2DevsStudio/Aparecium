/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.enums;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum ItemType {
  COMMON,
  TOOL,
  WEAPON,
  ARMOR,
  POTION,
  LAYOUT;

  public static ItemType getByMaterial(Material material) {
    String name = material.name().toLowerCase();

    if (name.contains("sword") || name.contains("bow") || name.contains("crossbow")) {
      return ItemType.WEAPON;
    } else if (name.contains("boots")
        || name.contains("chestplate")
        || name.contains("leggings")
        || name.contains("helmet")
        || name.contains("shield")) {
      return ItemType.ARMOR;
    } else if (name.contains("shovel")
        || name.contains("hoe")
        || name.contains("axe")
        || name.contains("pickaxe")) {
      return ItemType.TOOL;
    } else if (name.contains("potion")) {
      return ItemType.POTION;
    }
    return ItemType.COMMON;
  }
}
