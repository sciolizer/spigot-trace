package name.ball.joshua.spigot.trace;

import name.ball.joshua.spigot.trace.di.InitializingBean;
import name.ball.joshua.spigot.trace.di.Inject;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.util.LinkedHashMap;
import java.util.Map;

public class EventListener implements Listener, InitializingBean {

    @Inject private Events events;
    @Inject private Plugin plugin;

    private Map<Trackable, TrackableLogger> trackables = new LinkedHashMap<Trackable, TrackableLogger>();

    @Override
    public void afterPropertiesSet() throws Exception {
        EventExecutor eventExecutor = new EventExecutor() {
            @Override
            public void execute(Listener listener, Event event) throws EventException {
                Class<? extends Event> eventClass = event.getClass();
                for (Map.Entry<Trackable, TrackableLogger> entry : trackables.entrySet()) { // todo: something more efficient than iteration
                    if (entry.getKey().getEventClass().isAssignableFrom(eventClass)) {
                        entry.getValue().log(event);
                    }
                }
            }
        };
        for (Class<? extends Event> eventClass : events.getEvents()) {
            Bukkit.getPluginManager().registerEvent(eventClass, this, EventPriority.MONITOR, eventExecutor, plugin, true);
        }
    }

    public void put(Trackable trackable, TrackableLogger trackableLogger) {
        trackables.put(trackable, trackableLogger);
    }

    public void remove(Trackable trackable) {
        trackables.remove(trackable);
    }

    public void clear() {
        trackables.clear();
    }
}
