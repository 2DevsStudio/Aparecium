package com.ignitedev.aparecium.command.custom;

import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

public class CustomCommandProcessor extends BukkitCommand {

  protected CustomCommandProcessor(@NotNull String name, @NotNull String description, @NotNull String usageMessage,
      @NotNull List<String> aliases) {
    super(name, description, usageMessage, aliases);
  }

  @Override
  public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] arguments) {
    return false;
  }
}
