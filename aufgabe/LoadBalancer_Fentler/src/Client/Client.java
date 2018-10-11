package Client;

import Modules.Compute;
import Modules.Task;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*
 * A Client object for running Tasks on the Server
 *
 * @author  Marc Rousavy
 * @version 1.0
 */
public class Client {
    private Registry _registry;
    private Compute _compute;

    /*
     * Create a new Client and initialize the Registry
     *
     * @param host the given host name to get the registry from the Server
     * @param stubName The name of the compute stub (Server) in the registry
     * @author  Marc Rousavy
     * @version 1.0
     */
    public Client(String host, String stubName) throws RemoteException, NotBoundException {
        _registry = LocateRegistry.getRegistry(host);
        _compute = (Compute)_registry.lookup(stubName);
    }

    /*
     * Run a specific Task (stub) on the server
     *
     * @param stubName The name of the stub in the registry
     * @author  Marc Rousavy
     * @version 1.0
     */
    public <T> T run(Task<T> task) throws RemoteException {
        return _compute.run(task);
    }
}
