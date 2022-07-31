package com.ignitedev.aparecium.interfaces;

import com.ignitedev.curioslibrary.callback.Callback;
import java.util.List;
import java.util.function.Consumer;

public interface Usable<T> {

  String getCallbackId();

  void onUse(List<Callback<T>> callbacks);

  void onUse(Callback<T> callback);

  void onUse(Consumer<T> consumer);

  void use(T t);
}
