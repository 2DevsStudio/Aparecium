/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.interfaces;

import java.util.List;
import org.bukkit.entity.Player;

public interface Removable<T, D extends Player> {

  T remove(D d);

  T remove(List<D> list);
}
