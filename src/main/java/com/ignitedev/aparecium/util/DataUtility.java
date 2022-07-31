package com.ignitedev.aparecium.util;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.util.text.Placeholder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.material.Crops;

@SuppressWarnings("unused")
@UtilityClass
public class DataUtility {

  /**
   * @implNote checking placeholder list and updating specified data with specified value
   */
  public List<Placeholder> updateData(
      List<Placeholder> placeholdersToUpdate, String dataToUpdate, String updateValue) {

    AtomicBoolean isDataFound = new AtomicBoolean(false);
    ArrayList<Placeholder> updatedList = new ArrayList<>(placeholdersToUpdate);

    updatedList.forEach(
        placeholder -> {
          if (placeholder.getKey().equalsIgnoreCase(dataToUpdate)) {
            placeholder.setValue(updateValue);
            isDataFound.set(true);
          }
        });
    if (!isDataFound.get()) {
      updatedList.add(new Placeholder(dataToUpdate, updateValue));
    }

    return updatedList;
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  public boolean isUUID(String string) {
    try {
      UUID.fromString(string);
      return true;
    } catch (Exception exception) {
      return false;
    }
  }

  public <T extends Enum<?>> boolean enumsContains(T[] enumValues, String string) {
    for (T enumValue : enumValues) {
      if (enumValue.name().equalsIgnoreCase(string)) {
        return true;
      }
    }
    return false;
  }

  public boolean isBoolean(String string) {
    return string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false");
  }

  public <T> void removeListElements(List<T> list, int elements) {
    if (list.size() <= elements) {
      throw new IllegalArgumentException("Removing more elements than list contains!");
    }
    if (elements > 0) {
      list.subList(0, elements).clear();
    }
  }

  public <T> T getRandomElement(T[] list) {
    return list[ThreadLocalRandom.current().nextInt(list.length)];
  }

  public String[] flatten(String[][] data) {
    List<String> list = new ArrayList<>();
    for (String[] datum : data) {
      list.addAll(Arrays.asList(datum));
    }
    return list.toArray(new String[0]);
  }

  public int[] flatten(int[][] data) {
    int[] arr = new int[totalSize(data)];
    int j = 0;
    for (int[] datum : data) {
      for (int i : datum) {
        arr[j] = i;
        j++;
      }
    }
    return arr;
  }

  public int totalSize(int[][] a2) {
    int result = 0;
    for (int[] a1 : a2) {
      result += a1.length;
    }
    return result;
  }

  public void replaceSignText(Sign signState, String from, String to) {
    if (signState == null) {
      return;
    }
    int x = 0;
    for (Component line : signState.lines()) {
      signState.line(x, line.replaceText((value) -> value.matchLiteral(from).replacement(to)));
      x++;
    }
  }

  @SafeVarargs
  public <C> List<C> mergeLists(List<C>... lists) {
    List<C> returnList = new ArrayList<>();

    for (List<C> list : lists) {
      returnList.addAll(list);
    }
    return returnList;
  }

  public double round(double value, int precision) {
    int scale = (int) Math.pow(10, precision);
    return (double) Math.round(value * scale) / scale;
  }

  public boolean isCrop(Block block) {
    return block.getState().getData() instanceof Crops;
  }

  private boolean isDay(World world) {
    long time = world.getTime();
    return time < 12300 || time > 23850;
  }

  /**
   * Convert a millisecond duration to a string format
   *
   * @param millis A duration to convert to a string form
   * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
   */
  public String getDurationBreakdown(long millis) {
    if (millis < 0) {
      throw new IllegalArgumentException("Duration must be greater than zero!");
    }
    long days = TimeUnit.MILLISECONDS.toDays(millis);
    millis -= TimeUnit.DAYS.toMillis(days);
    long hours = TimeUnit.MILLISECONDS.toHours(millis);
    millis -= TimeUnit.HOURS.toMillis(hours);
    long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
    millis -= TimeUnit.MINUTES.toMillis(minutes);
    long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

    return (hours + " Hours " + minutes + " Minutes " + seconds + " Seconds");
  }

  @SneakyThrows
  public void unRegisterBukkitCommand(PluginCommand command, Aparecium aparecium) {
    Object result =
        ReflectionUtility.getPrivateField(aparecium.getServer().getPluginManager(), "commandMap");
    SimpleCommandMap commandMap = (SimpleCommandMap) result;
    Object map = ReflectionUtility.getPrivateField(commandMap, "knownCommands");
    HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;

    knownCommands.remove(command.getName());

    for (String alias : command.getAliases()) {
      if (knownCommands.containsKey(alias)
          && knownCommands.get(alias).toString().contains(aparecium.getName())) {
        knownCommands.remove(alias);
      }
    }
  }
}
