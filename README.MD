# Description

Powerful Library that helps you with creating everything you can imagine with Minecraft.

We are aiming for best performance and easy to use API which has powerful configuration for everything.

Library is still in development, and we are working hard to make it better and better.

Our goal is to make it as easy as possible to work with from Developer side and from Configurator side.

Whole library is supporting factory pattern and builder pattern :)

We are supporting PAPER functionalities but as well we support plain Spigot, so you'll be able to run Aparecium
everywhere you would like to, as long you are using at least java 17.

# Functionalities

[+] 30+ Utility Classes

[+] Sorting System (SortingHat)

[+] Item system with Different types of Items; DropItem, LayoutItem, PatternItem and ability to implement your own
item types and support for NBT tags.

[+] Layout system with different types of Layouts; AnimatedLayout, Layout and ability to create your own
implementation

[+] Layer system with different types of Layers; AnimatedLayoutLayer, LayoutLayer, LayoutPatternLayer and ability to
create your layer implementation, you can create layer prefabs and use it with different inventories, so it's going to
speed up your development and you no longer have to duplicate code

[+] Hologram system depending on HolographicDisplays (soon we're going to rewrite it to own system)

[+] Factories System which you can implement in your plugin, so you can create your own factories and use them,
library is using that factory system as well.

[+] Configuration system (we are using the best API of course, https://github.com/2DevsStudio/SimpleJSONConfig),
ItemBase which is holding all Items, LayerBase which is holding all Layers, and LayoutBase which is holding all Layouts,
everything is saved in JSON, but SimpleJsonConfig allows you to save it in YAML as well ( we'll add Aparecium
support for that soon)

[+] Configuration Adapters and Wrappers for giving you possibility to create configuration which will for example give
possibility for Configurator to choose between creating Item/Layer...etc. or getting it from Layer/Item/Layout Base.

[+] ApareciumComponent which is used instead of String/Paper Component, it's holding all information about 
String/Paper Component, and depending on platform you are running you can use it as String or Paper Component. It 
automatically converts String to Paper Component and vice versa.

[+] Simple Command system, so you can create your own simple text commands with using only configuration. ( Helpful 
for commands like /info etc.)

[+] Cuboid System for wrapping two locations into one object, and you can get all locations between two locations.

--- 

### Planned:

[-] Advanced Command System

[-] Event system

[-] Sound system

[-] Chat System

# Supported Versions

> 1.12.2 <-> 1.19
---
> Spigot and Paper API
---
> Java 17+
---

# Dependency

```xml

<repository>
  <id>com.2devsstudio</id>
  <name>2DevsStudio Repository</name>
  <url>https://repo.2devsstudio.com/latest</url>
</repository>

<dependency>
  <groupId>com.ignitedev</groupId>
  <artifactId>Aparecium</artifactId>
  <version>1.0.0</version>
</dependency>

```

