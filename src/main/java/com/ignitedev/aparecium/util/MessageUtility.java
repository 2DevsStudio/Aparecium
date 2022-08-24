/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.util;

import com.ignitedev.aparecium.util.text.Placeholder;
import com.ignitedev.aparecium.util.text.TextUtility;
import java.util.List;
import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

@SuppressWarnings("unused")
@UtilityClass
public class MessageUtility {

  public void send(Audience target, String text, Placeholder... placeholders) {
    if (text.equalsIgnoreCase("{BLANK}")) {
      return;
    }
    if(target instanceof OfflinePlayer offlinePlayer){
      text = PlaceholderAPI.setPlaceholders(offlinePlayer, TextUtility.replace(text, placeholders));
    }
    target.sendMessage(TextUtility.parseMiniMessage(TextUtility.colorize(text)));
  }

  public void send(Audience target, List<String> text, Placeholder... placeholders) {
    text.forEach(message -> send(target, message, placeholders));
  }

  public void sendConsole(String text) {
    send(Bukkit.getConsoleSender(), text);
  }

  public void sendConsole(List<String> text) {
    send(Bukkit.getConsoleSender(), text);
  }
}
