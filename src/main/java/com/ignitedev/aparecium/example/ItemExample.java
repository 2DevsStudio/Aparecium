package com.ignitedev.aparecium.example;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.config.ItemBase;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.item.basic.DropItem;
import com.ignitedev.aparecium.item.basic.Item;
import com.ignitedev.aparecium.item.repository.MagicItemRepository;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import org.bukkit.Material;

/**
 * @implNote These class has example usages of item api
 */
@Example
public class ItemExample {

  @Autowired // autowired fields HAS to be STATIC
  private static ItemBase itemBase;

  @Example
  public void createItem() {
    @Example
    Item item =
        Item.builder()
            .id("test")
            .name(new ApareciumComponent("test"))
            .itemType(ItemType.COMMON)
            .description(new ApareciumComponent("test desc"))
            .build();

    @Example
    DropItem build =
        DropItem.builder()
            .dropChance(0.5)
            .id("dropItemTest")
            .itemType(ItemType.COMMON)
            .description(new ApareciumComponent("test"))
            .build();

    @Example
    DropItem dropItemTest2 =
        Aparecium.getFactoriesManager()
            .getDropItemFactory()
            .createItem("dropItemTest2", Material.DIRT);
  }

  @Example
  public void saveItem() {
    Item item =
        Item.builder()
            .id("test")
            .name(new ApareciumComponent("test"))
            .itemType(ItemType.COMMON)
            .description(new ApareciumComponent("test desc"))
            .build();

    itemBase.saveItem(item);
  }

  @Example
  public void getItemFromConfig() {
    @Example MagicItem byId = itemBase.getById("default", Item.class);
  }

  @Example
  public void getItemFromRepository() {
    @Example MagicItem byId = MagicItemRepository.getInstance().findById("default");
  }
}
