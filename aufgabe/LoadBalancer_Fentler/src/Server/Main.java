package Server;

import JavaLogger.JLogger;
import JavaLogger.Logger;
import Modules.Helper;
import Proxy.LoadBalancer;
import Modules.Statics;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.AccessControlException;

public class Main {
    private static Logger<String> _logger = JLogger.Instance;


    public static void main(String args[]) throws RemoteException {
        int port = Statics.PORT; // Load Balancer Port
        String ip = "127.0.0.1"; // Load Balancer IP
        String name = Statics.LOAD_BALANCER + "1";

        Helper.SecurityManagerCheck();
        Helper.HostnameCheck();

        try {
            Processor processor = new Server(name, port);
            _logger.Log(Logger.Severity.Info, "Server successfully exported!");

            _logger.Log(Logger.Severity.Info, "Looking up load balancer..");
            Registry registry = Helper.OpenRegistry(ip, port);

            LoadBalancer balancer = (LoadBalancer) registry.lookup(name);
            balancer.add(processor);
            _logger.Log(Logger.Severity.Info, "Processor successfully registered!");
        } catch (NotBoundException e) {
            _logger.Log(e);
        }
    }
}
