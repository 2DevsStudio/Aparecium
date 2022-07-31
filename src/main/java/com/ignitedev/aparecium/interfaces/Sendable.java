package com.ignitedev.aparecium.interfaces;

import net.kyori.adventure.audience.Audience;

public interface Sendable<T> {

  T send(Audience audience);
}
