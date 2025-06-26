package com.ignitedev.aparecium.config.wrapper;

import com.ignitedev.aparecium.config.LayoutBase;
import com.ignitedev.aparecium.gui.basic.Layout;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

@Data
@Builder
@AllArgsConstructor
public class LayoutWrapper {

  @Autowired
  private static LayoutBase layoutBase;

  @Nullable
  private String layoutId;
  @Nullable private Layout layout;

  @Nullable
  public Layout getAvailableLayout() {
    return this.layout != null ? this.layout : layoutBase.getById(this.layoutId);
  }
}
