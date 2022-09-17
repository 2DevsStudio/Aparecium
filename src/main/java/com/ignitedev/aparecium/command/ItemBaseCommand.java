package com.ignitedev.aparecium.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.RegisteredCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.ignitedev.aparecium.Constants;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.config.ItemBase;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.util.MessageUtility;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import java.util.Map.Entry;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("itembase|ib|itemsbase|ibase|itemsb")
@CommandPermission("aparecium.itembase")
public class ItemBaseCommand extends BaseCommand {

  @Autowired private static ItemBase itemBase;

  @Default
  @HelpCommand
  @CommandPermission("aparecium.itembase.help")
  public void onHelp(Player player) {
    for (Entry<String, RegisteredCommand> key : this.getSubCommands().entries()) {
      MessageUtility.send(player, ApareciumComponent.of("<blue>/itembase <purple>" + key.getValue().getSyntaxText()));
    }
  }

  @Subcommand("list")
  @CommandPermission("aparecium.itembase.list")
  public void onList(Player player) {
    StringBuilder stringBuilder = new StringBuilder("<red>Items \n");
    int rowCounter = 0;

    for (Entry<String, MagicItem> entry : itemBase.getSavedItems().entrySet()) {
      if (rowCounter >= Constants.ITEMBASE_ITEMS_IN_ROW) {
        stringBuilder.append("\n");
        rowCounter = 0;
      }
      MagicItem value = entry.getValue();

      stringBuilder
          .append(" <blue>")
          .append(entry.getKey())
          .append("<purple> (")
          .append(value.getId())
          .append(",")
          .append(value.getName())
          .append(",")
          .append(value.getItemType())
          .append(",")
          .append(value.getItemSaveInstant().toString())
          .append(" )");

      rowCounter++;
    }
    MessageUtility.send(player, ApareciumComponent.of(stringBuilder.toString()));
  }

  @Subcommand("get")
  @CommandPermission("aparecium.itembase.get")
  public void onGet(Player player, String id, @Optional int amount) {
    itemBase.getById(id).give(player, amount <= 0 ? 1 : amount);
  }

  @Subcommand("give")
  @CommandPermission("aparecium.itembase.give")
  public void onGive(
      CommandSender ignoredCommandSender, OnlinePlayer target, String id, @Optional int amount) {
    itemBase.getById(id).give(target.getPlayer(), amount <= 0 ? 1 : amount);
  }
}
