/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.enums;

/**
 * @implNote plugin start phase
 */
public enum StartupStage {
  PRE_LOAD,
  INNER_LOAD,
  POST_LOAD,
  ENABLING,
  ENABLED,
  DISABLING,
  DISABLED
}
