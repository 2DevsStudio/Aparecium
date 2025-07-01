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
    <version>1.0.4</version>
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


---

# WIKI SECTION

---

## Aparecium - Plugin Base Class

`Aparecium` is the core abstract class that you should extend instead of `JavaPlugin` when developing plugins with the Aparecium framework.  
It provides additional lifecycle hooks, utility methods, and integration points to simplify plugin development and enhance functionality.

### Key Features

- **Extended Lifecycle:**  
  Offers extra loading stages (`onPreLoad`, `onInnerLoad`, `onPostLoad`, `onEnabling`, `onDisabling`) for better control over plugin initialization and shutdown.
- **Performance Measurement:**  
  Built-in stopwatch utility to measure execution time of lifecycle methods.
- **Factory Management:**  
  Access to a global `FactoriesManager` for managing plugin factories.
- **Paper Detection:**  
  Static check to determine if the server is running Paper.
- **TPS Monitoring:**  
  Easy access to server TPS (ticks per second) statistics and console reporting.
- **Integrated Logging:**  
  Uses the `HedwigLogger` system for advanced logging.

### Example Usage

The Full example is available in the [Aparecium Main Example](src/main/java/com/ignitedev/aparecium/engine/ApareciumMain.java).

```java
import com.ignitedev.aparecium.Aparecium;

public class MyPlugin extends Aparecium {

    @Override
    public void onPreLoad() {
        // Pre-load logic here
    }

    @Override
    public void onInnerLoad() {
        // Inner load logic here
    }

    @Override
    public void onPostLoad() {
        // Post-load logic here
    }

    @Override
    public void onEnabling() {
        // Plugin enabling logic here
    }

    @Override
    public void onDisabling() {
        // Plugin disabling logic here
    }
}
```

**Note:**
- You must extend `Aparecium` instead of `JavaPlugin` to use all features.
- All standard `JavaPlugin` methods are still available.
---

## MongoDB Integration

Aparecium provides a simple, extensible integration with MongoDB for asynchronous and synchronous database operations.  
The system is based on two main classes: `MongoDBConnection` (connection and thread management) and `SimpleMongo` (database operations).

### Key Classes

- **MongoDBConnection:**  
  Manages the MongoDB client and a dedicated executor service for async operations.

- **SimpleMongo:**  
  Provides utility methods for retrieving, saving, and managing documents in MongoDB collections, supporting both sync and async workflows.

### Example Usage

```java
import com.ignitedev.aparecium.database.MongoDBConnection;
import com.ignitedev.aparecium.database.SimpleMongo;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

// Create a connection to MongoDB
MongoDBConnection connection = new MongoDBConnection("mongodb://localhost:27017");

// Get a database instance
MongoDatabase database = connection.getDatabase("myDatabase");

// Create a SimpleMongo instance
SimpleMongo simpleMongo = new SimpleMongo(connection, database);

// Get a collection
MongoCollection<Document> collection = simpleMongo.misc().getCollection("users");

// Create a document identifier (e.g., by _id)
Document identifier = new Document("_id", "user123");

// Synchronously retrieve documents
for (Document doc : simpleMongo.get().getObjectSync(collection, identifier)) {
    // Process document
}

// Asynchronously retrieve documents
simpleMongo.get().getObjectASync(collection, identifier).thenAccept(docs -> {
    for (Document doc : docs) {
        // Process document
    }
});

// Save or update a document asynchronously
Document newUser = new Document("_id", "user123").append("name", "Steve");
simpleMongo.save().saveObjectAsync(collection, identifier, newUser);

// Save or update a document synchronously
simpleMongo.save().saveObjectSync(collection, identifier, newUser);
```

**Note:**
- All async operations use a dedicated single-threaded executor for thread safety.
- The API is designed for ease of use and can be extended for advanced MongoDB features.
---

# Cuboid - 3D Area Representation

Represents a 3D rectangular area (cuboid) in the Minecraft world, defined by two corner `Location`s: minimum and maximum.  
Provides utilities for checking if a location is inside, iterating blocks, clearing space, finding safe spawn points, and more.

#### Key Features

- **Area Representation:**  
  Stores minimum and maximum corners as `Location` objects.
- **Containment Check:**  
  Check if a given `Location` is inside the cuboid.
- **Block Iteration:**  
  Retrieve all blocks within the cuboid.
- **Block Counting:**  
  Count all blocks, with optional inclusion of air blocks.
- **Clear Space:**  
  Set all blocks in the cuboid to air.
- **Particle Effects:**  
  Spawn particles at every block in the cuboid.
- **Center Calculation:**  
  Get the geometric center of the cuboid.
- **Ground Items:**  
  List all dropped items within the cuboid.
- **Safe Spawn Location:**  
  Find a safe spot for spawning entities inside the cuboid.

#### Example Usage

```java
import com.ignitedev.aparecium.base.Cuboid;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;

// Create a cuboid from two corners
Cuboid cuboid = new Cuboid(minLocation, maxLocation);

// Check if a location is inside the cuboid
boolean inside = cuboid.isInside(player.getLocation());

// Get all blocks in the cuboid
List<Block> blocks = cuboid.getBlocks();

// Count all non-air blocks
int blockCount = cuboid.getBlockCount(false);

// Clear all blocks (set to air)
cuboid.clearSpace();

// Send particles to all blocks in the cuboid
cuboid.sendParticles(Particle.FLAME);

// Get the center location of the cuboid
Location center = cuboid.getCenter();

// Find a safe place to spawn an entity
Location safeSpawn = cuboid.findSafePlace();
```

**Note:**
- The minimum and maximum locations are automatically adjusted so that minimum is always less than or equal to maximum on all axes.
- Useful for region selection, area effects, structure placement, and more.

---

# ApareciumComponent

`ApareciumComponent` is a flexible wrapper for text used in Aparecium plugins, supporting both plain strings and Adventure `Component`s.  
It allows you to work with messages, lore, and GUI text in a way that is compatible with both Bukkit and Paper servers, enabling advanced formatting, MiniMessage support, and easy placeholder replacement.

### Key Features

- **Dual Representation:**  
  Stores text as both a list of strings and, if available, a list of Adventure `Component`s.
- **Automatic Conversion:**  
  Converts between strings and components as needed, depending on server capabilities.
- **MiniMessage & Color Support:**  
  Supports color codes and MiniMessage formatting for rich text.
- **Placeholder Replacement:**  
  Easily replace placeholders in text for dynamic messages.
- **Flexible Construction:**  
  Create from a string, list of strings, `Component`, or list of components.
- **Safe Accessors:**  
  Retrieve as string, list of strings, `Component`, or list of components.

### Example Usage

Full component Example can be found here [Aparecium Component Example](src/main/java/com/ignitedev/aparecium/example/ApareciumComponentExample.java)

