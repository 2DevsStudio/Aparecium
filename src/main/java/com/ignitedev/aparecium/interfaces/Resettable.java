package com.ignitedev.aparecium.interfaces;

import net.kyori.adventure.audience.Audience;

public interface Resettable<T> {

  T reset(Audience audience);
}
