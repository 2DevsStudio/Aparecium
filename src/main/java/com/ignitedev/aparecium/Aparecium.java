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

  @Getter private StartupStage startupStage;

  /*
   * JavaPlugin logic
   */

  private static boolean checkPaper() {
    try {
      Class.forName("com.destroystokyo.paper.ClientOption");
      return true;
    } catch (ClassNotFoundException ignored) {
      return false;
    }
  }

  @Override
  public void onLoad() {
    this.startupStage = StartupStage.PRE_LOAD;
    measure(this.stopwatch, this::onPreLoad);

    this.startupStage = StartupStage.INNER_LOAD;
    measure(this.stopwatch, this::onInnerLoad);

    this.startupStage = StartupStage.POST_LOAD;
    measure(this.stopwatch, this::onPostLoad);
  }

  @Override
  public void onEnable() {
    this.startupStage = StartupStage.ENABLING;

    // Aparecium logic
    factoriesManager.createFactories();

    // plugin logic
    measure(this.stopwatch, this::onEnabling);

    this.startupStage = StartupStage.ENABLED;
    // Aparecium logic
    HedwigLogger hedwigLogger = new HedwigLogger();

    hedwigLogger.initializeMainLogger(hedwigLogger, this);

    AdmittanceBook.getAdmittanceBook().cachePlugin(this.getName(), this);
  }

  /*
   * Aparecium logic
   */

  @Override
  public void onDisable() {
    this.startupStage = StartupStage.DISABLING;

    // Aparecium logic

    // plugin logic
    measure(this.stopwatch, this::onDisabling);

    this.startupStage = StartupStage.DISABLED;
    // Aparecium logic
    AdmittanceBook.getAdmittanceBook().removePluginCache(this.getName(), this);
  }

  /** LOAD STAGE 1 */
  public abstract void onPreLoad();

  /** LOAD STAGE 2 */
  public abstract void onInnerLoad();

  /** LOAD STAGE 3 */
  public abstract void onPostLoad();

  /*
   * Aparecium logic
   */

  /** Starting Plugin */
  public abstract void onEnabling();

  /** Disabling Plugin */
  public abstract void onDisabling();

  /**
   * @param stopWatch stopwatch instance can be obtained by {@link Stopwatch#createUnstarted()}
   * @param method your runnable to measure
   * @return how many milliseconds action took
   */
  @SneakyThrows
  public long measure(Stopwatch stopWatch, Runnable method) {
    stopWatch.reset();
    stopWatch.start();
    method.run();
    stopWatch.stop();
    return stopWatch.elapsed(TimeUnit.MILLISECONDS);
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
    HedwigLogger.getMainLogger()
        .info(
            "Last Minute TPS: "
                + getLastMinuteTPS()
                + "\n Last 5 Minute TPS: "
                + getLastFiveMinuteTPS()
                + "\n Last 10 Minute TPS: "
                + getLastTenMinuteTPS());
  }
}
