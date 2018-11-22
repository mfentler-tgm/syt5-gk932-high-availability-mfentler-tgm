package Server;

import Modules.Compute;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Processor extends Compute {
    int getWeight() throws RemoteException;

    void setWeight(int weight) throws RemoteException;

}
