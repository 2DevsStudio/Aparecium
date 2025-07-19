/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.config.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

public class InstantAdapter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {
  @Override
  public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    try {
      if (json.isJsonPrimitive()) {
        return Instant.ofEpochMilli(json.getAsLong());
      } else if (json.isJsonObject()) {
        JsonObject obj = json.getAsJsonObject();
        if (obj.has("epochSecond") && obj.has("nano")) {
          return Instant.ofEpochSecond(
              obj.get("epochSecond").getAsLong(),
              obj.get("nano").getAsInt()
          );
        }
        if (obj.has("instant")) {
          return Instant.ofEpochMilli(obj.get("instant").getAsLong());
        }
      }
      return Instant.now();
    } catch (Exception e) {
      return Instant.now();
    }
  }

  @Override
  public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.toEpochMilli());
  }
}