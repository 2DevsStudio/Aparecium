/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class SerializationUtility {

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

  public int deserializeInt(JsonObject jsonObject, String memberName) {
    int number = 0;

    if (jsonObject.has(memberName)) {
      number = jsonObject.get(memberName).getAsInt();
    }
    return number;
  }

  public double deserializeDouble(JsonObject jsonObject, String memberName) {
    double number = 0;

    if (jsonObject.has(memberName)) {
      number = jsonObject.get(memberName).getAsDouble();
    }
    return number;
  }

  public boolean deserializeBoolean(JsonObject jsonObject, String memberName) {
    boolean bool = false;

    if (jsonObject.has(memberName)) {
      bool = jsonObject.get(memberName).getAsBoolean();
    }
    return bool;
  }

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
