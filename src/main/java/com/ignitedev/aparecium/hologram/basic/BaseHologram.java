package com.ignitedev.aparecium.hologram.basic;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.hologram.data.HologramEntry;
import com.ignitedev.aparecium.hologram.factory.HologramFactory;
import com.ignitedev.aparecium.interfaces.Destroyable;
import com.ignitedev.aparecium.interfaces.Identifiable;
import com.ignitedev.aparecium.interfaces.Updatable;
import com.ignitedev.aparecium.util.text.Placeholder;
import java.util.List;
import org.bukkit.Location;

/**
 * @implNote base hologram representation
 */
public interface BaseHologram extends Identifiable, Updatable, Cloneable, Destroyable {

  List<HologramEntry> getLines();

  HologramEntry getLine(int index);

  HologramEntry setLine(int index, HologramEntry hologramEntry);

  List<Placeholder> getPlaceholderData();

  Placeholder getPlaceholderData(String key);

  void setPlaceholderData(String key, String value);

  Location getLocation();

  void spawn(Aparecium apareciumInstance);

  void spawn(HologramFactory factory, Aparecium apareciumInstance);

  BaseHologram clone();

  boolean isSpawned();
}
