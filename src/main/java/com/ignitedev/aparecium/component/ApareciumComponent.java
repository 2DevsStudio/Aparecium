/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.component;

import com.ignitedev.aparecium.engine.ApareciumMain;
import com.ignitedev.aparecium.interfaces.PaperOnly;
import com.ignitedev.aparecium.util.PaperUtility;
import com.ignitedev.aparecium.util.text.TextUtility;
import java.util.List;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.Singular;
import lombok.SneakyThrows;
import lombok.ToString;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@ToString
public class ApareciumComponent {

  @NotNull
  @Singular(value = "string")
  private List<String> strings;

  @NotNull
  @Singular(value = "component")
  @PaperOnly
  private List<Component> components;

  @SneakyThrows
  @PaperOnly
  public ApareciumComponent(ListComponents components) {
    PaperUtility.checkPaper();
    this.components = components.get();
    this.strings = TextUtility.serializeComponent(components.get());
  }

  public ApareciumComponent(ListStrings strings) {
    this.strings = strings.get();
    if (ApareciumMain.isUsingPaper()) {
      this.components = TextUtility.colorizeToComponent(strings.get());
    }
  }

  public ApareciumComponent(String string) {
    this.strings = List.of(string);
    if (ApareciumMain.isUsingPaper()) {
      this.components = List.of(TextUtility.colorizeToComponent(string));
    }
  }

  @SneakyThrows
  @PaperOnly
  public ApareciumComponent(Component component) {
    PaperUtility.checkPaper();
    this.components = List.of(component);
    this.strings = TextUtility.serializeComponent(List.of(component));
  }

  @NotNull
  public static ApareciumComponent of(@NotNull String string) {
    return new ApareciumComponent(string);
  }

  @SneakyThrows
  @NotNull
  @PaperOnly
  public static ApareciumComponent of(@NotNull Component component) {
    PaperUtility.checkPaper();
    return new ApareciumComponent(component);
  }

  public ApareciumComponent replace(String from, String to) {
    this.strings.set(0, this.strings.get(0).replace(from, to));
    if (ApareciumMain.isUsingPaper()) {
      this.components.set(
          0,
          this.components.get(0).replaceText((value) -> value.matchLiteral(from).replacement(to)));
    }
    return this;
  }

  @Nullable
  public List<String> getAsStrings() {
    fetch();

    if (!strings.isEmpty()) {
      return TextUtility.colorize(strings);
    } else if (!components.isEmpty()) {
      if (ApareciumMain.isUsingPaper()) {
        return TextUtility.serializeComponent(components);
      }
    }
    return null;
  }

  @Nullable
  public String getAsString() {
    fetch();
    List<String> asStrings = getAsStrings();

    if (asStrings != null && !asStrings.isEmpty()) {
      return asStrings.get(0);
    }
    return null;
  }

  @SneakyThrows
  @Nullable
  @PaperOnly
  public Component getAsComponent() {
    PaperUtility.checkPaper();
    fetch();
    List<Component> asComponents = getAsComponents();

    if (asComponents != null && !asComponents.isEmpty()) {
      return asComponents.get(0);
    }
    return null;
  }

  @Nullable
  @PaperOnly
  @SneakyThrows
  public List<Component> getAsComponents() {
    PaperUtility.checkPaper();
    fetch();

    if (!strings.isEmpty()) {
      return TextUtility.colorizeToComponent(strings);
    } else if (!components.isEmpty()) {
      return components;
    }
    return null;
  }

  @PaperOnly
  public void fetch() {
    // paper code
    if(ApareciumMain.isUsingPaper()){
      if(this.components.isEmpty() && !this.strings.isEmpty()){
        this.components = TextUtility.colorizeToComponent(this.strings);
      }
      if(this.strings.isEmpty() && !this.components.isEmpty()){
        this.strings = TextUtility.serializeComponent(this.components);
      }
    }
  }

  public interface ListStrings extends Supplier<List<String>> {}

  public interface ListComponents extends Supplier<List<Component>> {}
}
