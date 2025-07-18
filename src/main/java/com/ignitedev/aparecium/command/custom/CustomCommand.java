/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.command.custom;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.config.CustomCommandsBase;
import com.ignitedev.aparecium.interfaces.Sendable;
import com.ignitedev.aparecium.util.MessageUtility;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import java.util.List;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @implNote Custom command system for simple plain commands with command execution or text only
 */
@Data
public class CustomCommand implements Sendable<Player> {

  @Autowired private static CustomCommandsBase customCommandsBase;

  @NotNull private final String commandName;
  @Nullable private final String commandPermission;

  @NotNull private final List<String> commandAliases;

  /**
   * @implNote this messages command sender is going to receive on command execution, if message
   *     starts with '/' then it is treated as command, and it will be executed as player, if
   *     message starts with '@/' then it will be executed as console, you can use {PLAYER}
   *     placeholder to get executing player nickname
   */
  @NotNull private final List<String> commandResponse;

  /**
   * @param player command executor
   * @implNote for more information look at {@link #commandResponse comment}
   */
  @Override
  public Player send(Player player) {
    for (String value : commandResponse) {
      if (this.commandPermission != null && !player.hasPermission(this.commandPermission)) {
        // if player has no permission then we are not executing anything
        MessageUtility.send(player, customCommandsBase.getNoPermissionMessage());
        break;
      }
      // replacing placeholders
      value = value.replace("{PLAYER}", player.getName());

      if (value.startsWith("@/")) {
        // performing commands as console
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), value.substring(2));
      } else if (value.startsWith("/")) {
        // performing commands as player
        player.performCommand(value.substring(1));
      } else {
        MessageUtility.send(player, ApareciumComponent.of(value));
      }
    }
    return player;
  }
}
