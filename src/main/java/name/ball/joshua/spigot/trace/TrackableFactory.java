package name.ball.joshua.spigot.trace;

import name.ball.joshua.spigot.trace.di.Inject;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TrackableFactory {

    @Inject private TrackableFactoryDirect trackableFactoryDirect;
    @Inject private Events events;

    public Trackable newTrackable(String property) throws NoSuchMethodException, ClassNotFoundException {
        String[] parts = property.split("\\.");
        String className = parts[0];
        Class<? extends Event> eventClass = null;
        for (Class<? extends Event> ec : events.getAllEventClasses()) {
            if (ec.getSimpleName().equals(className)) {
                eventClass = ec;
                break;
            }
        }
        if (eventClass == null) {
            throw new ClassNotFoundException(className);
        }
        List<String> properties = new ArrayList<String>(parts.length - 1);
        properties.addAll(Arrays.asList(parts).subList(1, parts.length));
        return trackableFactoryDirect.newTrackable(eventClass, properties);
    }

    public Trackable newTrackable(Class<? extends Event> eventClass) {
        try {
            return trackableFactoryDirect.newTrackable(eventClass, Collections.<String>emptyList());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e); // should never happen with an empty list
        }
    }

    public Trackable newTrackable(Class<? extends Event> eventClass, List<String> propertyChain) throws NoSuchMethodException {
        return trackableFactoryDirect.newTrackable(eventClass, propertyChain);
    }
}
