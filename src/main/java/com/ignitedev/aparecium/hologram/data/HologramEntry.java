/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.hologram.data;

import lombok.Data;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("ClassCanBeRecord")
@Data
public final class HologramEntry {

  /**
   * @implNote text of hologram
   */
  private final @Nullable String line;
  /**
   * @implNote item as hologram line
   */
  private final @Nullable Material material;
}
