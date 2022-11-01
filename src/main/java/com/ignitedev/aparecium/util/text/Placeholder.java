/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util.text;

import com.ignitedev.aparecium.component.ApareciumComponent;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("unused")
@AllArgsConstructor
public class Placeholder {

  @Getter @Setter private ApareciumComponent key;
  @Getter @Setter private ApareciumComponent value;

  public static Placeholder replacing(ApareciumComponent key, ApareciumComponent value) {
    return new Placeholder(key, value);
  }

  public static Placeholder replacing(ApareciumComponent key, Number value) {
    if (value == null) {
      return Placeholder.replacing(key, "0");
    }
    return Placeholder.replacing(key, ApareciumComponent.of(String.valueOf(value)));
  }

  public static Placeholder replacing(ApareciumComponent key, Object value) {
    if (value == null) {
      return Placeholder.replacing(key, "");
    }
    return Placeholder.replacing(key, ApareciumComponent.of(value.toString()));
  }

  public ApareciumComponent replaceIn(ApareciumComponent text) {
    return text.replace(key.getAsString(), value.getAsString());
  }

  public List<ApareciumComponent> replaceIn(List<ApareciumComponent> text) {
    return text.stream().map(this::replaceIn).collect(Collectors.toList());
  }
}
