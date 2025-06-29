/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.command.custom;

import com.ignitedev.aparecium.config.CustomCommandsBase;
import com.twodevsstudio.simplejsonconfig.api.Config;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CustomCommandProcessor extends BukkitCommand {

  public CustomCommandProcessor(
      @NotNull String name,
      @NotNull String description,
      @NotNull String usageMessage,
      @NotNull List<String> aliases) {
    super(name, description, usageMessage, aliases);
  }

  @Override
  public boolean execute(
      @NotNull CommandSender commandSender, @NotNull String label, @NotNull String @NotNull [] arguments) {
    CustomCommand customCommand =
        Config.getConfig(CustomCommandsBase.class).getById(this.getName());

    if (customCommand != null) {
      customCommand.send((Player) commandSender);
      return true;
    }
    return false;
  }
}
