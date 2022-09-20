/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
@RequiredArgsConstructor
public class PageObject<P> {

  private final List<P> objects;
  private final int objectsCountPerPage;
  private final Map<Integer, List<P>> pages = new HashMap<>();

  public PageObject(Collection<P> objects, int objectsCountPerPage) {
    this.objects = new ArrayList<>();
    this.objectsCountPerPage = objectsCountPerPage;
    this.objects.addAll(objects);
  }

  private void loadPages() {
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

  public int getPagesAmount() {
    return pages.size();
  }

  @Nullable
  public List<P> getObjectsFromPage(int page) {
    return pages.get(page);
  }
}