```java
import com.ignitedev.aparecium.component.ApareciumComponent;
import net.kyori.adventure.text.Component;

// Create from a string
ApareciumComponent text = ApareciumComponent.of("&aHello &bWorld!");

// Create from an Adventure Component (Paper only)
ApareciumComponent comp = ApareciumComponent.of(Component.text("Welcome!"));

// Replace a placeholder
ApareciumComponent replaced = text.replace("{player}", "Steve");

// Get as a plain string
String plain = text.getAsString();

// Get as a list of strings
List<String> lines = text.getAsStrings();

// Get as an Adventure Component (Paper only)
Component adventure = text.getAsComponent();
```

**Note:**
- On Paper servers, Adventure `Component` features are fully supported.
- On Bukkit/Spigot, only string-based features are available.
- Useful for chat messages, item lore, GUI titles, and any plugin text handling.

---

# SortingHat - Aparecium's Sorting System

`SortingHat` is a thread-safe utility class for managing, sorting, and filtering collections of elements.  
It allows you to dynamically add, remove, sort, and filter elements using custom predicates, without modifying the original collection.

### Key Features

- **Thread-safe collection:**  
  Stores elements in a `ConcurrentSkipListSet` for safe use in multi-threaded environments.
- **Natural ordering:**  
  Elements are sorted by their natural order (`Comparable`).
- **Custom sorting:**  
  Obtain a sorted copy of the collection using any comparator.
- **Dynamic filtering:**  
  Add and remove named filters (predicates) and get filtered copies of the collection.

### Example Usage

Full Sorting Example can be found in the [Aparecium Sorting Example](src/main/java/com/ignitedev/aparecium/example/SortingExample.java).

```java
import com.ignitedev.aparecium.sorting.SortingHat;
import java.util.Comparator;
import java.util.Set;

// Create a SortingHat for integers
SortingHat<Integer> hat = new SortingHat<>();
hat.add(5, 2, 8, 1, 3);

// Get naturally sorted collection
Set<Integer> sorted = hat.getCollection(); // [1, 2, 3, 5, 8]

// Sort by custom comparator (reverse order)
Set<Integer> reversed = hat.sort(Comparator.reverseOrder()); // [8, 5, 3, 2, 1]

// Add a filter (only even numbers)
hat.addFilter("even", i -> i % 2 == 0);
Set<Integer> evens = hat.filter(); // [2, 8]

// Remove a filter
hat.removeFilter("even");

// Clear collection
hat.clearCollection();
```

**Note:**
- Filters are not applied automatically — use `filter()` to get a filtered copy.
- The class requires type `T` to implement `Comparable<T>`.
- Useful for managing collections, sorting results, dynamic filtering, and set operations in plugins.


---

# Hologram System - Aparecium wrapper for HolographicDisplays

The Aparecium Hologram System provides a flexible, object-oriented API for creating, managing, and displaying holograms in Minecraft using the HolographicDisplays plugin.  
It allows you to define holograms with text and item lines, manage their lifecycle, and easily update or destroy them as needed.

### Key Features

- **Abstract and Simple Hologram Classes:**  
  Use `AbstractBaseHologram` as a base for custom implementations, or `SimpleBaseHologram` for quick creation.
- **Line Flexibility:**  
  Each hologram line can be either text (`ApareciumComponent`) or an item (`Material`).
- **Placeholder Support:**  
  Built-in placeholder system for dynamic text replacement in hologram lines.
- **Factory Pattern:**  
  Hologram creation is handled by pluggable `HologramFactory` implementations, allowing for easy extension and integration.
- **Lifecycle Management:**  
  Spawn, update, and destroy holograms programmatically. All active holograms are tracked by `HologramManager`.
- **Integration with HolographicDisplays:**  
  Uses the HolographicDisplays API for rendering holograms in-game.

### Example Usage

Full Hologram Example can be found in the [Aparecium Hologram Example](src/main/java/com/ignitedev/aparecium/example/HologramExample.java).

```java
import com.ignitedev.aparecium.hologram.SimpleBaseHologram;
import com.ignitedev.aparecium.hologram.data.HologramEntry;
import com.ignitedev.aparecium.component.ApareciumComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import java.util.List;

// Create hologram lines (text and item)
List<HologramEntry> lines = List.of(
    new HologramEntry(ApareciumComponent.of("&aWelcome!"), null),
    new HologramEntry(null, Material.DIAMOND)
);

// Create and spawn a simple hologram
SimpleBaseHologram hologram = SimpleBaseHologram.builder()
    .id("spawn-holo")
    .location(new Location(world, 0, 100, 0))
    .lines(lines)
    .build();

hologram.spawn(apareciumInstance);

// Update a line
hologram.setLine(0, new HologramEntry(ApareciumComponent.of("&bEnjoy your stay!"), null));
hologram.update(apareciumInstance);

// Destroy the hologram
hologram.destroy();
```

### Managing Holograms

- **Destroy all holograms:**  
  `HologramManager.destroyAll();`
- **Destroy by ID:**  
  `HologramManager.destroy("spawn-holo");`
- **Check if spawned:**  
  `hologram.isSpawned();`

**Note:**
- Holograms are automatically registered and unregistered in the global manager.
- You can extend `AbstractBaseHologram` for advanced use cases.
- Factories can be customized for different hologram backends or effects.
- Requires HolographicDisplays plugin to be installed.

---



# Factories System

The Aparecium Factories System provides a flexible and extensible way to manage the creation of various objects (such as items, holograms, and GUI elements) using the factory pattern.  
It centralizes factory management, allowing you to register, retrieve, and use different factory implementations for custom behaviors and integrations.

### Key Features

- **Centralized Management:**  
  All factories are managed by the global `FactoriesManager`, which holds references to all available factory types.
- **Extensible Architecture:**  
  Easily register new factories for items, holograms, layouts, drops, patterns, and more.
- **FactoryHolder Cache:**  
  Factories are stored in a non-persistent cache (`FactoryHolder`), so you must register them each time the plugin starts.
- **Default and Custom Factories:**  
  Use default factories out of the box, or register your own for advanced use cases.
- **Type Safety:**  
  All factories implement the marker `Factory` interface for type safety and consistency.

### Example Usage

Full Factories Example can be found in the [Aparecium Factories Example](src/main/java/com/ignitedev/aparecium/hologram/factory).

```java
import com.ignitedev.aparecium.factory.FactoriesManager;
import com.ignitedev.aparecium.hologram.factory.HologramFactory;
import com.ignitedev.aparecium.item.factory.MagicItemFactory;

FactoriesManager factoriesManager = Aparecium.getFactoriesManager(); // Access the global FactoriesManager

// Register a custom hologram factory
factoriesManager.getHologramFactories().registerFactory("CUSTOM", new MyCustomHologramFactory());

// Retrieve a factory by ID
HologramFactory defaultHologramFactory = factoriesManager.getHologramFactories().getDefaultFactory();
MagicItemFactory layoutItemFactory = factoriesManager.getMagicItemFactories().getFactory("LAYOUT_DEFAULT");
```

