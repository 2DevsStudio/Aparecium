/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.example;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.hologram.SimpleBaseHologram;
import com.ignitedev.aparecium.hologram.data.HologramEntry;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;

/**
 * @implNote These class has example usages of hologram api
 */
@Example
public class HologramExample {

  @Example
  public void createHologram(Aparecium apareciumInstance) {
    SimpleBaseHologram firstExampleHologram =
        new SimpleBaseHologram(
            "testHologram",
            Bukkit.getWorlds().get(0).getSpawnLocation(),
            List.of(
                new HologramEntry("test First Line", null),
                new HologramEntry(null, Material.GOLD_BLOCK)),
            new ArrayList<>());

    /*
     * These two ways of creating holograms are technically equal
     * but just with different design patter
     */

    SimpleBaseHologram secondExampleHologram =
        SimpleBaseHologram.builder()
            .id("testHologram")
            .line(new HologramEntry("test First Line", null))
            .line(new HologramEntry(null, Material.GOLD_BLOCK))
            .build();

    firstExampleHologram.spawn(apareciumInstance);
    secondExampleHologram.spawn(apareciumInstance);
  }
}
