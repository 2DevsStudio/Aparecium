package com.ignitedev.aparecium.item.factory.factories;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.item.basic.ClickableItem;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClickableItemFactory extends DefaultMagicItemFactory<ClickableItem> {

  public ClickableItem createItem(
      @NotNull String id,
      @NotNull Material material,
      int amount,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      @Nullable Map<Enchantment, Integer> enchants,
      @Nullable List<ItemFlag> flags,
      Consumer<Player> onLeftClick,
      Consumer<Player> onRightClick,
      Consumer<Player> onLeftClickInventory,
      Consumer<Player> onRightClickInventory) {
    return new ClickableItem(
        id,
        material,
        amount,
        itemType,
        rarity,
        name,
        description,
        tags,
        enchants,
        flags,
        onRightClick,
        onLeftClick,
        onLeftClickInventory,
        onRightClickInventory);
  }
}
