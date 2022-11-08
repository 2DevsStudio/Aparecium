package com.ignitedev.aparecium.item.util;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.item.repository.MagicItemRepository;
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
    MagicItemRepository repository = MagicItemRepository.getInstance();

    for (ItemStack itemStack : itemStackList) {
      MagicItem byItemStack = repository.findByItemStack(itemStack);

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
}
