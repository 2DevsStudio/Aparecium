package com.ignitedev.aparecium.interfaces;

@FunctionalInterface
public interface UnsafeFunction<K, T> {

  T apply(K k) throws Exception;
}
