package name.ball.joshua.spigot.trace;

import name.ball.joshua.spigot.trace.di.Inject;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;

public class TraceCommand {

    @Inject private EventListener eventListener;
    @Inject private Events events;
    @Inject private MethodPropertyMapper methodPropertyMapper;
    @Inject private TrackableFactory trackableFactory;

    private final CommandSender commandSender;

    public TraceCommand(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public void reflect(String reflectable) {
        Trackable trackable = getTrackable(reflectable);
        if (trackable != null) {
            for (Trackable subTrackable : trackable.getSubTrackables()) {
                commandSender.sendMessage(subTrackable.getLastProperty());
            }
        }
    }

    public void create(final String trigger) {
        final Trackable trackable = getTrackable(trigger);
        if (trackable != null) {
            eventListener.put(trackable, new TrackableLogger() {
                @Override
                public void log(Event event) {
                    commandSender.sendMessage(trigger + " = " + trackable.getValue(event));
                }
            });
        }
    }

    public void deleteAll() {
        eventListener.clear();
    }

    protected Trackable getTrackable(String property) {
        try {
            return trackableFactory.newTrackable(new Owner() {
                @Override public CommandSender getCommandSender() {
                    return commandSender; // todo static class so don't keep reference to tracecommand class around
                }
            }, property);
        } catch (NoSuchMethodException e) {
            commandSender.sendMessage("Unknown property type: '" + e.getMessage() + "'.");
            return null;
        } catch (ClassNotFoundException e) {
            commandSender.sendMessage("Unknown event type: '" + e.getMessage() + "'.");
            return null;
        }
    }

}
