/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.config.adapter.ComponentAdapter;
import com.ignitedev.aparecium.config.adapter.InstantAdapter;
import com.ignitedev.aparecium.config.adapter.MagicItemAdapter;
import com.ignitedev.aparecium.item.MagicItem;
import com.twodevsstudio.simplejsonconfig.def.adapters.ChronoUnitAdapter;
import com.twodevsstudio.simplejsonconfig.def.adapters.ClassAdapter;
import com.twodevsstudio.simplejsonconfig.def.adapters.InterfaceAdapter;
import com.twodevsstudio.simplejsonconfig.def.adapters.ItemStackAdapter;
import com.twodevsstudio.simplejsonconfig.def.adapters.ReferenceAdapter;
import com.twodevsstudio.simplejsonconfig.def.adapters.WorldAdapter;
import com.twodevsstudio.simplejsonconfig.def.strategies.SuperclassExclusionStrategy;
import java.lang.ref.Reference;
import java.lang.reflect.Modifier;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.Data;
import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;

/**
 * @implNote This Gson Builder is used for SimpleJsonConfig .json files processing to provide type
 *     adapters for Aparecium stuff
 */
@Data
public class ApareciumGsonBuilder {

  private GsonBuilder gsonBuilder;

  public ApareciumGsonBuilder() {
    this.gsonBuilder =
        new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
            .serializeNulls()
            .registerTypeHierarchyAdapter(Class.class, new ClassAdapter())
            .registerTypeHierarchyAdapter(Instant.class, new InstantAdapter())
            .registerTypeHierarchyAdapter(ChronoUnit.class, new ChronoUnitAdapter());

    if (Aparecium.isUsingPaper()) {
      this.gsonBuilder.registerTypeHierarchyAdapter(Component.class, new ComponentAdapter());
    }
    this.gsonBuilder =
        this.gsonBuilder
            .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter())
            .registerTypeHierarchyAdapter(World.class, new WorldAdapter())
            .registerTypeHierarchyAdapter(Reference.class, new ReferenceAdapter())
            .registerTypeAdapter(BlockState.class, new InterfaceAdapter())
            .addDeserializationExclusionStrategy(new SuperclassExclusionStrategy())
            .addSerializationExclusionStrategy(new SuperclassExclusionStrategy())
            .registerTypeAdapter(MagicItem.class, new MagicItemAdapter());
  }

  public Gson build() {
    return gsonBuilder.create();
  }
}
