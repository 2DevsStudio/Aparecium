/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.util.text;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("unused")
@AllArgsConstructor
public class Placeholder {

  @Getter @Setter private String key;
  @Getter @Setter private String value;

  public static Placeholder replacing(String key, String value) {
    return new Placeholder(key, value);
  }

  public static Placeholder replacing(String key, Number value) {
    if (value == null) {
      return Placeholder.replacing(key, "0");
    }
    return Placeholder.replacing(key, String.valueOf(value));
  }

  public static Placeholder replacing(String key, Object value) {
    if (value == null) {
      return Placeholder.replacing(key, "");
    }
    return Placeholder.replacing(key, value.toString());
  }

  public String replaceIn(String text) {
    return text.replace(key, value);
  }

  public List<String> replaceIn(List<String> text) {
    return text.stream().map(this::replaceIn).collect(Collectors.toList());
  }
}
