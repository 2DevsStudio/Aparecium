package com.ignitedev.aparecium.hologram.basic;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.hologram.data.HologramEntry;
import com.ignitedev.aparecium.hologram.factory.HologramFactory;
import com.ignitedev.aparecium.hologram.manager.HologramManager;
import com.ignitedev.aparecium.util.text.Placeholder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Singular;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @implNote base implementation of Hologram, if you want to create your own implementation you can
 *     extend that class look at {@link com.ignitedev.aparecium.hologram.SimpleBaseHologram} it is
 *     simple implementation of that class
 */
@Getter
@SuperBuilder(toBuilder = true)
public abstract class AbstractBaseHologram implements BaseHologram {

  /**
   * @implNote hologram cached instance, not applicable for serialization
   */
  @Nullable private transient Hologram hologram;

  private String id;
  private Location location;

  /**
   * @implNote you can create hologram entry by {@link HologramEntry#HologramEntry(String,
   *     Material)}
   */
  @Singular("line")
  @NotNull
  private List<HologramEntry> lines;

  /**
   * @implNote placeholder system, you can simply create placeholder by {@link
   *     Placeholder#Placeholder(String, String)}
   */
  @Singular("data")
  @NotNull
  private List<Placeholder> placeholderData;

  public AbstractBaseHologram(
      String id,
      Location location,
      @NotNull List<HologramEntry> lines,
      @NotNull List<Placeholder> placeholderData) {
    this.id = id;
    this.location = location;
    this.lines = lines;
    this.placeholderData = placeholderData;
  }

  public AbstractBaseHologram(String id, Location location, @NotNull List<HologramEntry> lines) {
    this.id = id;
    this.location = location;
    this.lines = lines;
  }

  /**
   * @implNote spawning hologram once more
   * @param apareciumInstance required for spawning
   */
  @Override
  public void update(Aparecium apareciumInstance) {
    if (hologram != null) {
      hologram.delete();
    }
    spawn(
        Aparecium.getFactoriesManager().getHologramFactories().getDefaultFactory(),
        apareciumInstance);
  }

  @Override
  public boolean isSpawned() {
    return hologram != null;
  }

  @Override
  public void spawn(HologramFactory factory, Aparecium apareciumInstance) {
    hologram = factory.createHologram(this, apareciumInstance);
    HologramManager.ACTIVE_BASE_HOLOGRAMS.add(this);
  }

  /**
   * @implNote destroy current hologram and remove it from cache
   */
  @Override
  public void destroy() {
    if (hologram != null) {
      hologram.delete();
    }
    HologramManager.ACTIVE_BASE_HOLOGRAMS.remove(this);
  }

  @Override
  public HologramEntry getLine(int index) {
    return lines.get(index);
  }

  @Override
  public HologramEntry setLine(int index, HologramEntry hologramEntry) {
    return lines.set(index, hologramEntry);
  }

  @Override
  @Nullable
  public Placeholder getPlaceholderData(String key) {
    return placeholderData.stream()
        .filter(placeholder -> placeholder.getKey().equals(key))
        .findAny()
        .orElse(null);
  }

  @Override
  public void setPlaceholderData(String key, String value) {
    Placeholder placeholderData = getPlaceholderData(key);

    if (placeholderData != null) {
      placeholderData.setValue(value);
    }
  }

  @Override
  @SneakyThrows
  public AbstractBaseHologram clone() {
    AbstractBaseHologram clone = (AbstractBaseHologram) super.clone();

    clone.id = this.id;
    clone.location = location.clone();
    clone.lines = new ArrayList<>(this.lines);
    clone.placeholderData =
        this.placeholderData.stream()
            .map(placeholder -> Placeholder.replacing(placeholder.getKey(), placeholder.getValue()))
            .collect(Collectors.toCollection(ArrayList::new));

    return clone;
  }
}
