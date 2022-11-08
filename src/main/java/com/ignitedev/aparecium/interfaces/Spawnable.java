/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.interfaces;

import java.util.List;
import org.bukkit.entity.Player;

public interface Spawnable {

  void spawn(List<Player> receivers);

  void spawn(Player receiver);
}
