/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.hologram;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.hologram.basic.AbstractBaseHologram;
import com.ignitedev.aparecium.hologram.data.HologramEntry;
import com.ignitedev.aparecium.util.text.Placeholder;
import java.util.List;
import lombok.experimental.SuperBuilder;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * @implNote this class should be used for hologram creation by {@link #builder()}
 */
@SuperBuilder(toBuilder = true)
public class SimpleBaseHologram extends AbstractBaseHologram {

  public SimpleBaseHologram(
      String id,
      Location location,
      @NotNull List<HologramEntry> lines,
      @NotNull List<Placeholder> placeholderData) {
    super(id, location, lines, placeholderData);
  }

  public SimpleBaseHologram(String id, Location location, @NotNull List<HologramEntry> lines) {
    super(id, location, lines);
  }

  @Override
  public void spawn(Aparecium apareciumInstance) {
    super.spawn(
        Aparecium.getFactoriesManager().getHologramFactories().getDefaultFactory(),
        apareciumInstance);
  }
}
