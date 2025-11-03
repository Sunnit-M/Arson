# ArsonUtils
## A fabric server management tool

-----------------------------------

ArsonUtils is a powerful and easy-to-use tool designed to help server administrators manage their Fabric-based Minecraft servers efficiently. With a variety of features and commands, ArsonUtils simplifies server management tasks, making it easier to maintain a smooth gaming experience for players.

### Features
- **Custom Ban System** 
  - Ban and unban players with ``/arson timedBan <player> [Days] [Hours] [Minutes] [Seconds] [reason] `` and ``/arson unban <player>`` commands.
  - Permanently ban player with ``/arson permanentBan <player> [reason]`` command.'
  - With live time tracking, you can see how much time is left on a player's ban.

- **Player Management**
  - Set mods and admins with ease. (Permission System coming later)

- **Server Management**
  - Enter Maintenance Mode with ``/arson maintenance <on/off>`` command to restrict access during updates or repairs.
  - Set Required And Allowed Mods for you server.
  - Throttle Constant Player rejoin with a timeout.

- **Player QoL**
  - PLayer playtime is tracked and can be viewed in the menu.
  - Optional Minecraft Improvements Supported ( Currently unable to disable )

### Installation
1. Download the latest version of ArsonUtils from the [releases page](https://https://github.com/Sunnit-M/Arson/releases).
2. Place the downloaded JAR file into the `mods` folder of your Fabric server.
3. Start or restart your Fabric server to load the mod.
4. Download the client mod and place it in the `mods` folder of your Minecraft client for full functionality.
5. Configure the mod settings in the `config/arsonutils` folder as needed.

### Commands
- `/arson timedBan <player> [Days] [Hours] [Minutes] [Seconds] [reason]` - Temporarily ban a player for a specified duration.
- `/arson permanentBan <player> [reason]` - Permanently ban a player.
- `/arson unban <player>` - Unban a player.
- `/arson maintenance <on/off>` - Enable or disable maintenance mode.
- `/arson setMotd <text/default>` - Set MOTD for the server (Use default to set to default set in config).

### Contributing
Contributions are welcome! If you have suggestions for new features or improvements, please open an issue or submit a pull request