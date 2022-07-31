package com.ignitedev.aparecium.item.factory.factories;

import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.item.factory.RawItemStackFactory;
import com.ignitedev.aparecium.util.text.TextUtility;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;

/**
 * @implNote Default MagicItem Factory with implemented text assets
 */
public class DefaultMagicItemFactory extends RawItemStackFactory {

  @Override
  public List<Component> buildLore(MagicItem magicItem) {
    return TextUtility.parseMiniMessage(new ArrayList<>(magicItem.getDescription()));
  }

  @Override
  public Component buildName(MagicItem magicItem) {
    return TextUtility.parseMiniMessage(magicItem.getName());
  }
}
