package com.ignitedev.aparecium.config.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ignitedev.aparecium.util.text.TextUtility;
import java.lang.reflect.Type;
import net.kyori.adventure.text.Component;

public class ComponentAdapter implements JsonSerializer<Component>, JsonDeserializer<Component> {

  @Override
  public Component deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    return TextUtility.parseMiniMessage(
        jsonElement.getAsJsonObject().get("component").getAsString());
  }

  @Override
  public JsonElement serialize(
      Component component, Type type, JsonSerializationContext jsonSerializationContext) {
    JsonObject jsonObject = new JsonObject();

    jsonObject.add(
        "component", jsonSerializationContext.serialize(TextUtility.serializeComponent(component)));

    return jsonObject;
  }
}
