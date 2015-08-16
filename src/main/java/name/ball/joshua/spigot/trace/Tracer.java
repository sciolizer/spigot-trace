package name.ball.joshua.spigot.trace;

import name.ball.joshua.spigot.trace.di.DI;
import name.ball.joshua.spigot.trace.di.Inject;
import name.ball.joshua.spigot.trace.md.MaterialDataLorifier;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Tracer extends JavaPlugin implements Listener {

    @Inject private Events events;
//    @Inject private InstaBrewer instaBrewer;
//    @Inject private MaterialDataLorifier materialDataLorifier;
//    @Inject private TraceCommandFactory traceCommandFactory;

    public void onDisable() {
    }

    @Override
    public void onEnable() {
        List<Class<? extends Event>> eventClasses = events.getEvents();
        for (Class<? extends Event> eventClass : eventClasses) {

        }
    }

    public void onEnablePrev() {
        getDI().injectMembers(this);
        getCommand("trace").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                TraceCommand traceCommand = traceCommandFactory.newTraceCommand(commandSender);
                if (strings.length == 0) {
                    commandSender.sendMessage("Available sub-commands are 'reflect', 'create', and 'delete'.");
                    return false;
                }
                if ("reflect".equals(strings[0])) {
                    if (strings.length <= 1) {
                        commandSender.sendMessage("What would you like to reflect?, e.g. '/trace reflect BlockPlaceEvent'");
                        return false;
                    }
                    if (strings.length > 2) {
                        commandSender.sendMessage("Reflecting '" + strings[1] + "'.");
                    }
                    traceCommand.reflect(strings[1]);
                    return true;
                } else if ("create".equals(strings[0])) {
                    if (strings.length <= 1) {
                        commandSender.sendMessage("What trace would you like to create? e.g. '/trace create BlockPlaceEvent'");
                        return false;
                    }
                    if (strings.length > 2) {
                        commandSender.sendMessage("Creating trace for '" + strings[1] + "'.");
                    }
                    traceCommand.create(strings[1]);
                    return true;
                } else if ("delete".equals(strings[0])) {
                    if (strings.length <= 1 || !"all".equals(strings[1])) {
                        commandSender.sendMessage("Try '/trace delete all'.");
                        return false;
                    }
                    traceCommand.deleteAll();
                    return true;
                } else {
                    commandSender.sendMessage("Unrecognized sub-command '" + strings[0] + "'. Available sub-commands are 'reflect', 'create', and 'delete'.");
                    return false;
                }
            }
        });
        getCommand("potion").setExecutor(new PotionCommandExecutor());
        Bukkit.getPluginManager().registerEvents(materialDataLorifier, this);
        Bukkit.getPluginManager().registerEvents(instaBrewer, this);
    }

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
