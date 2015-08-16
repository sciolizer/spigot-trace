package name.ball.joshua.bukkit.eventtrace.monitor;

import name.ball.joshua.bukkit.eventtrace.api.Api;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Monitor {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        Api api = (Api) Naming.lookup("//localhost/" + Api.RMI_NAME);
        Api.Query query = new Api.Query();
        query.filter = new Api.Filter();
        query.projection = new Api.Projection();
        query.projection.klass = true;
        Api.Events events = api.getEvents(query);
        System.out.println(events);
    }
}
