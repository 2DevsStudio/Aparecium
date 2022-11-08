/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.item;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.interfaces.Identifiable;
import com.ignitedev.aparecium.util.ItemUtility;
import java.time.Instant;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @implNote Base abstraction layer of Item in Aparecium
 * @implNote To Create implementation of that abstraction use {{@link
 *     com.ignitedev.aparecium.item.basic.Item}}
 */
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public abstract class MagicItem implements Cloneable, Identifiable, Comparable<MagicItem> {

  /**
   * @implNote Item creation Instant
   */
  protected final Instant itemSaveInstant = Instant.now();

  @NotNull protected String id;

  @NotNull @Builder.Default protected Material material = Material.AIR;

  /**
   * @implNote Item Type useful for sorting and categorizing
   */
  @Builder.Default protected ItemType itemType = ItemUtility.getItemTypeByMaterial(material);
  // null

  /**
   * @implNote Rarity of item, useful for rarity api, sorting(+filterer) api, or any other RNG you
   *     want to create using it
   */
  @Builder.Default protected Rarity rarity = Rarity.NOT_SPECIFIED;

  /**
   * @implNote name of item applicable to itemstack
   */
  protected ApareciumComponent name;

  /**
   * @implNote lore applicable to itemstack
   */
  protected ApareciumComponent description;

  /**
   * @implNote NBT-TAGS applicable to ItemStack
   */
  @Singular protected Map<String, Object> tags;

  public MagicItem(@NotNull String id, @NotNull Material material) {
    this.id = id;
    this.material = material;
    itemType = ItemUtility.getItemTypeByMaterial(material);
  }

  public MagicItem(@NotNull String id, @NotNull Material material, ApareciumComponent name) {
    this.id = id;
    this.material = material;
    this.name = name;
    itemType = ItemUtility.getItemTypeByMaterial(material);
  }

  public MagicItem(
      @NotNull String id,
      @NotNull Material material,
      ApareciumComponent name,
      ApareciumComponent description) {
    this.id = id;
    this.material = material;
    this.name = name;
    this.description = description;
    itemType = ItemUtility.getItemTypeByMaterial(material);
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

  /**
   * @implNote Please note that item creation Instant will be the same for cloned version
   */
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

  /**
   * @implNote this comparable implementation is sorting by order of {{@link #getItemSaveInstant()}}
   *     (item creation order)
   */
  @Override
  public int compareTo(@NotNull MagicItem compareTo) {
    int compare =
        Long.compare(
            itemSaveInstant.getEpochSecond(), compareTo.getItemSaveInstant().getEpochSecond());

    if (compare != 0) {
      return compare;
    }
    return itemSaveInstant.getNano() - compareTo.getItemSaveInstant().getNano();
  }
}
