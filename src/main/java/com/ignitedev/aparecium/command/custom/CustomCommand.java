package com.ignitedev.aparecium.command.custom;

import com.ignitedev.aparecium.interfaces.Sendable;
import com.ignitedev.aparecium.util.MessageUtility;
import java.util.List;
import lombok.Data;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @implNote Custom command system for simple plain commands with command execution or text only
 */
@Data
public class CustomCommand implements Sendable<Audience> {

  @NotNull private final String commandName;
  @NotNull private final List<String> commandAliases;
  @Nullable private final String commandPermission;

  /**
   * @implNote this messages command sender is going to receive on command execution, if message
   *     starts with '/' then it is treated as command, and it will be executed as player, if
   *     message starts with '@/' then it will be executed as console, you can use {PLAYER}
   *     placeholder to get executing player nickname
   */
  @NotNull private final List<String> commandResponse;

  /**
   * @param audience command executor
   * @implNote for more information look at {@link #commandResponse comment}
   */
  @Override
  public Audience send(Audience audience) {
    for (String value : commandResponse) {
      if (audience instanceof Player player) {
        if (this.commandPermission != null && !player.hasPermission(this.commandPermission)) {
          // if player has no permission then we are not executing anything
          // todo maybe add message (?)
          break;
        }
        // replacing placeholders
        value = value.replace("{PLAYER}", player.getName());
      }
      if (value.startsWith("@/")) {
        // performing commands as console
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), value.substring(2));
      } else if (value.startsWith("/")) {
        // performing commands as player
        if (audience instanceof Player player) {
          player.performCommand(value.substring(1));
        }
      } else {
        MessageUtility.send(audience, value);
      }
    }
    return audience;
  }
}
