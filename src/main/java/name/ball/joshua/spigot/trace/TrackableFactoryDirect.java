package name.ball.joshua.spigot.trace;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;

import java.util.List;

public interface TrackableFactoryDirect {
    Trackable newTrackable(Owner owner, Class<? extends Event> eventClass, List<String> propertyChain) throws NoSuchMethodException;
}
