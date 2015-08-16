package name.ball.joshua.spigot.trace.rmi;

import java.rmi.Naming;

public class RmiClient {
    public static void main(String args[]) throws Exception {
        RmiServerIntf obj = (RmiServerIntf)Naming.lookup("//localhost/RmiServer");
        System.out.println(obj.getMessage());
    }
}
