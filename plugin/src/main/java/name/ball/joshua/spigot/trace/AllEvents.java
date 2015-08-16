package name.ball.joshua.spigot.trace;

import name.ball.joshua.bukkit.eventtrace.api.Api;
import org.bukkit.event.Event;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllEvents {

    private long startTime = System.currentTimeMillis();
    private int id = 0;
    private List<TrackedEvent> events = new ArrayList<TrackedEvent>();

    public void add(Event event) {
        try {
            TrackedEvent trackedEvent = new TrackedEvent();
            trackedEvent.id = id;
            trackedEvent.when = (int)(System.currentTimeMillis() - startTime);
            trackedEvent.event = event;
            events.add(trackedEvent);
        } finally {
            id++;
        }
    }

    public Api.Events getEvents(final Api.Query query) {
        try {
            return getEventsUnsafe(query);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Api.Events getEventsUnsafe(final Api.Query query) throws NoSuchFieldException, IllegalAccessException {
        Api.Events result = new Api.Events();
        for (TrackedEvent event : events) {
            String eventClassName = event.event.getClass().getSimpleName();
            if (query.filter.classes != null && !query.filter.classes.contains(eventClassName)) continue;
            if (query.filter.ids != null && !query.filter.ids.contains(event.id)) continue;
            Api.EventInfo eventInfo = new Api.EventInfo();
            eventInfo.id = event.id;
            eventInfo.when = event.when;
            if (query.projection.klass) {
                eventInfo.klass = eventClassName;
            }
            if (query.projection.properties.isEmpty()) {
                eventInfo.values = Collections.emptyList();
            } else {
                eventInfo.values = new ArrayList<Api.Value>(query.projection.properties.size());
                for (Api.Property property : query.projection.properties) {
                    Object value = event.event;
                    for (String field : property.chain.fields) { // todo: stop early on nulls
                        Field declaredField = value.getClass().getDeclaredField(field);
                        declaredField.setAccessible(true);
                        value = declaredField.get(value);
                    }
                    eventInfo.values.add(property.evaluation.evaluate(value));
                }
            }
            result.events.add(eventInfo);
        }
        return result;
    }

    private static class TrackedEvent {
        public int id;
        public int when;
        public Event event;
    }
}
