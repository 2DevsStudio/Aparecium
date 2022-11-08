package com.ignitedev.aparecium.component;

import com.ignitedev.aparecium.util.text.TextUtility;
import java.util.List;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.Singular;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
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

  @NotNull
  public static ApareciumComponent of(@NotNull String string) {
    return new ApareciumComponent(string);
  }

  @NotNull
  public static ApareciumComponent of(@NotNull Component component) {
    return new ApareciumComponent(component);
  }

  public ApareciumComponent replace(String from, String to) {
    this.strings.set(0, this.strings.get(0).replace(from, to));
    this.components.set(
        0, this.components.get(0).replaceText((value) -> value.matchLiteral(from).replacement(to)));
    return this;
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
  public String getAsString() {
    List<String> asStrings = getAsStrings();

    if (asStrings != null && !asStrings.isEmpty()) {
      return asStrings.get(0);
    }
    return null;
  }

  @Nullable
  public Component getAsComponent() {
    List<Component> asComponents = getAsComponents();

    if (asComponents != null && !asComponents.isEmpty()) {
      return asComponents.get(0);
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
