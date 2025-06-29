/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.config.wrapper;

import com.ignitedev.aparecium.config.LayerBase;
import com.ignitedev.aparecium.gui.layer.LayoutLayer;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

@Data
@Builder
@AllArgsConstructor
public class LayerWrapper {

  @Autowired private static LayerBase layerBase;

  @Nullable private String layerId;
  @Nullable private LayoutLayer layoutLayer;

  @Nullable
  public LayoutLayer getAvailableLayer() {
    return this.layoutLayer != null ? this.layoutLayer : layerBase.getById(this.layerId);
  }
}
