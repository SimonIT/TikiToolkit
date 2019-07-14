**TikiToolkit - Toolbar / Slot Based Administration Tools**  
[![Build Status](https://travis-ci.com/SimonIT/TikiToolkit.svg?branch=master)](https://travis-ci.com/SimonIT/TikiToolkit)

![[IMG]](http://dl.dropbox.com/u/465123/minecraft/plugins/TikiToolkit/tikitoolkit.jpg)  
TikiToolkit allows you to bind a plugin command to the left and right click function of any item on your toolbar. By using a simple configuration file you can define tools per player, per slot, per item. Which means a huge amount of customization for each user. Want to use a fish to access WorldEdit's teleport commands? NO PROBLEM!  
  
This plugin is an alternative to [Wandmin](http://forums.bukkit.org/threads/admn-wandmin-v0-29-easy-command-use-357.1823/) by Nathanwolf, and will work nicely with his [Spells](http://forums.bukkit.org/threads/fun-spells-v0-98-magic-for-exploring-building-combat-and-more-478.729/) plugin. I found that with Wandmin, I was only binding a couple commands to each wand and spending more time cycling through wands than it would take to type the command.This fixes that problem by limiting you to two commands per tool, but you can define up to 9 tools of any type (18 possible commands, if you don't want any hotbar slots)  
  
There is no method to actually modify the tools in game, it must be done through the config file. However if enough people want that feature, I will add it. But I think the config file editing works nicely since I personally never touch my tools once they're set up.  
  
![[IMG]](http://dl.dropbox.com/u/465123/minecraft/plugins/TikiToolkit/useage.png)  
_(See the example config at the bottom to see this screenshot's config file)_  
If you do not like the "_ToolName_ Selected" messages. Either remove the "name: ToolName" option in the config for the tool you don't want to show the message, or set "selected\_msg: false" for the player (see example config).  
**Features:**  

*   Bind any item or block to a function
*   Respawn with your admin tools when you die (configurable)
*   Prevents you from dropping inventory when killed (configurable)
*   Allows you to chain commands together (eg: /hpos 1, /hpos 2, /expand 1 up, /set 1) for 1 tool

****Commands:****  

*   **/tiki reload** - Reload the configuration file
*   **/tiki identify** - Identify the item you're currently holding (for use in config file)
*   **/tiki tools** - Adds your configured tools to your hotbar

**Changelog:**  

** Version 1.1.7: **

**\- Updated for to support PLAYER\_INTERACT changes, CraftBukkit 561+**​

Version 1.0.7:​

\- Last version that supports any craftbukkit prior to 561​

\- Fixes an issue with command chains not working for some plugins​

Version 1.0.6:​

\- Selected tool messages can be turned off with "selected\_msg: false", see example config.​

Version 1.0.5:​

\- Now uses player.chat() to send commands. Which means you can send chat messages and perform native minecraft commands​

\- Improved help syntax for invalid commands​

\- Switched to syncTasks when restoring inventory on respawn​

Version 1.0.4:​

\- Fixed some commands not working if they only registered in plugin.yml​

Version 1.0.3:​

\- Only ops can /tiki reload the config now (and the console)​

\- Added a /tiki tools command to restore the configured tools without killing yourself​

Version 1.0.2:​

\- All commands require leading / now (eg.. /thru instead of thru)​

\- Plugins that use PLAYER\_COMMAND\_PREPROCESS now supported​

Version 1.0.1:​

\- Added command chaining (see config example)​

Version 1.0.0:​

\- Initial Release​
