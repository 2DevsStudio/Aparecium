package com.ignitedev.aparecium.config;

import com.ignitedev.aparecium.command.custom.CustomCommand;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.interfaces.Configuration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("FieldMayBeFinal") // simplejsonconfig is not supporting final fields yet
@Getter
@Configuration("command-base.json")
public class CustomCommandsBase extends Config {

  private ApareciumComponent noPermissionMessage = ApareciumComponent.of("No Permission.");

  private Map<String, CustomCommand> savedCommands = exampleCommands();

  /**
   * @param commandName name command you are looking for
   * @return command with given name or null if not found
   */
  @Nullable
  public CustomCommand getById(String commandName) {
    return savedCommands.get(commandName);
  }

  /**
   * @param alias one of aliases of command you are looking for
   * @return command with given alias or null if not found
   */
  @Nullable
  public CustomCommand getByAlias(String alias) {
    for (CustomCommand value : savedCommands.values()) {
      List<String> commandAliases = value.getCommandAliases();

      if (commandAliases.contains(alias)) {
        return value;
      }
    }
    return null;
  }

  private Map<String, CustomCommand> exampleCommands() {
    return Map.of(
        "aparecium",
        new CustomCommand(
            "aparecium",
            new ArrayList<>(),
            null,
            List.of("Hey, this is test CustomCommand by Aparecium")));
  }
}
