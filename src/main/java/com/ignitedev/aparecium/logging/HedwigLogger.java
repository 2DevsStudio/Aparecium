/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.logging;

/*
 * "Very smart ow`l` you've got there."
 *
 *  ~~ Tom noting Hedwig's impressive intelligence
 */

import com.ignitedev.aparecium.Aparecium;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * @implNote This class is used as Logger for your plugin as well for whole Aparecium, you can
 *     obtain it instances with {@link #getOrCreate(Aparecium)} or{@link #getMainLogger()}} for main
 *     aparecium logger, by using this one you are logging messages as Aparecium, not your plugin
 */
public class HedwigLogger {

  @Getter private static final Map<Aparecium, HedwigLogger> hedwigCache = new HashMap<>();

  /**
   * @implNote Hedwig logger that's global for Aparecium
   */
  @Getter private static HedwigLogger mainLogger;

  @Setter private Logger logger;

  /**
   * @param aparecium your main class instance
   * @return hedwig saved cached for aparecium or new dedicated hedwig
   */
  public static HedwigLogger getOrCreate(@NotNull Aparecium aparecium) {
    if (hedwigCache.containsKey(aparecium)) {
      return hedwigCache.get(aparecium);
    }
    HedwigLogger hedwigLogger = new HedwigLogger();

    hedwigLogger.setLogger(aparecium.getLogger());
    hedwigCache.put(aparecium, hedwigLogger);

    return hedwigLogger;
  }

  public static void initializeMainLogger(HedwigLogger hedwigLogger, @NotNull Aparecium aparecium) {
    hedwigLogger.setLogger(aparecium.getLogger());
    mainLogger = hedwigLogger;
  }

  public void info(String text) {
    logger.log(Level.INFO, text);
  }

  public void error(String text) {
    logger.log(Level.SEVERE, text);
  }

  public void warning(String text) {
    logger.log(Level.WARNING, text);
  }
}
