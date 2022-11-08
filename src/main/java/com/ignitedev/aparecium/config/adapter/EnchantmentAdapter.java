/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.config.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

public class EnchantmentAdapter
    implements JsonSerializer<Enchantment>, JsonDeserializer<Enchantment> {

  @Override
  public Enchantment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    return Enchantment.getByKey(
        NamespacedKey.minecraft(json.getAsJsonObject().get("enchantment").getAsString()));
  }

  @Override
  public JsonElement serialize(Enchantment src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject jsonObject = new JsonObject();

    jsonObject.add("enchantment", context.serialize(src.getKey().getKey()));

    return null;
  }
}
