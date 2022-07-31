package com.ignitedev.aparecium.util;

import com.ignitedev.aparecium.util.text.Placeholder;
import com.ignitedev.aparecium.util.text.TextUtility;
import java.util.List;
import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

@SuppressWarnings("unused")
@UtilityClass
public class MessageUtility {

  public void send(CommandSender target, String text, Placeholder... placeholders) {
    if (text.equalsIgnoreCase("{BLANK}")) {
      return;
    }
    if(target instanceof OfflinePlayer offlinePlayer){
      text = PlaceholderAPI.setPlaceholders(offlinePlayer, TextUtility.replace(text, placeholders));
    }
    target.sendMessage(TextUtility.parseMiniMessage(TextUtility.colorize(text)));
  }

  public void send(CommandSender target, List<String> text, Placeholder... placeholders) {
    text.forEach(message -> send(target, message, placeholders));
  }

  public void sendConsole(String text) {
    send(Bukkit.getConsoleSender(), text);
  }

  public void sendConsole(List<String> text) {
    send(Bukkit.getConsoleSender(), text);
  }
}
