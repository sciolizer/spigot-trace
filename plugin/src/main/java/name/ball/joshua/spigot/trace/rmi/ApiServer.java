package name.ball.joshua.spigot.trace.rmi;

import name.ball.joshua.bukkit.eventtrace.api.Api;
import name.ball.joshua.spigot.trace.AllEvents;
import name.ball.joshua.spigot.trace.di.Inject;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.SynchronousQueue;

public class ApiServer extends UnicastRemoteObject implements Api {

    @Inject private Plugin plugin;
    @Inject private AllEvents allEvents;

    public ApiServer() throws RemoteException {
        super(0);    // required to avoid the 'rmic' step, see below
    }

    @Override
    public Events getEvents(final Query query) {
        final SynchronousQueue<Events> result = new SynchronousQueue<Events>();
        new BukkitRunnable() {
            @Override
            public void run() {
                Events eventsInGameThread = allEvents.getEvents(query);
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

    public static void bind(ApiServer server) throws Exception {
        System.out.println("RMI server started");

        try { //special exception handler for registry creation
            LocateRegistry.createRegistry(1099);
            System.out.println("java RMI registry created."); // todo: switch over to using logging
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }

        //Instantiate RmiServer

        // Bind this object instance to the name "RmiServer"
        Naming.rebind("//localhost/" + Api.RMI_NAME, server);
        System.out.println("PeerServer bound in registry");
    }
}
