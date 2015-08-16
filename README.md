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

Known bugs
----------

Can't do properties more than 1 layer deep, e.g. BrewEvent.Contents.Ingredient

Plans
-----

Since this is for development only, there's really not much point in trying to control everything from the game itself.
Instead, build a normal swing gui, and control everything from there.

run arbitrary java code and/or javascript; probably also nicer to have an external program running

in order to get maximum introspectability, you'd want to preserve all of the events within the memory of the minecraft game itself.
That way you could drill down arbitrarily into the events and see any properties you wanted to

Not sure how quickly the events will accumulate... that's probably a good question to answer before going much further into the design.
Different kinds of screens you'd like to be able to see:
  For a set of

oh dude - there's also the different priorities - would be interesting to see when the events are getting cancelled and when they are not
