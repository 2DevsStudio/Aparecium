/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

/**
 * Utility class for serialization and deserialization of JSON objects.
 * Provides methods to handle common data types such as lists, integers, doubles, and booleans.
 */
@UtilityClass
public final class SerializationUtility {

  /**
   * Deserializes a list of strings from a JSON object.
   *
   * @param jsonObject The JSON object containing the data.
   * @param memberName The name of the JSON member to deserialize.
   * @return A list of strings extracted from the JSON member.
   */
  public List<String> deserializeStringList(JsonObject jsonObject, String memberName) {
    List<String> list = new ArrayList<>();

    if (jsonObject.has(memberName)) {
      jsonObject
              .get(memberName)
              .getAsJsonArray()
              .forEach(jsonElement -> list.add(jsonElement.getAsString()));
    }
    return list;
  }

  /**
   * Deserializes an integer from a JSON object.
   *
   * @param jsonObject The JSON object containing the data.
   * @param memberName The name of the JSON member to deserialize.
   * @return The integer value extracted from the JSON member, or 0 if the member does not exist.
   */
  public int deserializeInt(JsonObject jsonObject, String memberName) {
    int number = 0;

    if (jsonObject.has(memberName)) {
      number = jsonObject.get(memberName).getAsInt();
    }
    return number;
  }

  /**
   * Deserializes a double from a JSON object.
   *
   * @param jsonObject The JSON object containing the data.
   * @param memberName The name of the JSON member to deserialize.
   * @return The double value extracted from the JSON member, or 0 if the member does not exist.
   */
  public double deserializeDouble(JsonObject jsonObject, String memberName) {
    double number = 0;

    if (jsonObject.has(memberName)) {
      number = jsonObject.get(memberName).getAsDouble();
    }
    return number;
  }

  /**
   * Deserializes a boolean from a JSON object.
   *
   * @param jsonObject The JSON object containing the data.
   * @param memberName The name of the JSON member to deserialize.
   * @return The boolean value extracted from the JSON member, or false if the member does not exist.
   */
  public boolean deserializeBoolean(JsonObject jsonObject, String memberName) {
    boolean bool = false;

    if (jsonObject.has(memberName)) {
      bool = jsonObject.get(memberName).getAsBoolean();
    }
    return bool;
  }

  /**
   * Serializes a list of strings into a JSON object.
   *
   * @param jsonObject The JSON object to add the serialized data to.
   * @param context The serialization context used for converting the list.
   * @param list The list of strings to serialize.
   * @param memberName The name of the JSON member to store the serialized data.
   */
  public void serializeStringList(
          JsonObject jsonObject,
          JsonSerializationContext context,
          List<String> list,
          String memberName) {

    if (list == null || list.isEmpty()) {
      return;
    }
    jsonObject.add(memberName, context.serialize(list));
  }
}