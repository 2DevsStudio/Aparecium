/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.item;

import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.interfaces.Identifiable;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @implNote Base representation of item in Aparecium
 */
@Data
@SuperBuilder(toBuilder = true)
public abstract class MagicItem implements Cloneable, Identifiable {
  protected String id;
  @Builder.Default protected Material material = Material.AIR;
  /**
   * @implNote Item Type useful for sorting and categorizing
   */
  @Builder.Default protected ItemType itemType = ItemType.COMMON;
  /**
   * @implNote Rarity of item, useful for rarity api, sorting(+filterer) api, or any other RNG you
   *     want to create using it
   */
  @Builder.Default protected Rarity rarity = Rarity.NOT_SPECIFIED;

  protected String name;

  /**
   * @implNote lore applicable to itemstack
   */
  @Singular("addDescription")
  protected List<String> description;

  /**
   * @implNote NBT-TAGS applicable to ItemStack
   */
  @Singular protected Map<String, Object> tags;

  /**
   * @param amount amount of itemstack you'd like to get
   * @return Prepared itemstack with all specified values and methods
   */
  public abstract ItemStack toItemStack(int amount);

  @Override
  @SneakyThrows
  public MagicItem clone() {
    MagicItem clone = (MagicItem) super.clone();

    clone.name = this.name;
    clone.material = this.material;
    clone.rarity = this.rarity;
    clone.itemType = this.itemType;
    clone.tags = Map.copyOf(tags);
    clone.id = this.id;

    return clone;
  }
}
