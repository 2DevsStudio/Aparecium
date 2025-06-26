/*
 * Copyright (c) 2022-2023. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.engine;

/*
 * "She tapped the diary three times and said, "Aparecium!" Nothing happened."
 *
 *  ~~ Hermione using the spell on Tom Riddle's diary
 */

import co.aikar.commands.PaperCommandManager;
import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.command.ItemBaseCommand;
import com.ignitedev.aparecium.command.custom.CustomCommand;
import com.ignitedev.aparecium.command.custom.CustomCommandProcessor;
import com.ignitedev.aparecium.config.CustomCommandsBase;
import com.ignitedev.aparecium.config.adapter.ComponentAdapter;
import com.ignitedev.aparecium.config.adapter.InstantAdapter;
import com.ignitedev.aparecium.config.adapter.MagicItemAdapter;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.item.interaction.listener.ItemInteractionListener;
import com.ignitedev.aparecium.logging.HedwigLogger;
import com.ignitedev.aparecium.util.ReflectionUtility;
import com.twodevsstudio.simplejsonconfig.SimpleJSONConfig;
import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.def.Serializer;
import com.twodevsstudio.simplejsonconfig.def.SharedGsonBuilder;
import com.twodevsstudio.simplejsonconfig.def.adapters.ChronoUnitAdapter;
import com.twodevsstudio.simplejsonconfig.def.adapters.ClassAdapter;
import com.twodevsstudio.simplejsonconfig.def.adapters.InterfaceAdapter;
import com.twodevsstudio.simplejsonconfig.def.adapters.ItemStackAdapter;
import com.twodevsstudio.simplejsonconfig.def.adapters.ReferenceAdapter;
import com.twodevsstudio.simplejsonconfig.def.adapters.WorldAdapter;
import com.twodevsstudio.simplejsonconfig.def.strategies.SuperclassExclusionStrategy;
import java.io.File;
import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.Getter;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandMap;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

/**
 * @implNote Aparecium Main engine, this is implementation of Aparecium, as well it is perfect
 *     example of how to set up Aparecium
 */
public class ApareciumMain extends Aparecium {

  @Getter private static ApareciumMain instance;

  @Override
  public void onPreLoad() {}

  @Override
  public void onInnerLoad() {}

  @Override
  public void onPostLoad() {}

  @Override
  public void onEnabling() {
    instance = this;

    registerGson(Serializer.getInst().toBuilder());
    SimpleJSONConfig.INSTANCE.register(this);

    registerCommands(new PaperCommandManager(this));
    registerListeners(Bukkit.getPluginManager());
    registerCustomCommands();
    initializeDirectories();
  }

  @Override
  public void onDisabling() {}

  private void initializeDirectories() {
    File file = new File(getDataFolder(), "schematics");

    if (file.mkdirs()) {
      HedwigLogger.getMainLogger().info("Created Schematics directory");
    }
  }

  private void registerCommands(PaperCommandManager paperCommandManager) {
    paperCommandManager.registerCommand(new ItemBaseCommand());
  }

  private void registerListeners(PluginManager pluginManager) {
    pluginManager.registerEvents(new ItemInteractionListener(), this);
  }

  @SneakyThrows
  private void registerCustomCommands() {
    CustomCommandsBase commandsBase = Config.getConfig(CustomCommandsBase.class);
    Field bukkitCommandMap =
        ReflectionUtility.getField(Bukkit.getServer().getClass(), "commandMap");
    CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

    for (CustomCommand value : commandsBase.getSavedCommands().values()) {
      commandMap.register(
          value.getCommandName(),
          new CustomCommandProcessor(
              value.getCommandName(),
              "Permission: " + value.getCommandPermission(),
              value.getCommandName(),
              value.getCommandAliases()));
    }
  }

  private void registerGson(SharedGsonBuilder sharedGsonBuilder) {
    sharedGsonBuilder
        .registerTypeHierarchyAdapter(Class.class, new ClassAdapter())
        .registerTypeHierarchyAdapter(Instant.class, new InstantAdapter())
        .registerTypeHierarchyAdapter(ChronoUnit.class, new ChronoUnitAdapter());

    if (Aparecium.isUsingPaper()) {
      sharedGsonBuilder.registerTypeHierarchyAdapter(Component.class, new ComponentAdapter());
    }
    sharedGsonBuilder
        .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter())
        .registerTypeHierarchyAdapter(World.class, new WorldAdapter())
        .registerTypeHierarchyAdapter(Reference.class, new ReferenceAdapter())
        .registerTypeAdapter(BlockState.class, new InterfaceAdapter())
        .addDeserializationExclusionStrategy(new SuperclassExclusionStrategy())
        .addSerializationExclusionStrategy(new SuperclassExclusionStrategy())
        .registerTypeAdapter(MagicItem.class, new MagicItemAdapter())
        .build();
  }
}
