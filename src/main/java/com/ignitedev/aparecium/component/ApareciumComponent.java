package com.ignitedev.aparecium.component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import net.kyori.adventure.text.Component;

@Data
@Builder
public class ApareciumComponent {

  public interface ListStrings extends Supplier<List<String>> {}
  public interface ListComponents extends Supplier<List<Component>> {}
  @Singular(value = "string")
  private List<String> strings = new ArrayList<>();

  @Singular(value = "component")
  private List<Component> components = new ArrayList<>();

  public ApareciumComponent(ListComponents components) {
    this.components = components.get();
  }

  public ApareciumComponent(ListStrings strings) {
    this.strings = strings.get();
  }

  public ApareciumComponent(String string){
    this.strings = List.of(string);
  }

  public ApareciumComponent(Component component){
    this.components = List.of(component);
  }
}



