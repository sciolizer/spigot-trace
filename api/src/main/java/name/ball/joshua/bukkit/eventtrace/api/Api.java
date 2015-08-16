package name.ball.joshua.bukkit.eventtrace.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Api extends Remote {

    // todo: return a reference instead of a Serializable, so that we can take advantage of distributed garbage collection
    RerunnableQuery getEvents(ApiSerializables.Query query) throws RemoteException;

}
