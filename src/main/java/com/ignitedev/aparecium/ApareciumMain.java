/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium;

/*
 * "She tapped the diary three times and said, "Aparecium!" Nothing happened."
 *
 *  ~~ Hermione using the spell on Tom Riddle's diary
 */

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @implNote Aparecium Main engine, this is implementation of Aparecium, as well it is perfect
 *     example of how to set up Aparecium
 */
@NoArgsConstructor
public class ApareciumMain extends Aparecium {

  @Getter private static ApareciumMain instance;

  @Override
  public void onPreLoad() {}

  @Override
  public void onInnerLoad() {}

  @Override
  public void onPostLoad() {}

  @Override
  public void onEnabling() {
    instance = this;
  }

  @Override
  public void onDisabling() {}
}
