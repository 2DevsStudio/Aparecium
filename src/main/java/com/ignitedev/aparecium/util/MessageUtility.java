/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.interfaces.PaperOnly;
import com.ignitedev.aparecium.util.text.Placeholder;
import com.ignitedev.aparecium.util.text.TextUtility;
import java.util.List;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Utility class for sending messages to players.
 * Supports placeholders, components, and various formats.
 */
@SuppressWarnings("unused")
@UtilityClass
public class MessageUtility {

  /**
   * Sends a message to a player with optional placeholders.
   *
   * @param target The player to send the message to.
   * @param text The message as an ApareciumComponent.
   * @param placeholders Optional placeholders to replace in the message.
   */
  public void send(Player target, ApareciumComponent text, Placeholder... placeholders) {
    String messageString = text.getAsString();
    String placeholdersString = TextUtility.replace(text, placeholders).getAsString();

    if (placeholdersString != null) {
      text = ApareciumComponent.of(PlaceholderAPI.setPlaceholders(target, placeholdersString));
    }
    // PAPER CODE
    if (Aparecium.isUsingPaper()) {
      Component asComponents = text.getAsComponent();

      if (asComponents != null) {
        target.sendMessage(asComponents);
        return;
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

  /**
   * Sends a message to an audience using Paper API with optional placeholders.
   *
   * @param target The audience to send the message to.
   * @param text The message as an ApareciumComponent.
   * @param placeholders Optional placeholders to replace in the message.
   * @throws Exception If Paper API is not available.
   */
  @SneakyThrows
  @PaperOnly
  public void send(Audience target, ApareciumComponent text, Placeholder... placeholders) {
    PaperUtility.checkPaper();
    send(((Player) target), text, placeholders);
  }

  /**
   * Sends a message to an audience using Paper API with optional placeholders.
   *
   * @param target The audience to send the message to.
   * @param text The message as a String.
   * @param placeholders Optional placeholders to replace in the message.
   * @throws Exception If Paper API is not available.
   */
  @SneakyThrows
  @PaperOnly
  public void send(Audience target, String text, Placeholder... placeholders) {
    PaperUtility.checkPaper();
    send(target, ApareciumComponent.of(text), placeholders);
  }

  /**
   * Sends a message to a player with optional placeholders.
   *
   * @param target The player to send the message to.
   * @param text The message as a String.
   * @param placeholders Optional placeholders to replace in the message.
   */
  public void send(Player target, String text, Placeholder... placeholders) {
    send(target, ApareciumComponent.of(text), placeholders);
  }

  /**
   * Sends a list of messages to an audience using Paper API with optional placeholders.
   *
   * @param target The audience to send the messages to.
   * @param text The list of messages as Strings.
   * @param placeholders Optional placeholders to replace in the messages.
   * @throws Exception If Paper API is not available.
   */
  @PaperOnly
  @SneakyThrows
  public void send(Audience target, List<String> text, Placeholder... placeholders) {
    PaperUtility.checkPaper();
    text.forEach(message -> send(target, ApareciumComponent.of(message), placeholders));
  }

  /**
   * Sends a list of messages to a player with optional placeholders.
   *
   * @param target The player to send the messages to.
   * @param text The list of messages as Strings.
   * @param placeholders Optional placeholders to replace in the messages.
   */
  public void send(Player target, List<String> text, Placeholder... placeholders) {
    text.forEach(message -> send(target, ApareciumComponent.of(message), placeholders));
  }

  /**
   * Sends a list of ApareciumComponents to an audience using Paper API with optional placeholders.
   *
   * @param target The audience to send the components to.
   * @param text The list of ApareciumComponents.
   * @param placeholders Optional placeholders to replace in the components.
   * @throws Exception If Paper API is not available.
   */
  @SneakyThrows
  @PaperOnly
  public void sendApareciumComponents(
          Audience target, List<ApareciumComponent> text, Placeholder... placeholders) {
    PaperUtility.checkPaper();
    text.forEach(message -> send(target, message, placeholders));
  }

  /**
   * Sends a list of ApareciumComponents to a player with optional placeholders.
   *
   * @param target The player to send the components to.
   * @param text The list of ApareciumComponents.
   * @param placeholders Optional placeholders to replace in the components.
   */
  public void sendApareciumComponents(
          Player target, List<ApareciumComponent> text, Placeholder... placeholders) {
    text.forEach(message -> send(target, message, placeholders));
  }

  /**
   * Sends a Component message to an audience using Paper API with optional placeholders.
   *
   * @param target The audience to send the message to.
   * @param text The message as a Component.
   * @param placeholders Optional placeholders to replace in the message.
   * @throws Exception If Paper API is not available.
   */
  @PaperOnly
  @SneakyThrows
  public void send(Audience target, Component text, Placeholder... placeholders) {
    PaperUtility.checkPaper();

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

  /**
   * Sends a list of Component messages to an audience using Paper API with optional placeholders.
   *
   * @param target The audience to send the messages to.
   * @param text The list of messages as Components.
   * @param placeholders Optional placeholders to replace in the messages.
   * @throws Exception If Paper API is not available.
   */
  @PaperOnly
  @SneakyThrows
  public void sendComponents(Audience target, List<Component> text, Placeholder... placeholders) {
    PaperUtility.checkPaper();
    text.forEach(message -> send(target, message, placeholders));
  }

  /**
   * Sends a message to the console.
   *
   * @param text The message as an ApareciumComponent.
   */
  public void sendConsole(ApareciumComponent text) {
    String messageString = text.getAsString();

    if (messageString != null) {
      Bukkit.getConsoleSender().sendMessage(messageString);
    }
  }

  /**
   * Sends a list of messages to the console.
   *
   * @param text The list of messages as ApareciumComponents.
   */
  public void sendConsole(List<ApareciumComponent> text) {
    for (ApareciumComponent apareciumComponent : text) {
      sendConsole(apareciumComponent);
    }
  }
}