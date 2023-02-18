/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import com.ignitedev.aparecium.engine.ApareciumMain;
import com.ignitedev.aparecium.exception.IncorrectEngineException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaperUtility {

  public void checkPaper() throws IncorrectEngineException {
    if (!ApareciumMain.isUsingPaper()) {
      throw new IncorrectEngineException();
    }
  }

  public boolean checkPaperClass() {
    try {
      Class.forName("com.destroystokyo.paper.ClientOption");
      return true;
    } catch (ClassNotFoundException ignored) {
      return false;
    }
  }
}