**Note:**
- Factories must be registered every time the plugin starts, as the cache is not persistent.
- The system is designed for modularity and easy extension, making it simple to swap or add new behaviors.
- Useful for advanced plugin development, custom item systems, GUI layouts, and integration with other plugins.
---

# Hedwig Logger - Aparecium's Logging System

`HedwigLogger` is the dedicated logging utility for Aparecium and your plugins.  
It provides a unified, extensible way to log messages, warnings, and errors, either globally for Aparecium or per-plugin.  
By using `HedwigLogger`, you ensure consistent log formatting and easy integration with Aparecium's internal systems.

### Key Features

- **Global and Plugin Loggers:**  
  Obtain a global logger for Aparecium or create a dedicated logger for your plugin instance.
- **Caching:**  
  Loggers are cached per plugin instance for efficient reuse.
- **Standard Logging Levels:**  
  Supports info, warning, and error levels, mapped to standard Java logging.
- **Integration:**  
  Works seamlessly with Aparecium's lifecycle and utility systems.

### Example Usage

```java
import com.ignitedev.aparecium.logging.HedwigLogger;
import com.ignitedev.aparecium.Aparecium;

// Get or create a logger for your plugin
HedwigLogger logger = HedwigLogger.getOrCreate(apareciumInstance);

// Log an info message
logger.info("Plugin started successfully!");

// Log a warning
logger.warning("This is a warning!");

// Log an error
logger.error("An error occurred!");

// Access the global Aparecium logger
HedwigLogger mainLogger = HedwigLogger.getMainLogger();
mainLogger.info("Aparecium is running.");
```

**Note:**
- Use `HedwigLogger.getOrCreate(apareciumInstance)` to get a logger for your plugin.
- Use `HedwigLogger.getMainLogger()` for global Aparecium logging.
- Ensures all logs are consistent and easy to manage across your project.

---

# Custom-Configurable reply commands

#### Accessing CustomCommandsBase.class allows you to create custom commands that can be configured in the Aparecium command-base.json configuration, This allows you to create simple reply commands that can be used on your server without having to write any code.
#### These simple commands support permissions and aliases.

---

# Data Store Service

#### Aparecium provides a data store for Items, Layers and Layouts, you can create similar data stores, but these are here to avoid code duplication and complexity in configuration. Think of it as a main storage.
#### Accessing the data can be done using @Autowired annotation for specific data store, or just by simple #getConfig, here is an example of how to access the data store:

```java

import com.ignitedev.aparecium.config.ItemBase;
import com.twodevsstudio.simplejsonconfig.api.Config;

@Autowired
private static ItemBase itemBase;

// or
private static Layerbase layerbase = Config.getConfig(Layerbase.class);
// or
private static LayoutBase layoutBase = Config.getConfig(LayoutBase.class);
```
#### To access data from the data store, you can use the following methods:

```java

import com.ignitedev.aparecium.config.LayerBase;
import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;

@Autowired
private static LayerBase layerBase;

public void test() {
    layerBase.getById("testLayerId"); // Get Layer by ID
    layerBase.getLayers(); // Get all Layers
}
```
### Data Store Classes:
- [ItemBase](src/main/java/com/ignitedev/aparecium/config/ItemBase.java): Data store for items
- [LayoutBase](src/main/java/com/ignitedev/aparecium/config/LayoutBase.java): Data store for inventory layouts
- [LayerBase](src/main/java/com/ignitedev/aparecium/config/LayerBase.java): Data store for inventory layers

---
# Gui System

#### System that allows you to create complex GUIs with ease, saving you time with a serializable system, thanks to Clickable items you'll be able to create advanced configurable clickable inventories.
#### The Whole idea is that we have a base class "Layout" that is used to create a GUI, and we have "LayoutLayer" which is a repeatable part of guis to avoid code duplication and complexity in configuration.
#### Creating simple GUI:

A Full example of GUI creation can be found in the [Aparecium GUI Example](src/main/java/com/ignitedev/aparecium/example/GuiExample.java)

```java

Layout layout = Layout.builder()
                .id("exampleLayout") // Unique ID of the layout
                .layoutSize(18) // Size of the layout, can be 9, 18, 27, 36, 45, 54
                .layoutTitle(ApareciumComponent.of("2Devs on FIRE")) // Title of the layout, can be a string or ApareciumComponent
                .inventoryType(InventoryType.CHEST) // Type of the inventory, can be CHEST, PLAYER, ANVIL, etc.
                .layoutInteractions(
                        LayoutInteractions.builder()
                                .inventoryClick(false) // Disable inventory click interactions
                                .inventoryDrag(false) // Disable inventory drag interactions
                                .inventoryMoveItem(false) // Disable inventory move item interactions
                                .build())
                .contents(
                        Map.of(
                                0, // Slot number in the inventory
                                layoutItemFactory.createItem("itemId", Material.DIRT).toWrapper(),
                                1,
                                layoutItemFactory.createItem("itemId", Material.DIRT).toWrapper()))
                .layers(Map.of(0, "LayerID5", 1, "LayerID3", 2, "LayerID8")) // Layers of the layout, can be used to create complex GUIs with multiple layers, the keys are the layer weight (LOWER = HIGHER PRIORITY)
                .layoutBackgroundLayer(backgroundLayer)
                .build();

```

Full example of GUI creation can be found in the [Aparecium GUI Example](src/main/java/com/ignitedev/aparecium/example/GuiExample.java)

---
# Item system

## To Quickly convert ItemStack to MagicItem you can use [Magic Item Converter](src/main/java/com/ignitedev/aparecium/item/util/MagicItemConverter.java)

A Full Item example can be found in the [Aparecium Item Example](src/main/java/com/ignitedev/aparecium/example/ItemExample.java)

#### Default Item system implementation of [MagicItem](src/main/java/com/ignitedev/aparecium/item/MagicItem.java) is [Item](src/main/java/com/ignitedev/aparecium/item/basic/Item.java), other useful implementations are:
* [ClickableItem](src/main/java/com/ignitedev/aparecium/item/basic/ClickableItem.java) - Item with interactions, can be used in the layout
* [LayoutItem](src/main/java/com/ignitedev/aparecium/item/basic/LayoutItem.java) - Item that can be used in the layout
* [DropItem](src/main/java/com/ignitedev/aparecium/item/basic/DropItem.java) - Item that has a drop chance
* [PatternItem](src/main/java/com/ignitedev/aparecium/item/basic/PatternItem.java) - Item implementation useful in rolling systems, it has a pattern and a chance to drop

---
# Utility Classes

---

