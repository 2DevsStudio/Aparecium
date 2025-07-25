/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.interfaces;

public interface Sequenceable {

  int getCurrentSequenceIndex();

  void moveToNextSequence();

  void moveToPreviousSequence();

  void setSequence(int sequenceIndex);
}
