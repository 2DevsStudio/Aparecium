package com.ignitedev.aparecium.config;

import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.interfaces.Configuration;
import java.util.Map;
import lombok.Getter;

@SuppressWarnings("FieldMayBeFinal")
@Getter
@Configuration("Aparecium-Configuration.json")
public class ApareciumConfiguration extends Config {

  /*
   * Default Rarity Configuration
   */

  /**
   * @implNote <Rarity Key Name, Rarity Display Name>
   */
  public Map<String, String> rarityName =
      Map.of(
          "not_specified",
          "<black><not_specified>",
          "common",
          "<gray> {COMMON}",
          "uncommon",
          "<yellow> {UNCOMMON}",
          "rare",
          "<orange> {RARE}",
          "epic",
          "<green> {EPIC}",
          "legendary",
          "<bold><gold> {LEGENDARY}",
          "mythic",
          "<purple> {MYTHIC}");

  public Map<String, String> itemType =
      Map.of(
          "common", "<gray> {COMMON}",
          "tool", "<brown> {TOOL}",
          "WEAPON", "<red> {WEAPON}",
          "ARMOR", "<black> {ARMOR}",
          "POTION", "<pink> {POTION}");
}
