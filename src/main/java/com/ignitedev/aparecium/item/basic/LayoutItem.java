package com.ignitedev.aparecium.item.basic;

import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LayoutItem extends Item {

  /** Interaction id for interaction hook */
  public double layoutItemInteractionId;

  public LayoutItem(
      @NotNull String id,
      @NotNull Material material,
      @Nullable ItemType itemType,
      @Nullable Rarity rarity,
      @Nullable ApareciumComponent name,
      @Nullable ApareciumComponent description,
      @Nullable Map<String, Object> tags,
      double layoutItemInteractionId) {
    super(id, material, itemType, rarity, name, description, tags);
    this.layoutItemInteractionId = layoutItemInteractionId;
  }

  @Override
  public LayoutItem clone() {
    LayoutItem clone = ((LayoutItem) super.clone());

    clone.layoutItemInteractionId = this.layoutItemInteractionId;

    return clone;
  }
}
