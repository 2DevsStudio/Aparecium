/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.config.wrapper;

import com.ignitedev.aparecium.config.ItemBase;
import com.ignitedev.aparecium.item.MagicItem;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

@Data
@Builder
@AllArgsConstructor
public class  MagicItemWrapper {

  @Autowired private static ItemBase itemBase;

  @Nullable private String itemId;
  @Nullable private MagicItem magicItem;

  @Nullable
  public MagicItem getAvailableMagicItem() {
    return this.magicItem != null ? this.magicItem : itemBase.findById(this.itemId);
  }
}
