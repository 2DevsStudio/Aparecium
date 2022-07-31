package com.ignitedev.aparecium.hologram.manager;

import com.ignitedev.aparecium.hologram.basic.BaseHologram;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

/*
 * There was possibly an unidentified projection spell which
 *  was used to project a moving, real-life image to people around the world.
 */
@UtilityClass
public class HologramManager {

  public final List<BaseHologram> ACTIVE_BASE_HOLOGRAMS = new ArrayList<>();

  public void destroyAll() {
    // Hologram.destroy() mutates this list so operation is performed on it's copy
    new ArrayList<>(ACTIVE_BASE_HOLOGRAMS).forEach(BaseHologram::destroy);
    // ACTIVE_BASE_HOLOGRAMS is not updated here because #AbstractBaseHologram performs that
  }

  public void destroy(String id) {
    new ArrayList<>(ACTIVE_BASE_HOLOGRAMS)
        .forEach(
            baseHologram -> {
              if (baseHologram.getId().equalsIgnoreCase(id)) {
                baseHologram.destroy();
              }
            });
  }
}
