package com.ignitedev.aparecium.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import com.ignitedev.aparecium.Constants;
import com.ignitedev.aparecium.config.ItemBase;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.util.MessageUtility;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import java.util.Map.Entry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("itembase|ib|itemsbase|ibase|itemsb")
@CommandPermission("aparecium.itembase")
public class ItemBaseCommand extends BaseCommand {

  @Autowired
  private static ItemBase itemBase;

  @Default
  @HelpCommand
  public void onHelp(Player player){
    //
  }

  @Subcommand("list")
  public void onList(Player player){
    StringBuilder stringBuilder = new StringBuilder("<red>Items \n");
    int rowCounter = 0;

    for (Entry<String, MagicItem> entry : itemBase.getSavedItems().entrySet()) {
      if(rowCounter >= Constants.ITEMBASE_ITEMS_IN_ROW){
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
    MessageUtility.send(player, stringBuilder.toString());
  }

  @Subcommand("get")
  public void onGet(Player player, String id, @Optional int amount){
    MagicItem byId = itemBase.getById(id);
    ItemStack item = byId.toItemStack(amount <= 0 ? 1 : amount);

    player.getInventory().addItem(item);
  }
}
