package com.ignitedev.aparecium.gui;

import com.ignitedev.aparecium.interfaces.Identifiable;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

@Data
@SuperBuilder(toBuilder = true)
public class Layout implements Cloneable, Identifiable, Comparable<Layout> {

  protected String id;

  @Override
  public int compareTo(@NotNull Layout compareTo) {
    // todo
    return 0;
  }

  @Override
  @SneakyThrows
  public Layout clone() {
    Layout clone = (Layout) super.clone();

    // todo
    clone.id = this.id;

    return clone;
  }
}
