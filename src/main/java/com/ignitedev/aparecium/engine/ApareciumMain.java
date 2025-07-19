/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.engine;

/*
 * "She tapped the diary three times and said, "Aparecium!" Nothing happened."
 *
 *  ~~ Hermione using the spell on Tom Riddle's diary
 */

import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.command.ItemBaseCommand;
import com.ignitedev.aparecium.command.custom.CustomCommand;
import com.ignitedev.aparecium.command.custom.CustomCommandProcessor;
import com.ignitedev.aparecium.config.CustomCommandsBase;
import com.ignitedev.aparecium.item.interaction.listener.ItemInteractionListener;
import com.ignitedev.aparecium.logging.HedwigLogger;
import com.ignitedev.aparecium.util.ReflectionUtility;
import com.twodevsstudio.simplejsonconfig.SimpleJSONConfig;
import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.def.Serializer;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Locale;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;

/**
 * @implNote Aparecium Main engine, this is implementation of Aparecium, as well it is perfect
 *     example of how to set up Aparecium
 */
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

    Serializer.getInst().setGson(new ApareciumGsonBuilder().build());
    SimpleJSONConfig.INSTANCE.register(this, new File(getDataFolder(), "/aparecium"));

    registerCommands();
    registerListeners(Bukkit.getPluginManager());
    registerCustomCommands();
    initializeDirectories();
  }

  @Override
  public void onDisabling() {}

  private void initializeDirectories() {
    File file = new File(getDataFolder(), "schematics");

    if (file.mkdirs()) {
      HedwigLogger.getMainLogger().info("Created Schematics directory");
    }
  }

  private void registerCommands() {
    PaperCommandManager paperCommandManager = new PaperCommandManager(this);

    paperCommandManager.addSupportedLanguage(Locale.ENGLISH);
    paperCommandManager.setFormat(
        MessageType.ERROR,
        ChatColor.BLACK,
        ChatColor.DARK_BLUE,
        ChatColor.DARK_GREEN,
        ChatColor.DARK_AQUA,
        ChatColor.DARK_RED,
        ChatColor.DARK_PURPLE,
        ChatColor.GOLD,
        ChatColor.GRAY,
        ChatColor.DARK_GRAY,
        ChatColor.BLUE,
        ChatColor.GREEN,
        ChatColor.AQUA,
        ChatColor.RED,
        ChatColor.LIGHT_PURPLE,
        ChatColor.YELLOW,
        ChatColor.WHITE);

    paperCommandManager.registerCommand(new ItemBaseCommand());
  }

  private void registerListeners(PluginManager pluginManager) {
    pluginManager.registerEvents(new ItemInteractionListener(), this);
  }

  @SneakyThrows
  private void registerCustomCommands() {
    CustomCommandsBase commandsBase = Config.getConfig(CustomCommandsBase.class);
    Field bukkitCommandMap =
        ReflectionUtility.getField(Bukkit.getServer().getClass(), "commandMap");
    CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

    for (CustomCommand value : commandsBase.getSavedCommands().values()) {
      commandMap.register(
          value.getCommandName(),
          new CustomCommandProcessor(
              value.getCommandName(),
              "Permission: " + value.getCommandPermission(),
              value.getCommandName(),
              value.getCommandAliases()));
    }
  }
}
