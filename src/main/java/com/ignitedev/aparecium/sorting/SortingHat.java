/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.sorting;

/*
 * "Oh you may not think I'm pretty, but don't judge on what you see,
 *  I'll eat myself if you can find a smarter hat than me."
 *
 *   ~~The Sorting Hat's opening lines of the 1991 Sorting Hat's song
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @param <T> Class that implement Comparable interface with the same type, for SortingHat natural
 *     ordering set entries
 */
@Data
@NoArgsConstructor
public class SortingHat<T extends Comparable<T>> {

  private final Map<String, Predicate<? super T>> filters = new HashMap<>();
  private Set<T> collection = new ConcurrentSkipListSet<>();

  public SortingHat(Collection<T> value) {
    this.collection = new ConcurrentSkipListSet<>();
    this.collection.addAll(value);
  }

  /**
   * @implNote Collection is sorted by default using comparator implementation, so you don't need to
   *     use that method, this method is used to get clone of collection sorted by comparator
   *     specified
   * @param comparator comparator to sort {@link Comparator#comparing(Function)}
   * @return cloned collection (ConcurrentSkipListSet) sorted by given comparator
   */
  public Set<T> sort(Comparator<? super T> comparator) {
    Stream<T> stream = getCollection().stream().sorted(comparator);

    return stream.collect(Collectors.toCollection(ConcurrentSkipListSet::new));
  }

  /**
   * @return cloned collection with only filtered elements
   */
  public Set<T> filter() {
    Stream<T> stream = getCollection().stream();

    for (Predicate<? super T> value : filters.values()) {
      stream = stream.filter(value);
    }
    return stream.collect(Collectors.toCollection(ConcurrentSkipListSet::new));
  }

  /**
   * @return copy of Collection
   */
  public Set<T> getCollection() {
    return new ConcurrentSkipListSet<>(this.collection);
  }

  public SortingHat<T> add(T value) {
    collection.add(value);
    return this;
  }

  @SafeVarargs
  public final SortingHat<T> add(T... value) {
    this.collection.addAll(Arrays.asList(value));
    return this;
  }

  public SortingHat<T> addAll(Collection<T> value) {
    this.collection.addAll(value);
    return this;
  }

  public SortingHat<T> remove(T value) {
    this.collection.remove(value);
    return this;
  }

  public SortingHat<T> clearCollection() {
    this.collection.clear();
    return this;
  }

  public SortingHat<T> clearFilters() {
    this.filters.clear();
    return this;
  }

  /**
   * @implNote Filter is not directly applied to the collection, you can get filtered collection
   *     copy by {@link SortingHat#filter()}
   */
  public SortingHat<T> addFilter(String filterName, Predicate<? super T> filter) {
    this.filters.putIfAbsent(filterName, filter);
    return this;
  }

  public SortingHat<T> removeFilter(String filterName) {
    this.filters.remove(filterName);
    return this;
  }
}
