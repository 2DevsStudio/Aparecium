/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.interfaces;

import net.kyori.adventure.audience.Audience;

public interface Resettable<T> {

  T reset(Audience audience);
}
