/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

/**
 * A utility class for managing paginated objects.
 * Allows splitting a collection of objects into pages and retrieving objects by page.
 *
 * @param <P> The type of objects to be paginated.
 */
@SuppressWarnings("unused")
@RequiredArgsConstructor
public class PageObject<P> {

  /** The list of objects to be paginated. */
  private final List<P> objects;

  /** The number of objects per page. */
  private final int objectsCountPerPage;

  /** A map storing pages with their corresponding objects. */
  private final Map<Integer, List<P>> pages = new HashMap<>();

  /**
   * Constructs a PageObject with a collection of objects and the number of objects per page.
   *
   * @param objects The collection of objects to be paginated.
   * @param objectsCountPerPage The number of objects per page.
   */
  public PageObject(Collection<P> objects, int objectsCountPerPage) {
    this.objects = new ArrayList<>();
    this.objectsCountPerPage = objectsCountPerPage;
    this.objects.addAll(objects);
  }

  /**
   * Loads all pages based on the provided objects and objects count per page.
   * This method must be called to initialize the pages.
   */
  public void loadPages() {
    pages.clear();

    int counter = 0;
    int actualPage = 1;

    List<P> loadedObjects = new ArrayList<>();

    for (P object : objects) {
      counter++;
      if (counter <= objectsCountPerPage) {
        loadedObjects.add(object);
      } else {
        pages.put(actualPage, new ArrayList<>(loadedObjects));
        loadedObjects.clear();
        actualPage++;
        loadedObjects.add(object);
        counter = 1;
      }
    }
    if (loadedObjects.size() > 0) {
      pages.put(actualPage, new ArrayList<>(loadedObjects));
    }
  }

  /**
   * Retrieves the total number of pages.
   *
   * @return The number of pages.
   */
  public int getPagesAmount() {
    return pages.size();
  }

  /**
   * Retrieves the objects from a specific page.
   *
   * @param page The page number to retrieve objects from.
   * @return A list of objects from the specified page, or null if the page does not exist.
   */
  @Nullable
  public List<P> getObjectsFromPage(int page) {
    return pages.get(page);
  }
}