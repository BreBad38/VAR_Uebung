package uebungVAR2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MonteCarloServer extends Remote {
    public int berechneZufallstropfen(int wiederholungen) throws RemoteException;
}
