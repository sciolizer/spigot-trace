package name.ball.joshua.bukkit.eventtrace.monitor;

import name.ball.joshua.bukkit.eventtrace.api.Api;
import name.ball.joshua.bukkit.eventtrace.api.ApiSerializables;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Monitor {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        Remote remote = Naming.lookup("//localhost/" + ApiSerializables.RMI_NAME);
        Api api = (Api) remote;
        ApiSerializables.Query query = new ApiSerializables.Query();
        query.filter = new ApiSerializables.Filter();
        query.projection = new ApiSerializables.Projection();
        query.projection.klass = true;
        ApiSerializables.Events events = api.getEvents(query);
        System.out.println(events);
    }
}
