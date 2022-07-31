package com.ignitedev.aparecium.interfaces;

import java.util.List;
import org.bukkit.entity.Player;

public interface Spawnable {

  void spawn(List<Player> receivers);

  void spawn(Player receiver);
}
