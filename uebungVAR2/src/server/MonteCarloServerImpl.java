package server;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.MonteCarloServer;

@SuppressWarnings("serial")
public class MonteCarloServerImpl extends UnicastRemoteObject implements MonteCarloServer {

    int genauigkeit;

    MonteCarloServerImpl(final int genauigkeit) throws RemoteException {
        super();
        this.genauigkeit = genauigkeit;
    }

    @Override
    public BigDecimal berechneZufallstropfen() throws RemoteException {
        int tropfenImKreis = 0;
        double x, y;
        for (int i = 1; i <= genauigkeit; i++) {
            x = Math.random();
            y = Math.random();
            if (Math.hypot(x, y) <= 1) {
                tropfenImKreis++;
            }
        }
        return new BigDecimal(4 * (double) tropfenImKreis / genauigkeit);
    }
}