## WorldGuardUtility

Utility class for interacting with WorldGuard regions in Minecraft.  
It provides methods to query, check, and create regions, as well as to determine if a player is inside specific regions.

### Key Features

- **Get region at a location:** Retrieve the first WorldGuard region at a given `Location`.
- **Get player regions:** List all regions a player is currently inside.
- **Check player presence:** Check if a player is in any or all of a set of regions.
- **Create regions:** Create a new cuboid region with optional flags.

### Example Usage

```java
import com.ignitedev.aparecium.util.WorldGuardUtility;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

// Get the region at a specific location
ProtectedRegion region = WorldGuardUtility.getRegionByLocation(location);

// Get all regions a player is currently in
Set<ProtectedRegion> playerRegions = WorldGuardUtility.getRegions(player.getUniqueId());

// Get names of all regions a player is in
Set<String> regionNames = WorldGuardUtility.getRegionsNames(player.getUniqueId());

// Check if a player is in all specified regions
boolean inAll = WorldGuardUtility.isPlayerInAllRegions(player.getUniqueId(), "spawn", "market");

// Check if a player is in any of the specified regions
boolean inAny = WorldGuardUtility.isPlayerInAnyRegion(player.getUniqueId(), "arena", "lobby");

// Create a new region from a Cuboid
WorldGuardUtility.createRegion("myRegion", cuboid, null);
```

**Note:**
- `Cuboid` is a custom class representing a 3D area (with min/max corners).
- You can pass WorldGuard flags as a map to `createRegion` to set region properties.
---

## SerializationUtility

Utility class for serializing and deserializing common data types to and from GSON `JsonObject`.  
Provides helper methods for handling lists, integers, doubles, and booleans in JSON structures.

### Key Features

- **Deserialize String List:** Extract a list of strings from a JSON object.
- **Deserialize Primitives:** Read integer, double, or boolean values from a JSON object.
- **Serialize String List:** Add a list of strings to a JSON object using a serialization context.

### Example Usage

```java
import com.ignitedev.aparecium.util.SerializationUtility;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;

// Deserialize a list of strings from JSON
List<String> names = SerializationUtility.deserializeStringList(jsonObject, "names");

// Deserialize an integer from JSON
int age = SerializationUtility.deserializeInt(jsonObject, "age");

// Deserialize a double from JSON
double price = SerializationUtility.deserializeDouble(jsonObject, "price");

// Deserialize a boolean from JSON
boolean enabled = SerializationUtility.deserializeBoolean(jsonObject, "enabled");

// Serialize a list of strings into JSON
SerializationUtility.serializeStringList(jsonObject, context, List.of("A", "B", "C"), "letters");
```
---

## SchematicUtility

Utility class for handling schematic operations in Minecraft using WorldEdit and FastAsyncWorldEdit (FAWE).  
Provides methods for asynchronously pasting schematics, checking if a location is empty before pasting, and caching loaded schematics for performance.

### Key Features

- **Asynchronous Schematic Pasting:**  
  Paste a schematic file at a given location without blocking the main server thread.
- **Clipboard Caching:**  
  Loaded schematics are cached in memory to avoid repeated disk reads.
- **Location Validation:**  
  Check if a location is empty or suitable for pasting a schematic.
- **Customizable Paste Options:**  
  Control whether to copy biomes, entities, and whether to ignore air blocks.

### Example Usage

```java
import com.ignitedev.aparecium.util.SchematicUtility;
import org.bukkit.Location;

// Asynchronously paste a schematic at a location
SchematicUtility.asyncPaste(
    "house.schem",           // Schematic file name (in the plugin's schematics folder)
    location,                // Bukkit Location to paste at
    () -> System.out.println("Paste complete!"), // Callback after paste
    true,                    // Copy biomes
    true,                    // Copy entities
    true                     // Ignore air blocks
);

// Synchronously paste a schematic and get the paste location
BlockVector3 pastedAt = SchematicUtility.paste(
    "tree.schem",
    location,
    true,    // Copy biomes
    false,   // Copy entities
    false    // Do not ignore air blocks
);

// Check if a location is empty before pasting
boolean isEmpty = SchematicUtility.isLocationEmpty(
    faweWorld,               // WorldEdit World instance
    blockVector,             // BlockVector3 location
    clipboard                // Loaded Clipboard
);
```

**Note:**
- Schematic files should be placed in the `schematics` folder inside your plugin's data directory.
- The class uses WorldEdit and FAWE APIs; make sure these plugins are installed and configured.
- All methods are thread-safe.
---

## ReflectionUtility

Utility class for advanced reflection operations in Java.  
Provides methods to dynamically access constructors, methods, and fields, invoke them, and handle type casting, with built-in caching for performance.

### Key Features

- **Constructor Access:**  
  Retrieve and cache constructors by parameter types.
- **Object Instantiation:**  
  Dynamically create objects with given arguments.
- **Method Access & Invocation:**  
  Find, cache, and invoke methods by name and parameters, including private ones.
- **Field Access & Manipulation:**  
  Get, set, and retrieve fields (including private and inherited fields).
- **Type-Safe Argument Checking:**  
  Validate and cast arguments to match method or constructor parameter types.
- **Primitive Type Casting:**  
  Utility for casting between primitive types and their wrappers.

### Example Usage

```java
import com.ignitedev.aparecium.util.ReflectionUtility;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

// Get a constructor and instantiate an object
Object myObj = ReflectionUtility.initializeObject(MyClass.class, "arg1", 42);

// Get and invoke a method
Object result = ReflectionUtility.invokeMethod(myObj, "myMethod", "param");

// Get and set a private field
Field field = ReflectionUtility.getField(MyClass.class, "privateField");
field.set(myObj, 123);

// Get a method with specific return type and parameters
Method typedMethod = ReflectionUtility.getTypedMethod(MyClass.class, "compute", int.class, String.class);
Object value = ReflectionUtility.executeMethod(typedMethod, myObj, "input");

// Set a field value by name
ReflectionUtility.setValue(myObj, "fieldName", "newValue");

// Get a private field value
Object fieldValue = ReflectionUtility.getPrivateField(myObj, "secret");
```

---

## RandomUtility

Utility class for generating random numbers and performing random-based calculations.  
Provides methods for generating random integers, simulating chance-based outcomes, and producing values with uniform or Gaussian distributions.

### Key Features

- **Chance Simulation:**  
  Calculate the number of successful outcomes based on a given probability and number of attempts.
- **Random Integer Generation:**  
  Generate random integers within a specified range, with optional scaling and exponent adjustment.
- **Gaussian Distribution:**  
  Generate random values using a normal (Gaussian) distribution for more natural randomness in large ranges.
- **Helper Methods:**  
  Internal helpers for average, standard deviation, and range enforcement.

### Example Usage

