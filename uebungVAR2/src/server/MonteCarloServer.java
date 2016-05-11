package server;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MonteCarloServer extends Remote {
    public BigDecimal berechneZufallstropfen() throws RemoteException;
}
