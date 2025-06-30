# Aparecium - Minecraft Plugin Development Library

Aparecium is a comprehensive, high-performance library designed to empower developers and server configurators in creating advanced Minecraft plugins.  
The library emphasizes ease of use, robust configuration, and modularity, supporting both **Paper** and **Spigot** platforms (Java 21+ required).


## Dependency

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.2DevsStudio</groupId>
    <artifactId>Aparecium</artifactId>
    <version>-SNAPSHOT</version>
</dependency>
```

Don't forget to add `depend` in your plugin.yml in order to make sure that Aparecium is loaded
before your plugin.

```yaml
depend: [ Aparecium ]
```

## Key Features

- **Builder and Factory Patterns:**  
  Modern design patterns are utilized to streamline object creation and management, ensuring maintainable and scalable codebases.

- **Extensive Utility Suite:**  
  Over 30 utility classes are provided, covering a wide range of common plugin development needs, including items, locations, math, reflection, serialization, randomization, messaging, and more.

- **Advanced Sorting System:**  
  The `SortingHat` system enables thread-safe, dynamic sorting and filtering of collections, supporting custom predicates and comparators for flexible data management.

- **Sophisticated Item System:**  
  Multiple item types are supported, such as DropItem, LayoutItem, and PatternItem, with full NBT tag compatibility and extensibility for custom item logic.

- **Modular Layout and Layer System:**  
  The layout system allows for the creation of complex, reusable GUI layouts and layers, supporting animated and pattern-based inventories for highly configurable user interfaces.

- **Hologram Management:**  
  An object-oriented API is provided for creating and managing holograms, with integration for HolographicDisplays and plans for a native solution.

- **Centralized Factories System:**  
  A global manager oversees the registration and retrieval of factories for items, holograms, layouts, and more, supporting both default and custom implementations.

- **Flexible Configuration:**  
  The library leverages [SimpleJSONConfig](https://github.com/2DevsStudio/SimpleJSONConfig) for seamless JSON/YAML storage and retrieval of items, layers, and layouts.

- **PlaceholderAPI Integration:**  
  Deep integration with PlaceholderAPI enables dynamic text and configuration, enhancing plugin flexibility and user experience.

- **Cross-Platform Support:**  
  Full compatibility with both Paper and Spigot servers, with additional enhancements available for Paper.

## Compatibility

- **Minecraft Versions:**  
  Compatible with Minecraft versions from 1.12.2 to 1.21, ensuring broad support for various server setups.
- **Java Version:**  
  Requires Java 21 or newer, leveraging modern language features for improved performance and maintainability.
- **Server Platforms:**  
  Designed for use with both Paper and Spigot servers, with a focus on stability and performance on Paper.

## Requirements

- Java 21 or newer
- Paper or Spigot server
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) (required)
- [HolographicDisplays](https://ci.codemc.io/job/filoghost/job/HolographicDisplays/) (for hologram features)

## Additional Information

- Aparecium is under active development, with frequent updates and new features.
- Comprehensive documentation and advanced usage examples are available in the project readme(down below) and the `src/main/java/com/ignitedev/aparecium/example` directory.
- Contributions and feedback from the community are highly encouraged.

---
# Planned

- Advanced Command System
- Event system
- Sound system
- Chat System