```java
import com.ignitedev.aparecium.util.RandomUtility;

// Simulate 100 attempts with a 25% chance of success each
int successes = RandomUtility.nextChance(25.0, 100);

// Generate a random integer from 0 (inclusive) to 10 (exclusive)
int random = RandomUtility.nextInt(10);

// Generate a random integer between 5 and 15, scaled by 2, with exponent 1.5
int ranged = RandomUtility.nextInt(5, 15, 2, 1.5);

// Generate a random integer between 10 and 50, scaled by 20 (uses Gaussian distribution)
int gaussian = RandomUtility.nextInt(10, 50, 20);
```

**Note:**
- All methods are thread-safe.
- Useful for loot tables, random events, and probability-based mechanics in plugins.

---

## ParticleUtility
### TO-DO

---

## PageObject

Utility class for managing paginated collections of objects.  
Allows you to split a collection into pages of a specified size and retrieve objects by page number.

### Key Features

- **Pagination:**  
  Divide any collection into pages with a fixed number of objects per page.
- **Page Retrieval:**  
  Get the list of objects for a specific page.
- **Page Count:**  
  Retrieve the total number of pages.

### Example Usage

```java
import com.ignitedev.aparecium.util.PageObject;
import java.util.List;
import java.util.Arrays;

// Create a PageObject with 10 items, 3 per page
List<String> items = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J");
PageObject<String> pageObject = new PageObject<>(items, 3);

// Load pages (must be called before accessing pages)
pageObject.loadPages();

// Get total number of pages
int totalPages = pageObject.getPagesAmount(); // 4

// Get objects from page 2
List<String> page2 = pageObject.getObjectsFromPage(2); // ["D", "E", "F"]
```

**Note:**
- Call `loadPages()` after creating the `PageObject` to initialize pagination.
- Page numbers start from 1.
- If a page does not exist, `getObjectsFromPage` returns `null`.

---

## MinecraftVersionUtility

Utility class for handling Minecraft server version information.  
Provides methods to compare the current server version with a given version and retrieve the server version as a string or integer.

### Key Features

- **Version Comparison:**  
  Check if the server version matches, is before, or is after a specified version.
- **Version Retrieval:**  
  Get the server version as a string (e.g., "1 20 R1") or as an integer (e.g., 20).

### Example Usage

```java
import com.ignitedev.aparecium.util.MinecraftVersionUtility;

// Check if the server is running version 1.20
boolean is20 = MinecraftVersionUtility.is(20);

// Check if the server version is after 1.19
boolean after19 = MinecraftVersionUtility.isAfter(19);

// Check if the server version is before 1.21
boolean before21 = MinecraftVersionUtility.isBefore(21);

// Check if the server version is 1.20 or newer
boolean isOrAfter20 = MinecraftVersionUtility.isOrAfter(20);

// Get the string representation of the server version
String versionString = MinecraftVersionUtility.getStringVersion(); // e.g., "1 20 R1"

// Get the integer representation of the server version
int versionInt = MinecraftVersionUtility.getIntVersion(); // e.g., 20
```

**Note:**
- Useful for version-dependent plugin features and compatibility checks.

---
## MessageUtility

Utility class for sending messages to players, audiences, and the console in various formats.  
Supports placeholders, Adventure components, MiniMessage formatting, and integration with PlaceholderAPI.

### Key Features

- **Send messages to players and audiences:**  
  Send single or multiple messages as strings, components, or custom ApareciumComponent objects.
- **Placeholder support:**  
  Replace placeholders in messages using custom placeholders and PlaceholderAPI.
- **Paper API integration:**  
  Use Adventure `Audience` and `Component` for advanced message formatting (Paper only).
- **Console messaging:**  
  Send messages directly to the server console.

### Example Usage

```java
import com.ignitedev.aparecium.util.MessageUtility;
import com.ignitedev.aparecium.util.text.Placeholder;
import com.ignitedev.aparecium.component.ApareciumComponent;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import java.util.List;

// Send a message with placeholders to a player
MessageUtility.send(player, "Hello, {player}!", Placeholder.of("player", player.getName()));

// Send an ApareciumComponent message to a player
MessageUtility.send(player, ApareciumComponent.of("Welcome!"));

// Send a message to an Adventure Audience (Paper)
MessageUtility.send(audience, "Welcome to the server!");

// Send a list of messages to a player
MessageUtility.send(player, List.of("Line 1", "Line 2"));

// Send a component message to an Audience (Paper)
MessageUtility.send(audience, ApareciumComponent.of("Component message"));

// Send a message to the console
MessageUtility.sendConsole(ApareciumComponent.of("Server started!"));

// Send a list of ApareciumComponents to the console
MessageUtility.sendConsole(List.of(ApareciumComponent.of("Line 1"), ApareciumComponent.of("Line 2")));
```

**Note:**
- Most methods support optional placeholders for dynamic text replacement.
- Paper-specific methods require the server to be running Paper or a compatible fork.
- Use `ApareciumComponent` for advanced formatting and MiniMessage support.
---

## MathUtility

Utility class providing various mathematical helper methods for number operations, randomization, trigonometry, and formatting.  
Includes optimized trigonometric calculations, random number generation, Roman numeral conversion, and more.

### Key Features

- **Roman Numeral Conversion:**  
  Convert integers to Roman numeral strings.
- **Number Normalization & Rounding:**  
  Normalize and round numbers to a specified number of decimal places.
- **Range Checks:**  
  Check if a number is between two values or clamp it within a range.
- **Randomization:**  
  Generate random integers or doubles within a range, or simulate chance-based outcomes.
- **Pluralization:**  
  Get the correct plural form of a word based on a count.
- **Trigonometric Functions:**  
  Fast sine and cosine calculations using precomputed tables.
- **Color Calculation:**  
  Get a color representing a percentage value.
- **Vector Math:**  
  Calculate squared length of 2D/3D vectors.

### Example Usage

```java
import com.ignitedev.aparecium.util.MathUtility;

// Convert 42 to Roman numerals
String roman = MathUtility.toRoman(42); // "XLII"

// Normalize a double to 2 decimal places
double normalized = MathUtility.normalizeDouble(3.14159); // 3.14

// Check if a number is between 5 and 10
boolean inRange = MathUtility.isBetween(7, 5, 10); // true

// Get a random integer between 1 and 100
int randomInt = MathUtility.getRandomNumber(1, 100);

// Get a random double between 0.0 and 1.0
double randomDouble = MathUtility.getRandomNumber(0.0, 1.0);

// Simulate a 25% chance
boolean success = MathUtility.getRandomPercent(25);

// Get the correct plural form
String plural = MathUtility.getCorrectPlural("apple", 3); // "apples"

// Fast sine and cosine
float sin = MathUtility.sin(Math.PI / 2); // ~1.0
float cos = MathUtility.cos(0);           // ~1.0

// Get a color for 75%
Color color = MathUtility.getColor(75);

// Round a double to 3 decimal places
double rounded = MathUtility.round(2.71828, 3); // 2.718
```

