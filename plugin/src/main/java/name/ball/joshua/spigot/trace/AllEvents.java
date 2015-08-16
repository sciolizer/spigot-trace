package name.ball.joshua.spigot.trace;

import name.ball.joshua.bukkit.eventtrace.api.ApiSerializables;
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

    public ApiSerializables.Events getEvents(final ApiSerializables.Query query) {
        try {
            return getEventsUnsafe(query);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private ApiSerializables.Events getEventsUnsafe(final ApiSerializables.Query query) throws NoSuchFieldException, IllegalAccessException {
        ApiSerializables.Events result = new ApiSerializables.Events();
        for (TrackedEvent event : events) {
            String eventClassName = event.event.getClass().getSimpleName();
            if (query.filter.classes != null && !query.filter.classes.contains(eventClassName)) continue;
            if (query.filter.ids != null && !query.filter.ids.contains(event.id)) continue;
            ApiSerializables.EventInfo eventInfo = new ApiSerializables.EventInfo();
            eventInfo.id = event.id;
            eventInfo.when = event.when;
            if (query.projection.klass) {
                eventInfo.klass = eventClassName;
            }
            if (query.projection.properties.isEmpty()) {
                eventInfo.values = Collections.emptyList();
            } else {
                eventInfo.values = new ArrayList<ApiSerializables.Value>(query.projection.properties.size());
                for (ApiSerializables.Property property : query.projection.properties) {
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
