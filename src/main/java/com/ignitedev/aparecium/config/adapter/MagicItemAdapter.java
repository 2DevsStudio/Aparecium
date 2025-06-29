/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.config.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.enums.ItemType;
import com.ignitedev.aparecium.enums.Rarity;
import com.ignitedev.aparecium.factory.FactoriesManager;
import com.ignitedev.aparecium.item.MagicItem;
import com.ignitedev.aparecium.item.basic.DropItem;
import com.ignitedev.aparecium.item.basic.Item;
import com.ignitedev.aparecium.item.basic.LayoutItem;
import com.ignitedev.aparecium.item.basic.PatternItem;
import com.ignitedev.aparecium.item.factory.factories.DefaultMagicItemFactory;
import com.ignitedev.aparecium.util.MinecraftVersionUtility;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;
import java.util.Map.Entry;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.Nullable;

public class MagicItemAdapter implements JsonSerializer<MagicItem>, JsonDeserializer<MagicItem> {

  private static final String CLASSNAME = "CLASSNAME";
  private static final String SAVE_DATE = "saveDate";
  private static final String ID = "id";
  private static final String MATERIAL = "material";

  private static final String AMOUNT = "amount";
  private static final String TYPE = "type";
  private static final String RARITY = "rarity";
  private static final String NAME = "name";
  private static final String DESCRIPTION = "description";
  private static final String TAGS = "tags";
  private static final String ENCHANTMENTS = "enchantments";
  private static final String FLAGS = "flags";
  private static final String DROP_CHANCES_BY_MATERIALS = "dropChancesByMaterials";
  private static final String DROP_CHANCE = "dropChance";
  private static final String INTERACTION_ID = "interactionId";
  private static final String PATTERNS = "patterns";

  @Override
  public MagicItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    Class<?> clazz = getObjectClass(jsonObject.get(CLASSNAME).getAsString());
    FactoriesManager factoriesManager = Aparecium.getFactoriesManager();
    DefaultMagicItemFactory<?> defaultFactory =
        (DefaultMagicItemFactory<?>) factoriesManager.getMagicItemFactories().getDefaultFactory();

    String id = jsonObject.get(ID).getAsString();
    Material material = Material.matchMaterial(jsonObject.get(MATERIAL).getAsString());
    int amount = jsonObject.get(AMOUNT).getAsInt();

    if (material == null) {
      throw new IllegalArgumentException("Material is null");
    }
    @Nullable JsonElement nameElement = jsonObject.get(NAME);
    ApareciumComponent name = null;

    if (nameElement != null) {
      if (!nameElement.isJsonNull()) {
        name = context.deserialize(nameElement.getAsJsonObject(), ApareciumComponent.class);
      }
    }
    @Nullable JsonElement descriptionElement = jsonObject.get(DESCRIPTION);
    ApareciumComponent description = null;

    if (descriptionElement != null) {
      if (!descriptionElement.isJsonNull()) {
        description =
            context.deserialize(descriptionElement.getAsJsonObject(), ApareciumComponent.class);
      }
    }
    @Nullable JsonElement enchantElement = jsonObject.get(ENCHANTMENTS);
    HashMap<String, Double> enchantmentsString = null;

    if (enchantElement != null) {
      enchantmentsString = context.deserialize(enchantElement, HashMap.class);
    }
    HashMap<Enchantment, Integer> enchantments = new HashMap<>();
    @Nullable JsonElement tagsElement = jsonObject.get(TAGS);
    Map<String, Object> tags = null;

    if (tagsElement != null) {
      tags = context.deserialize(tagsElement, HashMap.class);
    }
    @Nullable JsonElement flagsElement = jsonObject.get(FLAGS);
    List<ItemFlag> flags = null;

    if (flagsElement != null) {
      flags = context.deserialize(flagsElement, ArrayList.class);
    }
    @Nullable JsonElement typeElement = jsonObject.get(TYPE);
    ItemType itemType = null;

    if (typeElement != null) {
      itemType = ItemType.valueOf(typeElement.getAsString());
    }
    @Nullable JsonElement rarityElement = jsonObject.get(RARITY);
    Rarity rarity = null;

    if (rarityElement != null) {
      rarity = Rarity.valueOf(rarityElement.getAsString());
    }
    @Nullable JsonElement saveDateElement = jsonObject.get(SAVE_DATE);
    long saveDate = Instant.now().toEpochMilli();

    if (saveDateElement != null) {
      saveDate = saveDateElement.getAsLong();
    }
    if (enchantmentsString != null) {
      for (Entry<String, Double> entry : enchantmentsString.entrySet()) {
        if (MinecraftVersionUtility.isAfter(13)) {
          NamespacedKey key = NamespacedKey.fromString(entry.getKey().toLowerCase(Locale.ROOT));

          if (key == null) {
            throw new JsonParseException(
                "Enchantment key is null, check your enchantments in item config: " + jsonObject);
          }
          enchantments.put(Registry.ENCHANTMENT.get(key), entry.getValue().intValue());
        } else {
          enchantments.put(Enchantment.getByName(entry.getKey()), entry.getValue().intValue());
        }
      }
    }
    Item item =
        defaultFactory.createItem(
            id, material, amount, itemType, rarity, name, description, tags, enchantments, flags);

