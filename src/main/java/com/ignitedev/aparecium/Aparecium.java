/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium;

import com.google.common.base.Stopwatch;
import com.ignitedev.aparecium.enums.StartupStage;
import com.ignitedev.aparecium.factory.FactoriesManager;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * "The Revealing Charm will reveal invisible ink and messages hidden by magical means.
 *  Simply tap a book or parchment with your wand and any hidden message will be revealed.
 *  This spell is more than sufficient to overcome the basic concealing charms and so is a
 *  favourite of parents and teachers alike."
 *
 *  ~~ Miranda Goshawk, Book of Spells
 */

/**
 * @implNote This class is heart of your plugin, you need to extend this class instead of
 *     JavaPlugin, don't worry, Aparecium extends Java Plugin, so you'll have access to JavaPlugin
 *     methods as well as extra our methods
 */
public abstract class Aparecium extends JavaPlugin {

  @Getter private static final FactoriesManager factoriesManager = new FactoriesManager();

  @Getter private static final boolean usingPaper = checkPaper();

  private final Stopwatch stopwatch = Stopwatch.createUnstarted();

  private final HedwigLogger hedwigLogger;

  @Getter private StartupStage startupStage;

  public Aparecium() {
    this.hedwigLogger = HedwigLogger.getOrCreate(this);
  }

  /*
   * Aparecium logic
   */

  @Override
  public void onLoad() {
    this.startupStage = StartupStage.PRE_LOAD;
    measure(this.stopwatch, this.hedwigLogger, this::onPreLoad);

    this.startupStage = StartupStage.INNER_LOAD;
    measure(this.stopwatch, this.hedwigLogger, this::onInnerLoad);

    this.startupStage = StartupStage.POST_LOAD;
    measure(this.stopwatch, this.hedwigLogger, this::onPostLoad);
  }

  @Override
  public void onEnable() {
    this.startupStage = StartupStage.ENABLING;

    // Aparecium logic
    factoriesManager.createFactories();

    // plugin logic
    measure(this.stopwatch, this.hedwigLogger, this::onEnabling);

    this.startupStage = StartupStage.ENABLED;
    // Aparecium logic
    AdmittanceBook.getAdmittanceBook().cachePlugin(this.getName(), this);
  }

  @Override
  public void onDisable() {
    this.startupStage = StartupStage.DISABLING;

    // Aparecium logic

    // plugin logic
    measure(this.stopwatch, this.hedwigLogger, this::onDisabling);

    this.startupStage = StartupStage.DISABLED;
    // Aparecium logic
    AdmittanceBook.getAdmittanceBook().removePluginCache(this.getName(), this);
  }

  /*
   * Plugin Logic
   */

  /** LOAD STAGE 1 */
  public abstract void onPreLoad();

  /** LOAD STAGE 2 */
  public abstract void onInnerLoad();

  /** LOAD STAGE 3 */
  public abstract void onPostLoad();

  /** Starting Plugin */
  public abstract void onEnabling();

  /** Disabling Plugin */
  public abstract void onDisabling();

  /*
   * Aparecium logic
   */

  /**
   * @param stopWatch stopwatch instance can be obtained by {@link Stopwatch#createUnstarted()}
   * @param hedwigLogger your hedwig to log console measure, can be obtained by {@link
   *     HedwigLogger#getOrCreate(Aparecium)}
   * @param method your runnable to measure
   */
  @SneakyThrows
  public void measure(Stopwatch stopWatch, HedwigLogger hedwigLogger, Runnable method) {
    stopWatch.reset();
    stopWatch.start();
    method.run();
    stopWatch.stop();
    hedwigLogger.info(
        startupStage.name()
            + " took: "
            + stopWatch.elapsed(TimeUnit.MILLISECONDS)
            + " milliseconds");
  }

  public double getLastMinuteTPS() {
    return getServer().getTPS()[0];
  }

  public double getLastFiveMinuteTPS() {
    return getServer().getTPS()[1];
  }

  public double getLastTenMinuteTPS() {
    return getServer().getTPS()[2];
  }

  /**
   * @implNote send small message in console about your tps from (1, 5, 10) minutes
   */
  public void checkTPSInConsole() {
    hedwigLogger.info(
        "Last Minute TPS: "
            + getLastMinuteTPS()
            + "\n Last 5 Minute TPS: "
            + getLastFiveMinuteTPS()
            + "\n Last 10 Minute TPS: "
            + getLastTenMinuteTPS());
  }

  private static boolean checkPaper() {
    try {
      Class.forName("com.destroystokyo.paper.ClientOption");
      return true;
    } catch (ClassNotFoundException ignored) {
    }
    return false;
  }
}
