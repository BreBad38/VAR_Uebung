package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MonteCarloServer extends Remote {
    public long berechneTropfenImKreis(long wiederholungen) throws RemoteException;
}