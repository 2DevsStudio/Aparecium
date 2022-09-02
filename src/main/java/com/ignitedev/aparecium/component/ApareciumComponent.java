package com.ignitedev.aparecium.component;

import com.ignitedev.aparecium.util.text.TextUtility;
import java.util.List;
import java.util.function.Supplier;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@Builder
public class ApareciumComponent {

  @NotNull
  @Singular(value = "string")
  private List<String> strings;

  @NotNull
  @Singular(value = "component")
  private List<Component> components;

  public ApareciumComponent(ListComponents components) {
    this.components = components.get();
    this.strings = TextUtility.serializeComponent(components.get());
  }

  public ApareciumComponent(ListStrings strings) {
    this.strings = strings.get();
    this.components = TextUtility.colorizeToComponent(strings.get());
  }

  public ApareciumComponent(String string) {
    this.strings = List.of(string);
    this.components = List.of(TextUtility.colorizeToComponent(string));
  }

  public ApareciumComponent(Component component) {
    this.components = List.of(component);
    this.strings = TextUtility.serializeComponent(List.of(component));
  }

  @Nullable
  public List<String> getAsStrings() {
    if (!strings.isEmpty()) {
      return TextUtility.colorize(strings);
    } else if (!components.isEmpty()) {
      return TextUtility.serializeComponent(components);
    }
    return null;
  }

  @Nullable
  public List<Component> getAsComponents() {
    if (!strings.isEmpty()) {
      return TextUtility.colorizeToComponent(strings);
    } else if (!components.isEmpty()) {
      return components;
    }
    return null;
  }

  public interface ListStrings extends Supplier<List<String>> {}

  public interface ListComponents extends Supplier<List<Component>> {}
}
