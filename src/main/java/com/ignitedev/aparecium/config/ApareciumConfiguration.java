/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.config;

import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.interfaces.Configuration;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@SuppressWarnings("FieldMayBeFinal")
@Getter
@Configuration(value = "Aparecium-Configuration.json", configPath = "ApareciumConfiguration/")
public class ApareciumConfiguration extends Config {

  /*
   * Default Rarity Configuration
   */

  /**
   * @implNote <Rarity Key Name, Rarity Display Name>
   */
  public Map<String, String> rarityName =
      new HashMap<>(
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
              "<purple> {MYTHIC}"));

  public Map<String, String> itemType =
      new HashMap<>(
          Map.of(
              "common", "<gray> {COMMON}",
              "tool", "<brown> {TOOL}",
              "WEAPON", "<red> {WEAPON}",
              "ARMOR", "<black> {ARMOR}",
              "POTION", "<pink> {POTION}"));
}