**Note:**
- Useful for plugin development, especially for random events, formatting, and performance-critical math operations.
---


## LocationUtility

Utility class for manipulating and calculating locations in the Minecraft world.  
Provides methods for distance calculations, conversions, random location selection, safety checks, and various location-based operations.

### Key Features

- **Distance Calculations:**  
  Calculate 2D or 3D distance between two locations, including safe checks for world equality.
- **Location Serialization:**  
  Convert `Location` objects to and from strings, with customizable delimiters and readable formats.
- **Radius and Safety Checks:**  
  Check if a location is within a radius, or if a location is safe for landing (on land or liquid).
- **Random Location Selection:**  
  Pick a random location within a given area, with optional safety checks.
- **Block and Direction Utilities:**  
  Get blocks or locations behind a player, convert yaw to `BlockFace`, get blocks around a location, and more.
- **Shape Generation:**  
  Generate lists of locations forming circles or spheres for effects or area calculations.
- **Teleportation with Chunk Loading:**  
  Teleport a player, ensuring the destination chunk is loaded asynchronously.

### Example Usage

```java
import com.ignitedev.aparecium.util.LocationUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

// Calculate 2D distance between two locations
double dist2D = LocationUtility.calculateDistance2D(loc1, loc2);

// Convert a Location to a string and back
String locString = LocationUtility.toString(loc, ";");
Location parsed = LocationUtility.fromString(locString, ";");

// Check if a location is within a 10-block radius of another
boolean within = LocationUtility.isWithinRadius(loc1, 10, loc2);

// Pick a random safe location in a world
Location randomLoc = LocationUtility.decideLocation(world, 0, 100, 0, 100, 64, 100, true);

// Get a list of locations forming a circle
List<Location> circle = LocationUtility.getCircle(center, 5.0, 20);

// Teleport a player to a location, loading the chunk if needed
LocationUtility.teleportWithChunkLoad(player, targetLoc, aparecium);

// Get the block behind a player
Block behind = LocationUtility.getBlockBehindPlayer(player, 2);

// Get a fancy string representation of a location
String fancy = LocationUtility.toFancyString(loc, true);
```

**Note:**
- Useful for plugin features involving location math, random spawns, area effects, and safe teleportation.

---

## ItemUtility

Utility class providing helper methods for item manipulation.  
Includes validation, normalization, enchantment compatibility checks, and mob spawner item creation.

### Key Features

- **Item Validation:**  
  Check if an `ItemStack` has metadata, display name, and lore.
- **Item Normalization:**  
  Add all available `ItemFlag`s to an `ItemStack` for consistent appearance.
- **Enchantment Compatibility:**  
  Get a list of enchantments compatible with a given `ItemStack`.
- **Mob Spawner Item Creation:**  
  Create an `ItemStack` representing a mob spawner, copying the spawn type from a block if possible.

### Example Usage

```java
import com.ignitedev.aparecium.util.ItemUtility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

// Validate an item stack data
boolean valid = ItemUtility.isValid(itemStack);

// Normalize an item stack (add all item flags)
ItemStack normalized = ItemUtility.normalizeItemStack(itemStack);

// Get compatible enchantments for an item
List<Enchantment> enchantments = ItemUtility.getCompatibleEnchantments(itemStack);

// Create a mob spawner item stack from a block
ItemStack spawner = ItemUtility.getMobSpawnerItemStackFromBlock(block);
```

**Note:**
- Useful for custom item creation, GUI systems, and plugin features involving item manipulation.

---

## InventoryUtility

Utility class providing helper methods for inventory manipulation and checks.  
Includes methods for checking inventory contents, slot status, available space, free slots, and comparing inventory titles.

### Key Features

- **Check if inventory contains an item:**  
  Determine if a specific `ItemStack` is present in the inventory.
- **Check if a slot is empty:**  
  Verify if a given slot in the inventory is empty or contains air.
- **Check available space:**  
  Assess if the inventory has enough space for a specified number of items.
- **Count free slots:**  
  Get the number of empty slots in the inventory.
- **Compare inventory titles:**  
  Compare inventory titles as strings or Adventure `Component`s, ignoring color codes.

### Example Usage

```java
import com.ignitedev.aparecium.util.InventoryUtility;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryView;
import net.kyori.adventure.text.Component;

// Check if inventory contains a specific item
boolean hasItem = InventoryUtility.inventoryContainsItemStack(inventory, itemStack);

// Check if slot 5 is empty
boolean isEmpty = InventoryUtility.isSlotEmpty(inventory, 5);

// Check if inventory has space for 128 items
boolean hasSpace = InventoryUtility.hasPlace(128, inventory);

// Get the number of free slots
int freeSlots = InventoryUtility.getFreeSlots(inventory);

// Compare two inventory views by their titles
boolean sameTitle = InventoryUtility.compareInventories(view1, view2);

// Compare two inventory titles as strings
boolean sameStringTitle = InventoryUtility.compareInventories("Title1", "Title2");

// Compare two inventory titles as Adventure Components
boolean sameComponentTitle = InventoryUtility.compareInventories(component1, component2);
```

**Note:**
- Useful for GUI systems, custom inventory logic, and plugin features involving inventory management.

---

## EntityUtility

Utility class providing helper methods for entity manipulation.  
Includes methods for pushing entities, checking spawn times, and finding entities by UUID across all worlds.

### Key Features

- **Push Entity Forward:**  
  Pushes an entity in the direction it is facing with a specified velocity.
- **Check Spawn Time:**  
  Checks if entities can spawn at the current world time based on a given day cycle.
- **Find Entity by UUID:**  
  Finds a living entity or any entity by UUID across all loaded worlds.

### Example Usage

```java
import com.ignitedev.aparecium.util.EntityUtility;
import com.ignitedev.aparecium.util.enums.DayCycle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.World;
import java.util.UUID;

// Push an entity forward with velocity 1.5
EntityUtility.pushEntityForward(entity, 1.5);

// Check if mobs can spawn at the current world time (e.g., NIGHT)
boolean canSpawn = EntityUtility.canSpawnAtCurrentWorldTime(DayCycle.NIGHT, world);

// Find a living entity by UUID across all worlds
LivingEntity living = EntityUtility.findLivingEntityInAllWorlds(uuid);

// Find any entity by UUID across all worlds
Entity found = EntityUtility.findEntityInAllWorlds(uuid);
```

**Note:**
- Useful for custom mob mechanics, entity management, and time-based spawning logic.

---

## DataUtility

Utility class providing various helper methods for data manipulation, validation, and common operations used throughout the plugin.

### Key Features

