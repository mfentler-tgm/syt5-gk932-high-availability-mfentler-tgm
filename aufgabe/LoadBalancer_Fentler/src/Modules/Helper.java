package Modules;

import JavaLogger.JLogger;
import JavaLogger.Logger;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.AccessControlException;

public class Helper {
    private static Logger<String> _logger = JLogger.Instance;

    public static Registry OpenRegistry(String ip, int port) throws RemoteException {
        try {
            return LocateRegistry.getRegistry(ip, port);
        } catch (RemoteException e) {
            _logger.Log(Logger.Severity.Info, "No Registry found, " +
                    "creating new one..");
            return LocateRegistry.createRegistry(port);
        }
    }

    public static Registry OpenRegistry(int port) throws RemoteException {
        try {
            return LocateRegistry.getRegistry(port);
        } catch (RemoteException e) {
            _logger.Log(Logger.Severity.Info, "No Registry found, " +
                    "creating new one..");
            return LocateRegistry.createRegistry(port);
        }
    }

    public static void SecurityManagerCheck() {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            _logger.Log(Logger.Severity.Info, "No Security Manager available, " +
                    "created a new one.");
        }
    }

    public static void HostnameCheck() {
        try {
            String hostname = System.getProperties().getProperty("java.rmi.server.hostname");
            _logger.Log(Logger.Severity.Debug, "RMI running on hostname: " + hostname);
        } catch (AccessControlException ex) {
            _logger.Log(Logger.Severity.Error, "Access denied - Could not read java.rmi.server.hostname!");
        }
    }
}
