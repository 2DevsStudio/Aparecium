/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.hologram.factory;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.hologram.basic.BaseHologram;
import com.ignitedev.aparecium.hologram.data.HologramEntry;
import org.bukkit.inventory.ItemStack;

public class DefaultHologramFactory implements HologramFactory {
  @Override
  public Hologram createHologram(BaseHologram baseHologram, Aparecium apareciumInstance) {
    Hologram hologram = HologramsAPI.createHologram(apareciumInstance, baseHologram.getLocation());

    for (HologramEntry line : baseHologram.getLines()) {
      if (line.getMaterial() != null) {
        hologram.appendItemLine(new ItemStack(line.getMaterial()));
      } else {
        hologram.appendTextLine(line.getLine());
      }
    }
    return hologram;
  }
}
