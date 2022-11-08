/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.interfaces;

import org.bukkit.entity.Player;

public interface Sendable<T> {

  T send(Player player);
}
