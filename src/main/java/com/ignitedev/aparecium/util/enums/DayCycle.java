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
