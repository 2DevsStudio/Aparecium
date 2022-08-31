package com.ignitedev.aparecium.item.repository;

import com.ignitedev.aparecium.interfaces.Repository;
import com.ignitedev.aparecium.item.MagicItem;
import de.tr7zw.nbtapi.NBTItem;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagicItemRepository implements Repository<MagicItem> {

  private final Map<String, MagicItem> cachedItems = new HashMap<>();

  @NotNull
  public static MagicItemRepository getInstance() {
    return SingletonHelper.INSTANCE;
  }

  @Override
  @Nullable
  public MagicItem findById(String identifier) {
    return cachedItems.get(identifier);
  }

  @Nullable
  public MagicItem findByItemStack(ItemStack itemStack){
    NBTItem nbtItem = new NBTItem(itemStack, true);
    String id = nbtItem.getString("id");

    return findById(id);
  }


  @Override
  public void remove(String identifier) {
    cachedItems.remove(identifier);
  }

  @Override
  public void add(MagicItem value) {
    cachedItems.put(value.getId(), value);
  }

  private static class SingletonHelper {
    private static final MagicItemRepository INSTANCE = new MagicItemRepository();
  }
}
