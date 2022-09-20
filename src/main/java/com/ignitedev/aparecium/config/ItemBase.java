package com.ignitedev.aparecium.config;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.item.basic.DropItem;
import com.ignitedev.aparecium.item.basic.Item;
import com.ignitedev.aparecium.item.basic.LayoutItem;
import com.ignitedev.aparecium.item.factory.factories.DropItemFactory;
import com.ignitedev.aparecium.item.factory.factories.LayoutItemFactory;
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

  private final LayoutItemFactory layoutItemFactory =
      ((LayoutItemFactory)
          Aparecium.getFactoriesManager().getMagicItemFactories().getFactory("LAYOUT_DEFAULT"));
  private final DropItemFactory dropItemFactory =
      ((DropItemFactory)
          Aparecium.getFactoriesManager().getMagicItemFactories().getFactory("DROP_DEFAULT"));

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
  public <T extends MagicItem> T getById(String itemId) {
    if (savedItems.containsKey(itemId)) {
      return (T) savedItems.get(itemId).clone();
    } else {
      return (T)
          this.noneItem.clone().toBuilder().name(new ApareciumComponent(itemId)).build().clone();
    }
  }

  @NotNull
  public <T extends MagicItem> T getById(String itemId, Class<T> castClass) {
    T magicItem;

    if (savedItems.containsKey(itemId)) {
      magicItem = (T) savedItems.get(itemId).clone();
    } else {
      magicItem =
          (T)
              this.noneItem.clone().toBuilder()
                  .name(new ApareciumComponent(itemId))
                  .build()
                  .clone();
    }

    if (magicItem instanceof Item item && castClass == LayoutItem.class) {
      magicItem =
          (T)
              layoutItemFactory.createItem(
                  item.getId(), item.getMaterial(), item.getName(), item.getDescription());
    } else if (magicItem instanceof Item item && castClass == DropItem.class) {
      magicItem =
          (T)
              dropItemFactory.createItem(
                  item.getId(), item.getMaterial(), item.getName(), item.getDescription());
    }
    return magicItem;
  }

  private Map<String, MagicItem> exampleItems() {
    return Map.of(
        "default",
        Item.builder()
            .id("default")
            .material(Material.DIRT)
            .name(new ApareciumComponent("<yellow>DEFAULT"))
            .build());
  }
}
