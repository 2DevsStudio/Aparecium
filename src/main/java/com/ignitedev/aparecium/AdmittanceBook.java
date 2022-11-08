/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium;

/*
 * "Yer mad. His name's been down ever since he was born.
 *  He's off ter the finest school of witchcraft and wizardry in the world!"
 *
 *  ~~Rubeus Hagrid to Vernon Dursley about Harry Potter
 */

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

/**
 * @implNote This class is used for caching plugins using Aparecium, for better management
 */
@NoArgsConstructor
public class AdmittanceBook {

  @Getter private static final AdmittanceBook admittanceBook = new AdmittanceBook();

  private final HedwigLogger apareciumHedwigLogger = HedwigLogger.getMainLogger();

  /**
   * @implNote <Plugin Name, Plugin Instance>
   */
  private final Map<String, Aparecium> pluginCache = new HashMap<>();

  /**
   * @param pluginName plugin instance name
   * @param pluginInstance instance of plugin you want to register, if plugin already exists it
   *     won't be cached again
   */
  public void cachePlugin(String pluginName, Aparecium pluginInstance) {
    pluginCache.putIfAbsent(pluginName, pluginInstance);
    apareciumHedwigLogger.info("Plugin " + pluginName + " Cached");
  }

  /**
   * @implNote you can use pluginName of aparecium, one of them can be null
   * @param pluginName name of plugin instance you want to remove {@link Aparecium#getName()}
   * @param aparecium instance of Aparecium you want to remove
   */
  public void removePluginCache(@Nullable String pluginName, @Nullable Aparecium aparecium) {
    check(pluginName);
    if (aparecium != null) {
      check(aparecium.getName());
    }
  }

  private void check(@Nullable String keyName) {
    if (keyName != null) {
      if (!pluginCache.containsKey(keyName)) {
        apareciumHedwigLogger.error("This plugin is not in the cache!");
        return;
      }
      pluginCache.remove(keyName);
    }
  }
}
