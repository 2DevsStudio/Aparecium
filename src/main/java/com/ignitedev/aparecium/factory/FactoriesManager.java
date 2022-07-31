package com.ignitedev.aparecium.factory;

import com.ignitedev.aparecium.hologram.factory.DefaultHologramFactory;
import com.ignitedev.aparecium.hologram.factory.HologramFactories;
import com.ignitedev.aparecium.item.factory.MagicItemFactories;
import com.ignitedev.aparecium.item.factory.factories.DefaultMagicItemFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @implNote this class holds current type of factories used in Aparecium
 */
@NoArgsConstructor
@Getter
public class FactoriesManager {

  private HologramFactories hologramFactories;
  private MagicItemFactories magicItemFactories;

  /**
   * @implNote creating factories instances, this method should be only called on runtime
   */
  public void createFactories() {
    hologramFactories = new HologramFactories();
    hologramFactories.registerFactory("DEFAULT", new DefaultHologramFactory());
    //
    magicItemFactories = new MagicItemFactories();
    magicItemFactories.registerFactory("DEFAULT", new DefaultMagicItemFactory());
  }
}
