/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.factory;

import com.ignitedev.aparecium.hologram.factory.DefaultHologramFactory;
import com.ignitedev.aparecium.hologram.factory.HologramFactories;
import com.ignitedev.aparecium.item.factory.MagicItemFactories;
import com.ignitedev.aparecium.item.factory.factories.ClickableItemFactory;
import com.ignitedev.aparecium.item.factory.factories.DefaultMagicItemFactory;
import com.ignitedev.aparecium.item.factory.factories.DropItemFactory;
import com.ignitedev.aparecium.item.factory.factories.LayoutItemFactory;
import com.ignitedev.aparecium.item.factory.factories.PatternItemFactory;
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
  private LayoutItemFactory layoutItemFactory;
  private DropItemFactory dropItemFactory;
  private PatternItemFactory patternItemFactory;

  private ClickableItemFactory clickableItemFactory;

  /**
   * @implNote creating factories instances, this method should be only called on runtime
   */
  public void createFactories() {
    this.hologramFactories = new HologramFactories();
    this.hologramFactories.registerFactory("DEFAULT", new DefaultHologramFactory());
    //
    this.magicItemFactories = new MagicItemFactories();
    this.magicItemFactories.registerFactory("DEFAULT", new DefaultMagicItemFactory<>());

    this.layoutItemFactory = new LayoutItemFactory();
    this.dropItemFactory = new DropItemFactory();
    this.patternItemFactory = new PatternItemFactory();
    this.clickableItemFactory = new ClickableItemFactory();

    this.magicItemFactories.registerFactory("LAYOUT_DEFAULT", layoutItemFactory);
    this.magicItemFactories.registerFactory("DROP_DEFAULT", dropItemFactory);
    this.magicItemFactories.registerFactory("PATTERN_DEFAULT", patternItemFactory);
  }
}
