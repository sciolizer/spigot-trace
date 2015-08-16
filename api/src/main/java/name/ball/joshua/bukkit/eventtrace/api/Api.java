package name.ball.joshua.bukkit.eventtrace.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Api extends Remote { // maybe just do ordinary object serialization?

    // todo: return a reference instead of a Serializable, so that we can take advantage of distributed garbage collection
    ApiSerializables.Events getEvents(ApiSerializables.Query query) throws RemoteException;

}
