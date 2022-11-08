/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.example;

import com.ignitedev.aparecium.sorting.SortingHat;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class SortingExample {

  // This collection is initialized with few elements on the start
  private  final SortingHat<Player> playerSort = new SortingHat<>(Set.of(new Player(3, true),
      new Player(3, false), new Player(3, false), new Player(3,
      true), new Player(3, false))).addFilter("gays-only", player -> player.gay);


  public void main(){
    playerSort.add(new Player(10,  false)).add(new Player(11, false));
    playerSort.addAll(List.of(new Player(10,  false), new Player(11, false)));
    // add elements to collection, added element is automatically sorted using it Comparator

    playerSort.sort(Comparator.comparing(player -> player.level));
    // sort players by level

    playerSort.addFilter("gays-only", player -> player.gay);
    playerSort.addFilter("level-3-only", player -> player.level == 3);
    // filter once added will remain until you remove it, so you can apply multiple filters at once
    Set<Player> filter = playerSort.filter();
    // this set is copy of collection with only filtered elements
  }

    // example player class for testing purposes
    public record Player(int level, boolean gay) implements Comparable<Player> {


      @Override
      public int compareTo(@NotNull SortingExample.Player o) {
        return level() - o.level();
      }
    }


}
