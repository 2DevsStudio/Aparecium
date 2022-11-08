package com.ignitedev.aparecium.config;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.item.basic.DropItem;
import com.ignitedev.aparecium.item.basic.Item;
import com.ignitedev.aparecium.item.basic.LayoutItem;
import com.ignitedev.aparecium.item.basic.PatternItem;
import com.ignitedev.aparecium.item.factory.factories.DefaultMagicItemFactory;
import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.interfaces.Configuration;
import java.util.Map;
import lombok.Getter;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("FieldMayBeFinal") // simplejsonconfig is not supporting final fields yet
@Getter
@Configuration("item-base.json")
public class ItemBase extends Config {

  private Map<String, MagicItem> savedItems = exampleItems();

  /**
   * @implNote This item is returned if none item with specified id was found
   */
  private Item noneItem =
      Item.builder()
          .id("noneItem")
          .material(Material.BARRIER)
          .name(new ApareciumComponent("Couldn't find item, check typed id"))
          .build();

  /**
   * @implNote please note that this method save and reload config, if you have any pending changes
   *     in your config file then it might be overridden
   * @param item item to save
   */
  public <T extends MagicItem> void saveItem(T item) {
    savedItems.put(item.getId(), item);

    save();
    reload();
  }

  @NotNull
  public <T extends MagicItem> T getById(String itemId, Class<T> castClass) {
    DefaultMagicItemFactory factory;

    if (castClass == LayoutItem.class) {
      factory = Aparecium.getFactoriesManager().getLayoutItemFactory();
    } else if (castClass == DropItem.class) {
      factory = Aparecium.getFactoriesManager().getDropItemFactory();
    } else if (castClass == PatternItem.class) {
      factory = Aparecium.getFactoriesManager().getPatternItemFactory();
    } else {
      factory =
          (DefaultMagicItemFactory)
              Aparecium.getFactoriesManager().getMagicItemFactories().getDefaultFactory();
    }
    if (savedItems.containsKey(itemId)) {
      return (T) factory.from((Item) savedItems.get(itemId));
    } else {
      return (T)
          factory.from(this.noneItem).toBuilder().name(new ApareciumComponent(itemId)).build();
    }
  }

  private Map<String, MagicItem> exampleItems() {
    return Map.of(
        "defaultMagicItem",
        Item.builder()
            .id("defaultMagicItem")
            .material(Material.DIRT)
            .name(new ApareciumComponent("<yellow>DEFAULT"))
            .build());
  }
}
