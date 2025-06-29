/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.hologram.factory;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.hologram.basic.BaseHologram;
import com.ignitedev.aparecium.hologram.data.HologramEntry;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.internal.HolographicDisplaysAPIProvider;
import org.bukkit.inventory.ItemStack;

public class DefaultHologramFactory implements HologramFactory {
  @Override
  public Hologram createHologram(BaseHologram baseHologram, Aparecium apareciumInstance) {
    Hologram hologram =
        HolographicDisplaysAPIProvider.getImplementation()
            .getHolographicDisplaysAPI(apareciumInstance)
            .createHologram(baseHologram.getLocation());

    for (HologramEntry line : baseHologram.getLines()) {
      if (line.getMaterial() != null) {
        hologram.getLines().appendItem(new ItemStack(line.getMaterial()));
      } else {
        ApareciumComponent lineFinal = line.getLine();

        if (lineFinal != null) {
          hologram.getLines().appendText(lineFinal.getAsString());
        }
      }
    }
    return hologram;
  }
}
