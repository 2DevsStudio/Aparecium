/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.item.basic;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.item.MagicItem;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @implNote This is default implementation of MagicItem, you should use it if you don't need any
 *     additional type of custom implementation, or you should use it for creating your own
 *     implementation
 */
@Data
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Item extends MagicItem {

  public Item(MagicItemBuilder<?, ?> builder) {
    super(builder);
  }

  public Item(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags, @Nullable Map<Enchantment, Integer> enchantments,
      @Nullable List<ItemFlag> flags) {
    super(id, material, itemType, rarity, name, description, tags, enchantments, flags);

    if (this.rarity == null) {
      this.rarity = Rarity.NOT_SPECIFIED;
    }
    if (this.itemType == null) {
      this.itemType = ItemType.getByMaterial(this.material);
    }
  }

  @Override
  public ItemStack toItemStack(int amount) {
    return Aparecium.getFactoriesManager()
        .getMagicItemFactories()
        .getDefaultFactory()
        .toItemStack(this, amount);
  }

  @Override
  public Item clone() {
    return (Item) super.clone();
  }
}
