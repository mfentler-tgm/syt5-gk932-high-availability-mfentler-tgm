package Modules;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Task<T> {
    T run();
    int getWeight();
}