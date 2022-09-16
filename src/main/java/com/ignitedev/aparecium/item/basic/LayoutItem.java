package com.ignitedev.aparecium.item.basic;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.config.ItemBase;
import com.twodevsstudio.simplejsonconfig.api.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Data
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LayoutItem extends Item {

  /** Interaction id for interaction hook */
  private double layoutItemInteractionId;

  public LayoutItem(MagicItemBuilder<?, ?> builder) {
    super(builder);
  }

  public LayoutItem(@NotNull String id, @NotNull Material material) {
    super(id, material);
  }

  public LayoutItem(@NotNull String id, @NotNull Material material, ApareciumComponent name) {
    super(id, material, name);
  }

  public LayoutItem(
      @NotNull String id,
      @NotNull Material material,
      ApareciumComponent name,
      ApareciumComponent description) {
    super(id, material, name, description);
  }

  public static LayoutItem getCachedLayoutItem(String string) {
    return ((LayoutItem) Config.getConfig(ItemBase.class).getById(string));
  }

  @Override
  public ItemStack toItemStack(int amount) {
    return Aparecium.getFactoriesManager()
        .getMagicItemFactories()
        .getDefaultFactory()
        .toItemStack(this, amount);
  }

  @Override
  public LayoutItem clone() {
    LayoutItem clone = ((LayoutItem) super.clone());

    clone.layoutItemInteractionId = this.layoutItemInteractionId;

    return clone;
  }
}
