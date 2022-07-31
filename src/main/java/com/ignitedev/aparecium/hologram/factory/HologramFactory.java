package com.ignitedev.aparecium.hologram.factory;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.factory.Factory;
import com.ignitedev.aparecium.hologram.basic.BaseHologram;

public interface HologramFactory extends Factory {

  Hologram createHologram(BaseHologram baseHologram, Aparecium apareciumInstance);
}
