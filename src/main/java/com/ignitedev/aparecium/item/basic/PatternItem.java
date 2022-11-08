package com.ignitedev.aparecium.item.basic;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.util.MathUtility;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bukkit.inventory.ItemStack;

@Data
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PatternItem extends LayoutItem {

  /**
   * @implNote Global drop chance
   */
  private double dropChance;

  @Singular("addPattern")
  private List<PatternItem> patterns;

  public PatternItem(MagicItemBuilder<?, ?> builder) {
    super(builder);
  }

  /**
   * @return list of items which pass successful {{@link #tryLuck()}}
   */
  public List<PatternItem> roll() {
    List<PatternItem> positiveItems = new ArrayList<>();

    this.patterns.forEach(
        patterns -> {
          if (patterns.tryLuck()) {
            positiveItems.add(patterns.clone());
          }
        });
    return positiveItems;
  }

  /**
   * Changes current item to one from patterns
   */
  public void rollSet() {
    for (PatternItem pattern : this.patterns) {
      PatternItem clone = pattern.clone();

      if (pattern.tryLuck()) {
        this.setMaterial(clone.getMaterial());
        this.setId(clone.getId());
        this.setName(clone.getName());
        this.setDescription(clone.getDescription());
        this.setRarity(clone.getRarity());
        this.setDropChance(clone.getDropChance());
        this.setTags(clone.getTags());
        break;
      }
    }
  }

  /**
   * @return try your luck with {{{@link #dropChance}}}
   */
  public boolean tryLuck() {
    return MathUtility.getRandomPercent(dropChance);
  }

  @Override
  public ItemStack toItemStack(int amount) {
    return Aparecium.getFactoriesManager()
        .getMagicItemFactories()
        .getDefaultFactory()
        .toItemStack(this, amount);
  }

  @Override
  public PatternItem clone() {
    PatternItem clone = ((PatternItem) super.clone());

    clone.dropChance = this.dropChance;
    clone.patterns = this.patterns;

    return clone;
  }
}
