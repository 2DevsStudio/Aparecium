/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.example;

import com.ignitedev.aparecium.interfaces.Example;
import com.ignitedev.aparecium.sorting.SortingHat;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

@Example
public class SortingExample {

  // This collection is initialized with few elements on the start
  @Example
  private final SortingHat<ExamplePlayer> playerSort =
      new SortingHat<>(
              Set.of(
                  new ExamplePlayer(3, true),
                  new ExamplePlayer(3, false),
                  new ExamplePlayer(3, false),
                  new ExamplePlayer(3, true),
                  new ExamplePlayer(3, false)))
          .addFilter("gays-only", examplePlayer -> examplePlayer.gay);

  @Example
  public void main() {
    playerSort.add(new ExamplePlayer(10, false)).add(new ExamplePlayer(11, false));
    playerSort.addAll(List.of(new ExamplePlayer(10, false), new ExamplePlayer(11, false)));
    // add elements to collection, added element is automatically sorted using it Comparator

    @Example
    Set<ExamplePlayer> sort =
        playerSort.sort(Comparator.comparing(examplePlayer -> examplePlayer.level));
    // sort players by level ( sort returns clone of list which is sorted, not going to affect
    // original )

    playerSort.addFilter("gays-only", examplePlayer -> examplePlayer.gay);
    playerSort.addFilter("level-3-only", examplePlayer -> examplePlayer.level == 3);
    // filter once added will remain until you remove it, so you can apply multiple filters at once
    @Example Set<ExamplePlayer> filter = playerSort.filter();
    // this set is copy of collection with only filtered elements
  }

  // example player class for testing purposes
  @Example
  public record ExamplePlayer(int level, boolean gay) implements Comparable<ExamplePlayer> {

    @Override
    public int compareTo(@NotNull SortingExample.ExamplePlayer o) {
      return level() - o.level();
    }
  }
}
