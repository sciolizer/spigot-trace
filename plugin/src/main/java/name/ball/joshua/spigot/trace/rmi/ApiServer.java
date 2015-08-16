package name.ball.joshua.spigot.trace.rmi;

import name.ball.joshua.bukkit.eventtrace.api.Api;
import name.ball.joshua.bukkit.eventtrace.api.ApiSerializables;
import name.ball.joshua.bukkit.eventtrace.api.RerunnableQuery;
import name.ball.joshua.spigot.trace.AllEvents;
import name.ball.joshua.spigot.trace.di.InitializingBean;
import name.ball.joshua.spigot.trace.di.Inject;
import org.bukkit.plugin.Plugin;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ApiServer extends UnicastRemoteObject implements Api, InitializingBean {

    @Inject private Plugin plugin;
    @Inject private AllEvents allEvents;
    @Inject private RememberingQueryFactory rememberingQueryFactory;

    public ApiServer() throws RemoteException {
        super(0);    // required to avoid the 'rmic' step, see below
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        bind(this); // todo find a better way
    }

    @Override
    public RerunnableQuery getEvents(final ApiSerializables.Query query) {
        return rememberingQueryFactory.newRememberingQuery(query);
    }

    public static void bind(ApiServer server) throws Exception {
        if (System.getSecurityManager() == null) {
            SecurityManager securityManager = new PermissiveSecurityManager();
            System.setSecurityManager(securityManager);
            System.out.println("Created and set my own security manager");
        } else {
            System.out.println("There is already a security manager");
        }
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
        Naming.rebind("//localhost/" + ApiSerializables.RMI_NAME, server);
        System.out.println("PeerServer bound in registry");
    }

}
