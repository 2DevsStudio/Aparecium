package com.ignitedev.aparecium.item.factory;

import com.ignitedev.aparecium.item.MagicItem;
import de.tr7zw.nbtapi.NBTItem;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

/** Raw ItemStack Factory without implemented text assets; lore and name */
public abstract class RawItemStackFactory implements MagicItemFactory {

  /**
   * @param magicItem item to convert
   * @param amount itemstack quantity
   * @return built itemstack with all values in MagicItem
   */
  public ItemStack toItemStack(MagicItem magicItem, int amount) {
    ItemStack itemStack = new ItemStack(magicItem.getMaterial());

    applyTags(magicItem, new NBTItem(itemStack, true));

    Damageable itemMeta = (Damageable) itemStack.getItemMeta();

    itemMeta.displayName(buildName(magicItem));
    itemMeta.lore(buildLore(magicItem));
    itemMeta.addItemFlags(ItemFlag.values());

    itemStack.setItemMeta(itemMeta);
    itemStack.setAmount(amount);

    return itemStack;
  }

  /**
   * @param magicItem MagicItem instance for assets
   * @param itemStack itemstack to apply changes
   */
  public void updateItemStack(MagicItem magicItem, ItemStack itemStack) {
    Damageable itemMeta = (Damageable) itemStack.getItemMeta();

    itemMeta.displayName(buildName(magicItem));
    itemMeta.lore(buildLore(magicItem));

    itemStack.setItemMeta(itemMeta);
  }

  /**
   * @param magicItem MagicItem instance for applying NBT-TAGS
   * @param nbtItem item to apply nbt changes
   */
  public void applyTags(MagicItem magicItem, NBTItem nbtItem) {
    nbtItem.setString("id", magicItem.getId());
    nbtItem.setString("rarity", magicItem.getRarity().name());
    nbtItem.setString("itemType", magicItem.getItemType().name());

    magicItem.getTags().forEach(nbtItem::setObject);
  }

  /**
   * @param magicItem MagicItem instance to apply changes
   * @return built lore with specific implementation
   */
  public abstract List<Component> buildLore(MagicItem magicItem);

  /**
   * @param magicItem MagicItem instance to apply changes
   * @return built name with specific implementation
   */
  public abstract Component buildName(MagicItem magicItem);
}
