package com.ignitedev.aparecium.item.basic;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ClickableItem extends Item {

  private Consumer<Player> onRightClick;

  private Consumer<Player> onLeftClick;

  private Consumer<Player> onLeftClickInventory;

  private Consumer<Player> onRightClickInventory;

  public ClickableItem(
      @NotNull String id,
      @NotNull Material material,
      int amount,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      @Nullable Map<Enchantment, Integer> enchantments,
      @Nullable List<ItemFlag> flags,
      Consumer<Player> onRightClick,
      Consumer<Player> onLeftClick,
      Consumer<Player> onLeftClickInventory,
      Consumer<Player> onRightClickInventory) {
    super(id, material, amount, itemType, rarity, name, description, tags, enchantments, flags);
    this.onRightClick = onRightClick;
    this.onLeftClick = onLeftClick;
    this.onLeftClickInventory = onLeftClickInventory;
    this.onRightClickInventory = onRightClickInventory;
  }

  @Override
  public Item clone() {
    ClickableItem clone = (ClickableItem) super.clone();

    clone.onLeftClick = this.onLeftClick;
    clone.onRightClick = this.onRightClick;
    clone.onRightClickInventory = this.onRightClickInventory;
    clone.onLeftClickInventory = this.onLeftClickInventory;

    return clone;
  }
}
