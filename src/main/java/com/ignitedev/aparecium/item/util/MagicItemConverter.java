/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.item.util;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.config.ItemBase;
import com.ignitedev.aparecium.item.MagicItem;
import com.twodevsstudio.simplejsonconfig.api.Config;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class MagicItemConverter {

  /**
   * @param itemStackList list of itemstacks you want to convert
   * @param createNewMagicItems if true all non-existing MagicItems contained in specified List will
   *     be created with default values, otherwise returned List won't contain them
   */
  public List<MagicItem> convertItemStackList(
      List<ItemStack> itemStackList, boolean createNewMagicItems) {
    List<MagicItem> magicItems = new ArrayList<>();
    ItemBase config = Config.getConfig(ItemBase.class);

    for (ItemStack itemStack : itemStackList) {
      MagicItem byItemStack = config.findByItemStack(itemStack);

      if (byItemStack != null) {
        magicItems.add(byItemStack);
      } else if (createNewMagicItems) {
        magicItems.add(
            Aparecium.getFactoriesManager()
                .getMagicItemFactories()
                .getDefaultFactory()
                .fromItemStack(itemStack));
      }
    }
    return magicItems;
  }

  public boolean isSimilarComponent(boolean isSimilar, ApareciumComponent componentLore,
      ApareciumComponent toCheckComponentLore) {

    if (componentLore == null || toCheckComponentLore == null) {
      return isSimilar;
    }
    String componentLoreString = componentLore.getAsString();
    String toCheckComponentLoreString = toCheckComponentLore.getAsString();

    if (componentLoreString == null || toCheckComponentLoreString == null) {
      return isSimilar;
    }
    if (componentLoreString.equalsIgnoreCase(toCheckComponentLoreString)) {
      isSimilar = true;
    }
    return isSimilar;
  }
}