- **Placeholder Management:**  
  Update or add placeholders in a list, useful for dynamic text replacement.
- **Type Validation:**  
  Check if a string is a valid UUID or boolean, or if an enum contains a value.
- **List Operations:**  
  Remove elements, merge lists, get random elements, flatten arrays.
- **Block and World Utilities:**  
  Check if a block is a crop, replace text on signs, check if it is day in a world.
- **Time Formatting:**  
  Convert milliseconds to a human-readable duration string.
- **Command Unregistration:**  
  Unregister Bukkit commands at runtime using reflection.

### Example Usage

```java
import com.ignitedev.aparecium.util.DataUtility;

// Update or add a placeholder
List<Placeholder> updated = DataUtility.updateData(placeholders, "playerName", "Steve");

// Check if a string is a UUID
boolean valid = DataUtility.isUUID("123e4567-e89b-12d3-a456-426614174000");

// Remove first 3 elements from a list
DataUtility.removeListElements(myList, 3);

// Get a random element from an array
String random = DataUtility.getRandomElement(new String[]{"A", "B", "C"});

// Flatten a 2D array
String[] flat = DataUtility.flatten(new String[][]{{"A", "B"}, {"C"}});

// Round a double to 2 decimal places
double rounded = DataUtility.round(3.14159, 2);

// Replace text on a sign
DataUtility.replaceSignText(sign, "old", "new");

// Unregister a Bukkit command
DataUtility.unRegisterBukkitCommand(command, aparecium);
```
---
## BungeeUtility

#### TO-DO: Add BungeeCord utility methods for cross-server communication, player management, and other Bungee-specific functionalities.

---

## BlockVisualizerUtility

Utility for visualizing blocks in the Minecraft world by spawning glowing, named falling blocks at specific locations. Useful for highlighting or previewing blocks to players.

### Key Features

- **Visualize a Block:**  
  Temporarily replaces a block with a glowing, named falling block for all players in the world.
- **Stop Visualizing:**  
  Removes the visualization and restores the original block appearance for all players.
- **Stop All Visualizations:**  
  Removes all currently visualized blocks.
- **Check Visualization:**  
  Checks if a block is currently being visualized.

### Example Usage

```java
import com.ignitedev.aparecium.util.BlockVisualizerUtility;

// Visualize a block with custom name and appearance
BlockVisualizerUtility.visualize(block, blockData, "Special Block", aparecium);

// Stop visualizing a specific block
BlockVisualizerUtility.stopVisualizing(block, aparecium);

// Stop all visualizations
BlockVisualizerUtility.stopAll(aparecium);

// Check if a block is visualized
boolean isVisualized = BlockVisualizerUtility.isVisualized(block);
```

---
## LoreBuilderUtility

Utility class for building and manipulating item lore (list of `Component` lines).
Allows you to easily append, insert, remove, replace, and conditionally modify lore lines, supporting method chaining and advanced predicates.

### Key Features

- **Append and Insert:**  
  Add new lore lines at the end or at a specific index.
- **Conditional Operations:**  
  Append, insert, or remove lines only if a predicate is satisfied.
- **Remove and Replace:**  
  Remove lines by index or value, replace lines by value or using a predicate.
- **Clear and Check:**  
  Clear all lore, check if lore is empty or contains a specific line.
- **Build:**  
  Retrieve the final list of lore components.

### Example Usage

```java
import com.ignitedev.aparecium.util.text.LoreBuilderUtility;
import net.kyori.adventure.text.Component;
import java.util.List;

// Create a new lore builder
LoreBuilderUtility builder = new LoreBuilderUtility();

// Append lines to the lore
builder.append(Component.text("Line 1"))
       .append(Component.text("Line 2"));

// Insert a line at index 1
builder.insert(1, Component.text("Inserted Line"));

// Conditionally append a line if a value is true
builder.appendIf(Component.text("Special Line"), true, b -> b);

// Remove a line by value
builder.remove(Component.text("Line 1"));

// Replace a line
builder.replace(Component.text("Line 2"), Component.text("Replaced Line"));

// Build the final lore list
List<Component> lore = builder.build();
```

**Note:**
- All methods support method chaining for fluent usage.
- Useful for dynamic item creation, custom GUIs, and advanced lore manipulation.
- For reading lore, use `LoreReaderUtility`.

---

## LoreReaderUtility

Utility class for reading and extracting information from item lore (a list of `Component` lines).
Allows you to convert lore to strings, get values by key, filter lines, and parse numbers from lore.

### Key Features

- **Lore as String List:**  
  Convert all lore components to a list of plain strings.
- **Get Line by Index:**  
  Retrieve a specific lore line by its index.
- **Filter Lines:**  
  Get all lore lines matching a predicate.
- **Get Values by Key:**  
  Extract all or the first value from lore lines starting with a specific key.
- **Parse Numbers:**  
  Parse integer or double values from lore lines with a given key.

### Example Usage

```java
import com.ignitedev.aparecium.util.text.LoreReaderUtility;
import net.kyori.adventure.text.Component;
import java.util.List;

// Create a LoreReaderUtility from a list of Components
List<Component> lore = List.of(Component.text("Level: 5"), Component.text("Owner: Steve"));
LoreReaderUtility reader = new LoreReaderUtility(lore);

// Get all lore as plain strings
List<String> lines = reader.getAsStringList(); // ["Level: 5", "Owner: Steve"]

// Get the first lore line
String firstLine = reader.get(0).toString();

// Get all values for a key
List<String> levels = reader.getAllValues("Level: "); // ["5"]

// Get the first value for a key
String owner = reader.getFirstValue("Owner: "); // "Steve"

// Parse a double from lore
double value = reader.getDouble("Level: "); // 5.0

// Parse an int from lore
int level = (int) reader.getInt("Level: "); // 5

// Get all lines containing "Owner"
List<String> owners = reader.getMatching(line -> line.contains("Owner"));
```

**Note:**
- Useful for extracting custom data from item lore, such as stats, owner names, or custom tags.
- For building or modifying lore, use `LoreBuilderUtility`.

---

## Placeholder

Utility class for handling text placeholders, especially for dynamic message formatting and component replacement.  
Allows you to define key-value pairs (as `ApareciumComponent`) and replace them in text or lists of components.

### Key Features

- **Create Placeholders:**  
  Easily create placeholders from components, numbers, or objects.
- **Replace in Text:**  
  Replace all occurrences of the placeholder key with its value in a single component or a list of components.
- **Flexible Value Types:**  
  Supports string, number, and object values for dynamic replacement.

### Example Usage

