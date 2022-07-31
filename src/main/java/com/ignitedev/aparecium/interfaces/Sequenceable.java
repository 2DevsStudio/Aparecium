package com.ignitedev.aparecium.interfaces;

public interface Sequenceable {

  int getCurrentSequenceIndex();

  void moveToNextSequence();

  void moveToPreviousSequence();

  void setSequence(int sequenceIndex);
}
