Spigot tracer
=============

This is a plugin for bukkit/spigot, intended for development use only. It adds a command 'trace', which can be used
to enable logging of all events of a certain type.

    /trace create BlockBreakEvent

Logs the BlockBreakEvent every time it happens.

    /trace create BlockEvent

Logs all BlockBreakEvents, BlockBurnEvents, BlockCanBuildEvents, etc.

    /trace create BlockBreakEvent.Player

Logs the player that broke a block every time a block is broken.

    /trace create BlockBreakEvent.Player.Name

Logs only the name of the player that broke a block every time a block is broken.

Available properties can be seen using the reflect command.

    /trace reflect BlockBreakEvent

outputs

    Block
    ExpToDrop
    Player

All log lines can be cancelled with delete all.

    /trace delete all
