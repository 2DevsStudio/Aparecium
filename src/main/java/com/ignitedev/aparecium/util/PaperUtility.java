/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import com.ignitedev.aparecium.engine.ApareciumMain;
import com.ignitedev.aparecium.exception.IncorrectEngineException;
import lombok.experimental.UtilityClass;

/**
 * Utility class for checking compatibility with the Paper engine.
 * Provides methods to verify if the server is running Paper or supports Paper-specific classes.
 */
@UtilityClass
public class PaperUtility {

  /**
   * Checks if the server is using the Paper engine.
   * Throws an exception if the server is not running Paper.
   *
   * @throws IncorrectEngineException if the server is not using Paper.
   */
  public void checkPaper() throws IncorrectEngineException {
    if (!ApareciumMain.isUsingPaper()) {
      throw new IncorrectEngineException();
    }
  }

  /**
   * Checks if a specific Paper class is available on the server.
   * This method verifies the presence of the `com.destroystokyo.paper.ClientOption` class.
   *
   * @return True if the class is found, false otherwise.
   */
  public boolean checkPaperClass() {
    try {
      Class.forName("com.destroystokyo.paper.ClientOption");
      return true;
    } catch (ClassNotFoundException ignored) {
      return false;
    }
  }
}