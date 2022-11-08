package com.ignitedev.aparecium.example;

import com.ignitedev.aparecium.component.ApareciumComponent;
import java.util.List;
import net.kyori.adventure.text.Component;

@Example
public class ApareciumComponentExample {

  @Example
  public void createComponent() {
    @Example
    ApareciumComponent stringComponent = new ApareciumComponent("test");
    @Example
    ApareciumComponent componentComponent = new ApareciumComponent(Component.text("test"));

    @Example
    ApareciumComponent stringsComponent = new ApareciumComponent(() -> List.of("test", "test"));
    @Example
    ApareciumComponent componentsComponent =
        new ApareciumComponent(() -> List.of(Component.text("test"), Component.text("test")));

    @Example
    ApareciumComponent stringBuilderComponent =
        ApareciumComponent.builder().string("test").string("nextTest").build();
    @Example
    ApareciumComponent componentBuilderComponent =
        ApareciumComponent.builder()
            .component(Component.text("test"))
            .component(Component.text("nextTest"))
            .build();
  }
}
