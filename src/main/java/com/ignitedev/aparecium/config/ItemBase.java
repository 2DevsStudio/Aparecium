package com.ignitedev.aparecium.config;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.item.basic.Item;
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
          .material(Material.BARRIER)
          .name(new ApareciumComponent("Couldn't find item, check typed id"))
          .build();

  /**
   * @implNote please note that this method save and reload config, if you have any pending changes
   *     in your config file then it might be overridden
   * @param magicItem item to save
   */
  public void saveItem(MagicItem magicItem) {
    savedItems.put(magicItem.getId(), magicItem);

    save();
    reload();
  }

  @NotNull
  public MagicItem getById(String itemId) {
    return ((Item)
            savedItems.getOrDefault(
                itemId,
                this.noneItem.clone().toBuilder().name(new ApareciumComponent(itemId)).build()))
        .clone();
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
