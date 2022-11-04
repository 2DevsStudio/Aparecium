package com.ignitedev.aparecium.exception;

public class IncorrectEngineException extends Exception {
  public IncorrectEngineException() {
    super("You are trying to run code only executable with Paper Engine");
  }
}
