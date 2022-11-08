package com.ignitedev.aparecium.item.basic;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.item.repository.MagicItemRepository;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @implNote This is default implementation of MagicItem, you should use it if you don't need any
 *     additional type of custom implementation, or you should use it for creating your own
 *     implementation, it is using additional logic like caching items in repository {{@link
 *     com.ignitedev.aparecium.item.repository.MagicItemRepository}}
 */
@Data
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Item extends MagicItem {

  public Item(MagicItemBuilder<?, ?> builder) {
    super(builder);
    MagicItemRepository.getInstance().add(this);
  }

  public Item(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags) {
    super(id, material, itemType, rarity, name, description, tags);

    if (this.rarity == null) {
      this.rarity = Rarity.NOT_SPECIFIED;
    }
    if (this.itemType == null) {
      this.itemType = ItemType.getByMaterial(this.material);
    }
    MagicItemRepository.getInstance().add(this);
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
