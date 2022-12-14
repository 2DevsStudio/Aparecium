![Aparecium](https://www.2devsstudio.com/resources/aparecium/title.png)

Aparecium is a powerful Library that helps you with creating everything you can imagine with Minecraft.

We are aiming for the best performance and easy-to-use API which has a powerful configuration for
everything. One of our goal is to make it as easy as possible to work from the Developer side and the Configurator
side.

We support **PAPER** functionalities but as well we support plain **Spigot**, so you'll be able to
run Aparecium everywhere you would like to, as long you are using at least **java 17**.

The library is still in development, and we are working hard to make it better and better.

![Functionalities](https://www.2devsstudio.com/resources/separators/functionalities.png)

[+] Supports builder and factory patters

[+] Over 30 Utility Classes

[+] Sorting System (SortingHat)

[+] Item system with Different types of Items; DropItem, LayoutItem, PatternItem, and the ability to
implement your own
item types and support for NBT tags.

[+] Layout system with different types of Layouts; AnimatedLayout, Layout, and the ability to create
your own
implementation

[+] Layer system with different types of Layers; AnimatedLayoutLayer, LayoutLayer,
LayoutPatternLayer, and the ability
to create your layer implementation, you can create layer prefabs and use them with different
inventories, so it's going
to speed up your development and you no longer have to duplicate code

[+] Hologram system depending on HolographicDisplays (soon we're going to rewrite it to our own
system)

[+] Factories System which you can implement in your plugin, so you can create your own factories
and use them, the
library is using that factory system as well.

[+] Configuration system (we are using the best API of
course, https://github.com/2DevsStudio/SimpleJSONConfig),
ItemBase which is holding all Items, LayerBase which is holding all Layers, and LayoutBase which is
holding all Layouts,
everything is saved in JSON, but SimpleJsonConfig allows you to save it in YAML as well ( we'll add
Aparecium support
for that soon)

[+] Configuration Adapters and Wrappers for giving you the possibility to create a configuration
which will for example
give the possibility for Configurator to choose between creating Item/Layer...etc. or getting it
from Layer/Item/Layout
Base.

[+] ApareciumComponent which is used instead of String/Paper Component, it's holding all information
about String/Paper
Component, and depending on the platform you are running you can use it as String or Paper
Component. It automatically
converts String to Paper Component and vice versa.

[+] Simple Command system, so you can create your own simple text commands with using only
configuration. ( Helpful for
commands like /info etc.)

[+] Cuboid System for wrapping two locations into one object, and you can get all locations between
two locations.

--- 

![Planned](https://www.2devsstudio.com/resources/separators/planned.png)

[-] Advanced Command System

[-] Event system

[-] Sound system

[-] Chat System

![Compatibility](https://www.2devsstudio.com/resources/separators/compatibility.png)

> 1.12.2 <-> 1.19
---
> Spigot and Paper API ( Paper Currently is most stable, WIP.)
---
> Java 17+
---

![Dependency](https://www.2devsstudio.com/resources/separators/dependency.png)

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
    <scope>provided</scope>
</dependency>

```

Don't forget to add `depend` in your plugin.yml in order to make sure that Aparecium is loaded
before your plugin.

```yaml

depend: [ Aparecium ]

```

![Requirements](https://www.2devsstudio.com/resources/separators/requirements.png)

### Holographic Displays

If you want to use Holograms you need to download **HolographicDisplays** and add it to your plugins folder.
Currently, we are working on our own hologram system but for now you have to use HolographicDisplays :(

[Here](https://ci.codemc.io/job/filoghost/job/HolographicDisplays/) you can download the latest version of
HolographicDisplays ( for 1.19, 3.0.1 is dev build )

## PlaceholderAPI

Aparecium base many things on PlaceholderAPI so that plugin is definitely **REQUIRED**.

[here](https://www.spigotmc.org/resources/placeholderapi.6245/) you can download the latest version of PlaceholderAPI

