/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.interfaces;

import java.util.List;
import net.kyori.adventure.audience.Audience;

public interface Removable<T, D extends Audience> {

  T remove(D d);

  T remove(List<D> list);
}
