package com.ignitedev.aparecium.example;

import com.ignitedev.aparecium.component.ApareciumComponent;
import java.util.List;
import net.kyori.adventure.text.Component;

@Example
public class ApareciumComponentExample {

  @Example
  public void createComponent(){
    ApareciumComponent stringComponent = new ApareciumComponent("test");
    ApareciumComponent componentComponent = new ApareciumComponent(Component.text("test"));

    ApareciumComponent stringsComponent = new ApareciumComponent(() -> List.of("test", "test"));
    ApareciumComponent componentsComponent = new ApareciumComponent(() -> List.of(Component.text("test"), Component.text("test")));

    ApareciumComponent stringBuilderComponent = ApareciumComponent.builder().string("test").string("nextTest").build();
    ApareciumComponent componentBuilderComponent =
        ApareciumComponent.builder().component(Component.text("test")).component(Component.text("nextTest")).build();
  }

}
