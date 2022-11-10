/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.item;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.config.wrapper.MagicItemWrapper;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.enums.SimilarCheck;
import com.ignitedev.aparecium.interfaces.Identifiable;
import com.ignitedev.aparecium.item.util.MagicItemConverter;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.Singular;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @implNote Base abstraction layer of Item in Aparecium
 * @implNote To Create implementation of that abstraction use {{@link
 *     com.ignitedev.aparecium.item.basic.Item}}
 */
@Data
@SuperBuilder(toBuilder = true)
public abstract class MagicItem implements Cloneable, Identifiable, Comparable<MagicItem> {

  /**
   * @implNote Item creation Instant
   */
  @Nullable @Setter protected Instant itemSaveInstant;

  @NotNull protected String id;
  @NotNull @Builder.Default protected Material material = Material.AIR;
  /**
   * @implNote Item Type useful for sorting and categorizing
   */
  @Builder.Default protected ItemType itemType = ItemType.COMMON;
  /**
   * @implNote Rarity of item, useful for rarity api, sorting(+filterer) api, or any other RNG you
   *     want to create using it
   */
  @Builder.Default protected Rarity rarity = Rarity.NOT_SPECIFIED;
  /**
   * @implNote name of item applicable to itemstack
   */
  @Nullable protected ApareciumComponent name;
  /**
   * @implNote lore applicable to itemstack
   */
  @Nullable protected ApareciumComponent description;
  /**
   * @implNote NBT-TAGS applicable to ItemStack
   */
  @Nullable @Singular protected Map<String, Object> tags;

  @Nullable @Singular protected Map<Enchantment, Integer> enchants;
  @Nullable @Singular protected List<ItemFlag> flags;

  public MagicItem(
      @NotNull String id,
      @NotNull Material material,
      ItemType itemType,
      Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      @Nullable Map<Enchantment, Integer> enchants,
      @Nullable List<ItemFlag> flags) {
    this.id = id;
    this.material = material;
    this.itemType = itemType;
    this.rarity = rarity;
    this.name = name;
    this.description = description;
    this.tags = tags;
    this.enchants = enchants;
    this.flags = flags;
  }

  /**
   * @param amount amount of itemstack you'd like to get
   * @return Prepared itemstack with all specified values and methods
   */
  public abstract ItemStack toItemStack(int amount);

  /**
   * @param player player which will receive item
   */
  public void give(Player player) {
    give(player, 1);
  }

  /**
   * @param player player which will receive item
   * @param amount amount of this item that player going to receive
   */
  public void give(Player player, int amount) {
    player.getInventory().addItem(toItemStack(amount <= 0 ? 1 : amount));
  }

  public boolean isSimilar(MagicItem toCheck, SimilarCheck... similarCheck) {
    boolean isSimilar = false;

    for (SimilarCheck check : similarCheck) {
      if (check == SimilarCheck.ALL || check == SimilarCheck.ID) {
        if (this.getId().equalsIgnoreCase(toCheck.getId())) {
          isSimilar = true;
        }
      }
      if (check == SimilarCheck.ALL || check == SimilarCheck.NAME) {
        isSimilar =
            MagicItemConverter.isSimilarComponent(isSimilar, this.getName(), toCheck.getName());
      }
      if (check == SimilarCheck.ALL || check == SimilarCheck.LORE) {
        isSimilar =
            MagicItemConverter.isSimilarComponent(
                isSimilar, this.getDescription(), toCheck.getDescription());
      }
      if (check == SimilarCheck.ALL || check == SimilarCheck.NBT_TAGS) {
        if (this.tags != null) {
          if (this.tags.equals(toCheck.getTags())) {
            isSimilar = true;
          }
        }
      }
    }
    return isSimilar;
  }

  public MagicItemWrapper toWrapper(){
    return new MagicItemWrapper(null, this);
  }

  /**
   * @implNote Please note that item creation Instant will be the same for cloned version
   */
  @Override
  @SneakyThrows
  public MagicItem clone() {
    MagicItem clone = (MagicItem) super.clone();

    clone.description = this.description;
    clone.name = this.name;
    clone.material = this.material;
    clone.rarity = this.rarity;
    clone.itemType = this.itemType;
    clone.enchants = this.enchants;
    clone.flags = this.flags;
    if (tags != null) {
      clone.tags = Map.copyOf(tags);
    }
    clone.id = this.id;

    return clone;
  }

  /**
   * @implNote this comparable implementation is sorting by order of {{@link #getItemSaveInstant()}}
   *     (item creation order)
   */
  @Override
  public int compareTo(@NotNull MagicItem compareTo) {
    int compare = 0;

    if (itemSaveInstant != null && compareTo.getItemSaveInstant() != null) {
      compare =
          Long.compare(
              itemSaveInstant.getEpochSecond(), compareTo.getItemSaveInstant().getEpochSecond());
    }
    if (compare != 0) {
      return compare;
    }
    if (itemSaveInstant != null && compareTo.getItemSaveInstant() != null) {
      return itemSaveInstant.getNano() - compareTo.getItemSaveInstant().getNano();
    }
    return compare;
  }
}
