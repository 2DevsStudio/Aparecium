package com.ignitedev.aparecium.config;

import com.ignitedev.aparecium.gui.Layout;
import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.interfaces.Configuration;
import java.util.Map;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("FieldMayBeFinal") // simplejsonconfig is not supporting final fields yet
@Getter
@Configuration("gui-base.json")
public class GuiBase extends Config {

  private Map<String, Layout> savedLayouts = exampleLayouts();

  /**
   * @implNote please note that this method save and reload config, if you have any pending changes
   *     in your config file then it might be overridden
   * @param layout layout to save
   */
  public void saveLayout(Layout layout) {
    // todo

    save();
    reload();
  }

  @NotNull
  public Layout getById(String layoutId) {
    // todo
    return null;
  }

  private Map<String, Layout> exampleLayouts() {
    // todo
//    return Map.of("testLayout", new Layout());
    return null;
  }
}
