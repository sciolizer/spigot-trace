package name.ball.joshua.bukkit.eventtrace.monitor;

import name.ball.joshua.bukkit.eventtrace.api.Api;
import name.ball.joshua.bukkit.eventtrace.api.ApiSerializables;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collections;

public class Monitor {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        // java -Djava.rmi.server.codebase=file:/Users/joshball/spigot-all/josh/plugins/event-trace-plugin-1.0.jar -Xmx1024M -XX:MaxPermSize=128M -jar $JAR -o true
        // Note that the java.rmi.server.codebase property needs to be defined in order for the class to be found.
        Remote remote = Naming.lookup("//localhost/" + ApiSerializables.RMI_NAME);
        Api api = (Api) remote;
        ApiSerializables.Query query = new ApiSerializables.Query();
        query.filter = new ApiSerializables.Filter();
        query.projection = new ApiSerializables.Projection();
        query.projection.klass = true;
        query.projection.properties = Collections.emptyList();
        ApiSerializables.Events events = api.getEvents(query);
        System.out.println(events);
    }
}
