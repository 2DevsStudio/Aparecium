/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.interfaces;

import java.util.function.Consumer;

public interface Usable<T> {

  String getCallbackId();

//  void onUse(List<Callback<T>> callbacks);
//
//  void onUse(Callback<T> callback);

  void onUse(Consumer<T> consumer);

  void use(T t);
}