```java
import com.ignitedev.aparecium.util.text.Placeholder;
import com.ignitedev.aparecium.component.ApareciumComponent;
import java.util.List;

// Create a placeholder for player name
Placeholder playerPlaceholder = Placeholder.replacing(
    ApareciumComponent.of("{player}"),
    ApareciumComponent.of("Steve")
);

// Replace in a single component
ApareciumComponent message = ApareciumComponent.of("Hello, {player}!");
ApareciumComponent replaced = playerPlaceholder.replaceIn(message); // "Hello, Steve!"

// Replace in a list of components
List<ApareciumComponent> lore = List.of(
    ApareciumComponent.of("Owner: {player}"),
    ApareciumComponent.of("Level: {level}")
);
Placeholder levelPlaceholder = Placeholder.replacing(
    ApareciumComponent.of("{level}"),
    5
);
List<ApareciumComponent> replacedLore = levelPlaceholder.replaceIn(
    playerPlaceholder.replaceIn(lore)
);
// ["Owner: Steve", "Level: 5"]
```

**Note:**
- Useful for dynamic message formatting, lore generation, and any situation where text needs to be customized with variable values.

---

## TextUtility

Utility class for manipulating strings and Adventure `Component`s
Provides methods for colorizing text, parsing MiniMessage, serializing components, removing color codes, placeholder replacement, formatting, and more.

### Key Features

- **Colorize Strings:**  
  Translate `&` color codes to Bukkit color codes.
- **MiniMessage Parsing:**  
  Parse strings or lists to Adventure `Component`s using MiniMessage (Paper only).
- **Component Serialization:**  
  Serialize Adventure `Component`s to MiniMessage strings.
- **Remove Color Codes:**  
  Remove color codes from strings or components.
- **Placeholder Replacement:**  
  Replace placeholders in components or custom `ApareciumComponent`s.
- **Enum Name Formatting:**  
  Convert enum names to user-friendly strings.
- **Integer Parsing:**  
  Extract all integers from a string.
- **Invisible/Visible Text:**  
  Make text invisible (for hidden data) or revert it to visible.
- **Progress Bar Generation:**  
  Create a colored progress bar string.
- **Percentage Formatting:**  
  Format a float as a percentage string.

### Example Usage

```java
import com.ignitedev.aparecium.util.text.TextUtility;
import com.ignitedev.aparecium.util.text.Placeholder;
import com.ignitedev.aparecium.component.ApareciumComponent;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import java.util.List;

// Colorize a string with & codes
String colored = TextUtility.colorize("&aHello &bWorld!");

// Parse MiniMessage to Component (Paper only)
Component comp = TextUtility.parseMiniMessage("<red>Hello <yellow>World!");

// Serialize a Component to MiniMessage string (Paper only)
String serialized = TextUtility.serializeComponent(comp);

// Remove color codes from a string
String noColor = TextUtility.removeColor("&aTest &bString");

// Replace placeholders in an ApareciumComponent
ApareciumComponent text = ApareciumComponent.of("Hello, {player}!");
ApareciumComponent replaced = TextUtility.replace(text, Placeholder.of("{player}", "Steve"));

// Get a user-friendly enum name
String friendly = TextUtility.getEnumFriendlyName(ChatColor.DARK_RED); // "Dark Red"

// Parse all integers from a string
int[] numbers = TextUtility.parseStringIntegers("Level 5 XP 100");

// Make a string invisible (hidden data)
String hidden = TextUtility.invisible("Secret");

// Make an invisible string visible again
String visible = TextUtility.visible(hidden);

// Create a progress bar
String bar = TextUtility.getProgressBar(3, 10, 10, '|', ChatColor.GREEN, ChatColor.GRAY);

// Format a float as a percentage
String percent = TextUtility.toPercentage(0.85f, 1); // "85.0%"
```

**Note:**
- Methods using Adventure `Component` or MiniMessage require Paper or compatible server.
- Useful for chat formatting, GUI text, placeholder handling, and advanced string manipulation in plugins.

---

## RandomSelector

Utility class for randomly selecting elements from a collection, supporting both uniform and weighted distributions.  
Useful for loot tables, random events, and any scenario where you need to pick random elements with or without custom weights.

### Key Features

- **Uniform Selection:**  
  Selects elements with equal probability from a collection.
- **Weighted Selection:**  
  Selects elements based on custom weights, using Vose's alias method for efficient O(1) selection.
- **Thread-Safe:**  
  All methods are thread-safe and suitable for concurrent use.
- **Streaming Support:**  
  Provides an infinite stream of random selections for advanced use cases.

### Example Usage

```java
import com.ignitedev.aparecium.util.collection.RandomSelector;
import java.util.*;

// Uniform random selection from a list
List<String> items = Arrays.asList("A", "B", "C", "D");
RandomSelector<String> uniformSelector = RandomSelector.uniform(items);
Random random = new Random();
String randomItem = uniformSelector.next(random);

// Weighted random selection from a map of weights
Map<String, Double> weights = new HashMap<>();
weights.put("Common", 70d);
weights.put("Rare", 25d);
weights.put("Epic", 5d);
RandomSelector<String> weightedSelector = RandomSelector.weighted(weights.keySet(), weights::get);
String loot = weightedSelector.next(random);

// Infinite stream of random selections
uniformSelector.stream(random)
    .limit(5)
    .forEach(System.out::println); // Prints 5 random items
```

**Note:**
- The collection passed to the selector is copied internally; changes to the original collection do not affect the selector.
- For weighted selection, all weights must be positive and non-zero.
- Efficient for large collections and frequent random selections.

---

## WeightCollection

Utility class for weighted random selection from a collection of elements.  
Each element is assigned a weight (the higher the weight, the higher the chance of being selected).  
Useful for loot tables, random events, or any scenario where you want to pick elements with different probabilities.

### Key Features

- **Add elements with weights:**  
  Add elements to the collection with a specified weight (must be greater than 0).
- **Weighted random selection:**  
  Efficiently select a random element, where the probability is proportional to its weight.
- **Cloneable map:**  
  Retrieve a copy of the internal map for inspection or debugging.
- **Custom Random instance:**  
  Optionally use your own `Random` instance for deterministic results.

### Example Usage

```java
import com.ignitedev.aparecium.util.collection.WeightCollection;
import java.util.Random;

// Create a WeightCollection with default Random
WeightCollection<String> collection = new WeightCollection<>();

// Add elements with weights
collection.add(70, "Common");
collection.add(25, "Rare");
collection.add(5, "Epic");

// Get a random element based on weights
String result = collection.next(); // "Common" is most likely

// Use a custom Random for reproducible results
Random random = new Random(123);
WeightCollection<Integer> numbers = new WeightCollection<>(random);
numbers.add(1, 10);
numbers.add(2, 20);
numbers.add(7, 30);
int picked = numbers.next(); // 30 is most likely

// Get a copy of the internal map
var mapCopy = collection.getMap();
```

**Note:**
- Weights must be positive; elements with weight \<= 0 are ignored.
- The selection is efficient even for large collections.
- Useful for any plugin feature requiring weighted randomness.