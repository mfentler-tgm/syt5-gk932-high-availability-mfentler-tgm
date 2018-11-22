package Server;

import JavaLogger.JLogger;
import JavaLogger.Logger;
import Modules.Compute;
import Modules.Helper;
import Modules.Task;
import Proxy.LoadBalancer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class Server implements Processor {
    private static final long serialVersionUID = 42L;

    private Compute _stub;
    private Registry _registry;
    private LoadBalancer _balancer;
    private String _name;
    private int weight;
    private String taskName;

    public Server(String name, int port)
            throws RemoteException {
        _name = name;

        _stub = (Compute) UnicastRemoteObject.exportObject(this, port); //8001
        _registry = Helper.OpenRegistry(port);
        _registry.rebind(name, _stub);
    }

    /**
     * Die Methode setzt die Weight des Servers bei einer neuen Berechnung - der Weight vom Task.
     * Wenn die Berechnung fertig ist, erhöht sie ihn wieder.
     *
     * @param task
     * @param <T>
     * @return
     * @throws RemoteException
     * @Author: Mario Fentler
     */
    @Override
    public <T> T run(Task<T> task) throws RemoteException {
        try{
            setWeight(getWeight() - task.getWeight());
            T result = task.run();
            JLogger.Instance.Log(Logger.Severity.Info,
                    _name + ": Executed Task \"" + task.toString() +
                            "\", at " + new Date().toString());
            return result;
        } finally {
            setWeight(getWeight() + task.getWeight());
        }
    }

    @Override
    public void shutdown() throws RemoteException {
        try {
            _registry.unbind(_name);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } finally {
            UnicastRemoteObject.unexportObject(this, true);
        }
    }

    @Override
    public String toString() {
        return _name + " (" + Integer.toHexString(this.hashCode()) + ")";
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }


}
