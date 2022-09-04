/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium;

/*
 * "She tapped the diary three times and said, "Aparecium!" Nothing happened."
 *
 *  ~~ Hermione using the spell on Tom Riddle's diary
 */

import com.ignitedev.aparecium.command.custom.CustomCommand;
import com.ignitedev.aparecium.command.custom.CustomCommandProcessor;
import com.ignitedev.aparecium.config.CustomCommandsBase;
import com.ignitedev.aparecium.util.ReflectionUtility;
import com.twodevsstudio.simplejsonconfig.SimpleJSONConfig;
import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.def.Serializer;
import java.lang.reflect.Field;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

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
    SimpleJSONConfig.INSTANCE.register(this);

    registerCustomCommands();
  }

  @Override
  public void onDisabling() {}

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
