package Proxy;

import Modules.Compute;
import Server.Processor;

import java.rmi.RemoteException;

/**
 * A Proxy for balancing CPU load on multiple Servers
 */
public interface LoadBalancer extends Compute {
    void add(Processor processor) throws RemoteException;
    boolean remove(Processor processor) throws RemoteException;
}
