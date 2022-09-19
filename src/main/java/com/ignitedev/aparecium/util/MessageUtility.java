/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.util;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.util.text.Placeholder;
import com.ignitedev.aparecium.util.text.TextUtility;
import java.util.List;
import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

@SuppressWarnings("unused")
@UtilityClass
public class MessageUtility {

  public void send(Audience target, ApareciumComponent text, Placeholder... placeholders) {
    String messageString = text.getAsString();

    if (target instanceof OfflinePlayer offlinePlayer) {
      String placeholdersString = TextUtility.replace(text, placeholders).getAsString();

      if (placeholdersString != null) {
        text =
            ApareciumComponent.of(
                PlaceholderAPI.setPlaceholders(offlinePlayer, placeholdersString));
      }
    }
    // PAPER CODE
    if (Aparecium.isUsingPaper()) {
      Component asComponents = text.getAsComponent();

      if (asComponents != null) {
        target.sendMessage(asComponents);
      }
    }
    // END OF PAPER CODE
    if (messageString != null) {
      if (messageString.equalsIgnoreCase("{BLANK}")) {
        return;
      }
      target.sendMessage(TextUtility.parseMiniMessage(messageString));
    }
  }

  public void sendApareciumComponents(
      Audience target, List<ApareciumComponent> text, Placeholder... placeholders) {
    text.forEach(message -> send(target, message, placeholders));
  }

  public void send(Audience target, Component text, Placeholder... placeholders) {
    if (TextUtility.serializeComponent(text).equalsIgnoreCase("{BLANK}")) {
      return;
    }
    if (target instanceof OfflinePlayer offlinePlayer) {
      String placeholdersString =
          TextUtility.replace(ApareciumComponent.of(text), placeholders).getAsString();

      if (placeholdersString != null) {
        text =
            TextUtility.parseMiniMessage(
                PlaceholderAPI.setPlaceholders(offlinePlayer, placeholdersString));
      }
    }
    send(target, ApareciumComponent.of(text), placeholders);
  }

  public void sendComponents(Audience target, List<Component> text, Placeholder... placeholders) {
    text.forEach(message -> send(target, message, placeholders));
  }

  public void sendConsole(ApareciumComponent text) {
    send(Bukkit.getConsoleSender(), text);
  }

  public void sendConsole(List<ApareciumComponent> text) {
    sendApareciumComponents(Bukkit.getConsoleSender(), text);
  }
}
