package com.ignitedev.aparecium.interfaces;

import java.util.List;
import net.kyori.adventure.audience.Audience;

public interface Removable<T, D extends Audience> {

  T remove(D d);

  T remove(List<D> list);
}
