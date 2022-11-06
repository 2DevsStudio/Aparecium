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
}
