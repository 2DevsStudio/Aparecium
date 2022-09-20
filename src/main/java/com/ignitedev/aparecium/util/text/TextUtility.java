/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.util.text;

import com.google.common.base.Strings;
import com.ignitedev.aparecium.component.ApareciumComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@UtilityClass
public class TextUtility {

  public String colorize(String toColor) {
    return ChatColor.translateAlternateColorCodes('&', toColor);
  }

  public Component parseMiniMessage(String toParse) {
    return MiniMessage.miniMessage().deserialize(colorize(toParse));
  }

  public List<Component> parseMiniMessage(List<String> toParse) {
    return toParse.stream().map(TextUtility::parseMiniMessage).collect(Collectors.toList());
  }

  public List<String> colorize(List<String> toColor) {
    return toColor.stream().map(TextUtility::colorize).collect(Collectors.toList());
  }

  public Component colorizeToComponent(String toColor) {
    return parseMiniMessage(toColor);
  }

  public String serializeComponent(Component component) {
    return MiniMessage.miniMessage().serialize(component);
  }

  public List<String> serializeComponent(List<Component> component) {
    return component.stream().map(TextUtility::serializeComponent).collect(Collectors.toList());
  }

  public Component removeColor(Component component) {
    return colorizeToComponent(removeColor(serializeComponent(component)));
  }

  public String removeColor(String name) {
    for (String color :
        Arrays.stream(ChatColor.values()).map(chatColor -> "&" + chatColor.getChar()).toList()) {
      name = name.replaceAll(color, "");
      name = name.replaceAll(colorize(color), "");
    }
    return name;
  }

  public List<String> removeColor(List<String> lines) {
    return lines.stream().map(TextUtility::removeColor).collect(Collectors.toList());
  }

  public List<Component> removeColorComponent(List<Component> component) {
    return component.stream()
        .map(TextUtility::removeColor)
        .map(value -> MiniMessage.miniMessage().stripTags(serializeComponent(value)))
        .map(TextUtility::parseMiniMessage)
        .collect(Collectors.toList());
  }

  public List<Component> colorizeToComponent(List<String> toColor) {
    List<Component> colorizedComponents = new ArrayList<>();

    for (String value : toColor) {
      colorizedComponents.add(colorizeToComponent(value));
    }
    return colorizedComponents;
  }

  public Component replace(Component component, Placeholder... placeholders) {
    for (Placeholder placeholder : placeholders) {
      component =
          component.replaceText(
              (value) ->
                  value
                      .matchLiteral(placeholder.getKey().getAsString())
                      .replacement(placeholder.getValue().getAsComponent()));
    }
    return component;
  }

  public ApareciumComponent replace(ApareciumComponent text, Placeholder... placeholders) {
    for (Placeholder placeholder : placeholders) {
      text = placeholder.replaceIn(text);
    }
    return text;
  }

  public ApareciumComponent replace(ApareciumComponent text, List<Placeholder> placeholders) {
    for (Placeholder placeholder : placeholders) {
      text = placeholder.replaceIn(text);
    }
    return text;
  }

  public List<ApareciumComponent> replace(
      List<ApareciumComponent> text, Placeholder... placeholders) {
    for (Placeholder placeholder : placeholders) {
      text = placeholder.replaceIn(text);
    }
    return text;
  }

  public String getEnumFriendlyName(Enum<?> anEnum) {
    return WordUtils.capitalizeFully(anEnum.name().replace("_", " "));
  }

  /**
   * @return parsed integers from string
   * @apiNote wrap all integers from string as array
   */
  public int[] parseStringIntegers(String string) {
    string = ChatColor.stripColor(string);

    String[] split = string.split(" ");
    int[] ints = new int[10];
    int index = 0;

    for (String value : split) {
      if (value.isEmpty()) {
        continue;
      }
      if (StringUtils.isNumeric(value)) {
        ints[index] = Integer.parseInt(value);
        index = index + 1;
      }
    }
    return ints;
  }

  /**
   * Puts color char before every character in given string. Simply makes this string invisible
   *
   * @param line to apply invisibility
   * @return Invisible line
   * @see #visible(String)
   */
  @NotNull
  public String invisible(@NotNull String line) {
    StringBuilder invisibleString = new StringBuilder();

    for (char c : line.toCharArray()) {
      invisibleString.append(colorize("&")).append(c);
    }
    return invisibleString.toString();
  }

  /**
   * Removes all color chars from string to make every character visible. It simply reverts action
   * made by {@code invisible(String)} method
   *
   * @param line to apply visibility
   * @return Visible string
   * @see #invisible(String)
   */
  @NotNull
  public String visible(@NotNull String line) {
    return line.replaceAll(colorize("&"), "");
  }

  public String getProgressBar(
      int current,
      int max,
      int totalBars,
      char symbol,
      ChatColor completedColor,
      ChatColor notCompletedColor) {
    float percent = (float) current / max;
    int progressBars = (int) (totalBars * percent);

    return Strings.repeat("" + completedColor + symbol, progressBars)
        + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
  }

  public String removeQuotesAndUnescape(String uncleanJson) {
    return StringEscapeUtils.unescapeJava(uncleanJson.replaceAll("^\"|\"$", ""));
  }

  public static String toPercentage(float number, int digits) {
    return String.format("%." + digits + "f", number * 100) + "%";
  }
}
