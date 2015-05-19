package name.ball.joshua.spigot.trace;

import org.bukkit.event.Event;

import java.util.List;

public interface TrackableFactoryDirect {
    Trackable newTrackable(Class<? extends Event> eventClass, List<String> propertyChain) throws NoSuchMethodException;
}
