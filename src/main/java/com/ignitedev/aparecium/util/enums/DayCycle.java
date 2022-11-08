/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @implNote Minecraft's day/night ticks representation as enum
 */
@Getter
@RequiredArgsConstructor
public enum DayCycle {
  DAY(0, 12300),
  NIGHT(12300, 24000L),
  ALL_DAY(0, 24000);

  private final long minTime;
  private final long maxTime;
}