    item.setItemSaveInstant(Instant.ofEpochMilli(saveDate));

    if (clazz == DropItem.class) {
      // drop item data
      HashMap<Material, Double> dropChancesByMaterials =
          context.deserialize(jsonObject.get(DROP_CHANCES_BY_MATERIALS), HashMap.class);
      double dropChance = jsonObject.get(DROP_CHANCE).getAsDouble();

      return factoriesManager
          .getDropItemFactory()
          .createItem(
              id,
              material,
              amount,
              itemType,
              rarity,
              name,
              description,
              tags,
              enchantments,
              flags,
              dropChance,
              dropChancesByMaterials);
    } else if (clazz == LayoutItem.class) {
      // layout item data
      double interactionId = jsonObject.get(INTERACTION_ID).getAsDouble();

      return factoriesManager
          .getLayoutItemFactory()
          .createItem(
              id,
              material,
              amount,
              itemType,
              rarity,
              name,
              description,
              tags,
              enchantments,
              flags,
              interactionId);
    } else if (clazz == PatternItem.class) {
      // pattern item data
      List<PatternItem> patterns = context.deserialize(jsonObject.get(PATTERNS), ArrayList.class);

      double dropChance = jsonObject.get(DROP_CHANCE).getAsDouble();
      return factoriesManager
          .getPatternItemFactory()
          .createItem(
              id,
              material,
              amount,
              itemType,
              rarity,
              name,
              description,
              tags,
              enchantments,
              flags,
              dropChance,
              dropChance,
              patterns);
    }
    return item;
  }

  @Override
  public JsonElement serialize(MagicItem src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject jsonObject = new JsonObject();
    Class<? extends MagicItem> clazz = src.getClass();
    List<ItemFlag> flags = src.getFlags();
    Map<Enchantment, Integer> enchants = src.getEnchants();
    Map<String, Object> tags = src.getTags();
    ApareciumComponent description = src.getDescription();
    Map<String, Integer> enchantments = new HashMap<>();

    jsonObject.addProperty(ID, src.getId());
    jsonObject.addProperty(MATERIAL, src.getMaterial().name());
    jsonObject.addProperty(AMOUNT, src.getAmount());
    jsonObject.add(NAME, context.serialize(src.getName()));

    if (description != null) {
      jsonObject.add(DESCRIPTION, context.serialize(description));
    }
    if (enchants != null) {
      // for versions before 1.13 enchantments are stored as a map of enchantment name and level
      // for versions after 1.13 enchantments are stored as a map of enchantment and level,
      // so we need to convert it to the old format
      if (MinecraftVersionUtility.isAfter(13)) {
        enchants.forEach(
            (enchantment, level) -> enchantments.put(enchantment.getKey().getKey(), level));
      } else {
          //noinspection removal
          enchants.forEach((enchantment, level) -> enchantments.put(enchantment.getName(), level));
      }
    }
    jsonObject.add(ENCHANTMENTS, context.serialize(enchantments));

    if (tags != null) {
      jsonObject.add(TAGS, context.serialize(new HashMap<>(tags)));
    }
    if (flags != null) {
      jsonObject.add(FLAGS, context.serialize(new ArrayList<>(flags)));
    }
    if (clazz == DropItem.class) {
      // drop item data
      DropItem dropItem = (DropItem) src;

      jsonObject.addProperty(DROP_CHANCE, dropItem.getDropChance());
      jsonObject.add(
          DROP_CHANCES_BY_MATERIALS, context.serialize(dropItem.getDropChancesForMaterials()));
    } else if (clazz == LayoutItem.class) {
      // layout item data
      jsonObject.addProperty(INTERACTION_ID, ((LayoutItem) src).getLayoutItemInteractionId());
    } else if (clazz == PatternItem.class) {
      // pattern item data
      PatternItem patternItem = (PatternItem) src;

      jsonObject.addProperty(DROP_CHANCE, patternItem.getDropChance());
      jsonObject.add(PATTERNS, context.serialize(patternItem.getPatterns()));
    }
    jsonObject.addProperty(TYPE, src.getItemType().name());
    jsonObject.addProperty(RARITY, src.getRarity().name());
    if (src.getItemSaveInstant() != null) {
      jsonObject.addProperty(SAVE_DATE, src.getItemSaveInstant().toEpochMilli());
    }
    jsonObject.addProperty(CLASSNAME, clazz.getName());

    return jsonObject;
  }

  private Class<?> getObjectClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException exception) {
      throw new JsonParseException(exception.getMessage());
    }
  }
}
