/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.engine;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.config.adapter.ComponentAdapter;
import com.ignitedev.aparecium.config.adapter.InstantAdapter;
import com.ignitedev.aparecium.config.adapter.MagicItemAdapter;
import com.ignitedev.aparecium.item.MagicItem;
import com.twodevsstudio.simplejsonconfig.def.Serializer;
import com.twodevsstudio.simplejsonconfig.def.SharedGsonBuilder;
import java.time.Instant;
import net.kyori.adventure.text.Component;

/**
 * @implNote This Gson Builder is used for SimpleJsonConfig .json files processing to provide type
 *     adapters for Aparecium stuff
 */
public class ApareciumGsonBuilder {
  private final SharedGsonBuilder gsonBuilder;

  public ApareciumGsonBuilder() {
    // Get the existing Gson builder
    this.gsonBuilder = Serializer.getInst().toBuilder();

    // Register all our custom adapters
    registerAdapters();
  }

  private void registerAdapters() {
    InstantAdapter instantAdapter = new InstantAdapter();
    MagicItemAdapter magicItemAdapter = new MagicItemAdapter();

    gsonBuilder
        .registerTypeHierarchyAdapter(Instant.class, instantAdapter)
        .registerTypeHierarchyAdapter(MagicItem.class, magicItemAdapter);

    if (Aparecium.isUsingPaper()) {
      gsonBuilder.registerTypeHierarchyAdapter(Component.class, new ComponentAdapter());
    }
  }

  public void build() {
    gsonBuilder.build();
  }
}