package com.ignitedev.aparecium.example;

import com.ignitedev.aparecium.config.ItemBase;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.item.basic.DropItem;
import com.ignitedev.aparecium.item.basic.Item;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;

/**
 * @implNote These class has example usages of item api
 */
public class ItemExample {

    @Autowired // autowired fields HAVE to be STATIC
    private static ItemBase itemBase;


    public void createItem() {
        Item item = Item.builder().id("test").name("test").itemType(ItemType.COMMON)
            .addDescription("test desc").build();

        DropItem build = DropItem.builder().dropChance(0.5).id("dropItemTest")
            .itemType(ItemType.COMMON).addDescription("test").build();
    }

    public void saveItem(){
        Item item = Item.builder().id("test").name("test").itemType(ItemType.COMMON)
            .addDescription("test desc").build();

        itemBase.saveItem(item);
    }

    public void getItemFromRepository() {
        MagicItem byId = itemBase.getById("default");
    }


}
