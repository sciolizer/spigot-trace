package name.ball.joshua.spigot.trace;

import name.ball.joshua.spigot.trace.di.DI;
import name.ball.joshua.spigot.trace.di.Inject;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Tracer extends JavaPlugin implements Listener {

    @Inject private Events events;
//    @Inject private InstaBrewer instaBrewer;
//    @Inject private MaterialDataLorifier materialDataLorifier;
//    @Inject private TraceCommandFactory traceCommandFactory;

    private List<Event> allEvents = new ArrayList<Event>(); // even at 32,900 events, the server was still only using half (200mb) of its allocated ram (400mb).
    // So it's probably entirely reasonable to keep the most recent 100,000 events.
    // I suggest that for each new screen the user opens, we create a new collection. That way our "allEvents" tab
    // can continue to roll over without affecting the watched collections.

    public void onDisable() {
    }

    private void serializeAll() throws IOException {
        List<Api.EventInfo> result = new ArrayList<Api.EventInfo>(allEvents.size());
        int i = 0;
        for (Event event : allEvents) {
            Api.EventInfo eventInfo = new Api.EventInfo();
            eventInfo.id = i;
            eventInfo.klass = event.getClass().getSimpleName();
            eventInfo.when = i; // todo
            Api.Value value = new Api.Value();
            value.integer = 0;
            value.string = "yes";
            eventInfo.values = Collections.singletonList(value);
            result.add(eventInfo);
            i++;
        }
        long start = System.nanoTime();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(result);
        long end = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(end - start);
        Bukkit.getServer().broadcastMessage("millis = " + millis);
        int size = baos.toByteArray().length;
        Bukkit.getServer().broadcastMessage("size = " + size);
    }

    @Override
    public void onEnable() {
        getDI().injectMembers(this);
        List<Class<? extends Event>> eventClasses = events.getEvents();
        EventExecutor eventExecutor = new EventExecutor() {

//            private int i = 0;

            @Override
            public void execute(Listener listener, Event event) throws EventException {
//                Bukkit.getServer().broadcastMessage("adding event");
                allEvents.add(event);
                int size = allEvents.size();
                if (size % 100 == 0) {
                    Bukkit.getServer().broadcastMessage("size = " + size);
                    try {
                        serializeAll();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        for (Class<? extends Event> eventClass : eventClasses) {
            Bukkit.getPluginManager().registerEvent(eventClass, this, EventPriority.MONITOR, eventExecutor, this, true);
        }
    }

//    public void onEnablePrev() {
//        getDI().injectMembers(this);
//        getCommand("trace").setExecutor(new CommandExecutor() {
//            @Override
//            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
//                TraceCommand traceCommand = traceCommandFactory.newTraceCommand(commandSender);
//                if (strings.length == 0) {
//                    commandSender.sendMessage("Available sub-commands are 'reflect', 'create', and 'delete'.");
//                    return false;
//                }
//                if ("reflect".equals(strings[0])) {
//                    if (strings.length <= 1) {
//                        commandSender.sendMessage("What would you like to reflect?, e.g. '/trace reflect BlockPlaceEvent'");
//                        return false;
//                    }
//                    if (strings.length > 2) {
//                        commandSender.sendMessage("Reflecting '" + strings[1] + "'.");
//                    }
//                    traceCommand.reflect(strings[1]);
//                    return true;
//                } else if ("create".equals(strings[0])) {
//                    if (strings.length <= 1) {
//                        commandSender.sendMessage("What trace would you like to create? e.g. '/trace create BlockPlaceEvent'");
//                        return false;
//                    }
//                    if (strings.length > 2) {
//                        commandSender.sendMessage("Creating trace for '" + strings[1] + "'.");
//                    }
//                    traceCommand.create(strings[1]);
//                    return true;
//                } else if ("delete".equals(strings[0])) {
//                    if (strings.length <= 1 || !"all".equals(strings[1])) {
//                        commandSender.sendMessage("Try '/trace delete all'.");
//                        return false;
//                    }
//                    traceCommand.deleteAll();
//                    return true;
//                } else {
//                    commandSender.sendMessage("Unrecognized sub-command '" + strings[0] + "'. Available sub-commands are 'reflect', 'create', and 'delete'.");
//                    return false;
//                }
//            }
//        });
//        getCommand("potion").setExecutor(new PotionCommandExecutor());
//        Bukkit.getPluginManager().registerEvents(materialDataLorifier, this);
//        Bukkit.getPluginManager().registerEvents(instaBrewer, this);
//    }

    private DI getDI() {
        Map<Class<?>,DI.Provider<?>> providers = new LinkedHashMap<Class<?>, DI.Provider<?>>();
        providers.put(Plugin.class, new DI.Provider<Plugin>() {
            @Override
            public Plugin get() {
                return Tracer.this;
            }
        });
        return new DI(providers);
    }

}
