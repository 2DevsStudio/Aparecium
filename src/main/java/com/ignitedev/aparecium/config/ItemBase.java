package com.ignitedev.aparecium.config;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.item.basic.Item;
import com.ignitedev.aparecium.item.factory.factories.DefaultMagicItemFactory;
import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.interfaces.Configuration;
import de.tr7zw.nbtapi.NBTItem;
import java.util.Map;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("FieldMayBeFinal") // simplejsonconfig is not supporting final fields yet
@Getter
@Configuration("item-base.json")
public class ItemBase extends Config {

  private Map<String, Item> savedItems = exampleItems();

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
  public <T extends Item> void saveItem(T item) {
    savedItems.put(item.getId(), item);

    save();
    reload();
  }

  @NotNull
  public <T extends Item> T findById(String itemId, Class<T> castClass) {
    DefaultMagicItemFactory<?> factory = DefaultMagicItemFactory.getByClass(castClass);

    if (savedItems.containsKey(itemId)) {
      return (T) factory.from((T) savedItems.get(itemId));
    } else {
      return (T)
          factory.from(this.noneItem).toBuilder().name(new ApareciumComponent(itemId)).build();
    }
  }

  public Item findById(String itemId) {
    return findById(itemId, Item.class);
  }

  @Nullable
  public <T extends Item> T findByItemStack(ItemStack itemStack, Class<T> castClass) {
    NBTItem nbtItem = new NBTItem(itemStack, true);
    String id = nbtItem.getString("id");

    return findById(id, castClass);
  }

  @Nullable
  public Item findByItemStack(ItemStack itemStack) {
    return findByItemStack(itemStack, Item.class);
  }

  private Map<String, Item> exampleItems() {
    return Map.of(
        "defaultMagicItem",
        Item.builder()
            .id("defaultMagicItem")
            .material(Material.DIRT)
            .name(new ApareciumComponent("<yellow>DEFAULT"))
            .build());
  }
}
