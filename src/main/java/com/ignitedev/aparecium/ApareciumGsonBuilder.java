package com.ignitedev.aparecium;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.time.temporal.ChronoUnit;
import lombok.Data;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;

/**
 * @implNote This Gson Builder is used for SimpleJsonConfig .json files processing to provide type adapters for
 * Aparecium stuff
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
            .registerTypeHierarchyAdapter(ChronoUnit.class, new ChronoUnitAdapter())
            .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter())
            .registerTypeHierarchyAdapter(World.class, new WorldAdapter())
            .registerTypeHierarchyAdapter(Reference.class, new ReferenceAdapter())
            .registerTypeAdapter(BlockState.class, new InterfaceAdapter())
            .addDeserializationExclusionStrategy(new SuperclassExclusionStrategy())
            .addSerializationExclusionStrategy(new SuperclassExclusionStrategy())
            .registerTypeAdapter(MagicItem.class, new InterfaceAdapter());
  }

  public Gson build() {
    return gsonBuilder.create();
  }

}

