package name.ball.joshua.bukkit.eventtrace.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RerunnableQuery extends Remote {
    ApiSerializables.Events runQuery() throws RemoteException;
}
