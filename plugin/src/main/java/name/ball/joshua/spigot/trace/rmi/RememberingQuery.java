package name.ball.joshua.spigot.trace.rmi;

import name.ball.joshua.bukkit.eventtrace.api.ApiSerializables;
import name.ball.joshua.bukkit.eventtrace.api.RerunnableQuery;
import name.ball.joshua.spigot.trace.AllEvents;
import name.ball.joshua.spigot.trace.di.Inject;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.SynchronousQueue;

public class RememberingQuery extends UnicastRemoteObject implements RerunnableQuery {

    @Inject private AllEvents allEvents;
    @Inject private Plugin plugin;

    private final ApiSerializables.Query query;

    public RememberingQuery(ApiSerializables.Query query) throws RemoteException {
        this.query = query;
    }

    @Override
    public ApiSerializables.Events runQuery() throws RemoteException {
        final SynchronousQueue<ApiSerializables.Events> result = new SynchronousQueue<ApiSerializables.Events>();
        new BukkitRunnable() {
            @Override
            public void run() {
                ApiSerializables.Events eventsInGameThread = allEvents.getEvents(query);
                try {
                    result.put(eventsInGameThread);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTask(plugin);
        try {
            return result.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
