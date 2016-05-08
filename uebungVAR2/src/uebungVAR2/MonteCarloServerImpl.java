package uebungVAR2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class MonteCarloServerImpl extends UnicastRemoteObject implements MonteCarloServer {

    MonteCarloServerImpl() throws RemoteException {
        super();
    }

    @Override
    public int berechneZufallstropfen(final int wiederholungen) throws RemoteException {
        int tropfenImKreis = 0;
        double x, y;
        for (int i = 1; i <= wiederholungen; i++) {
            x = Math.random();
            y = Math.random();
            if (Math.hypot(x, y) <= 1) {
                tropfenImKreis++;
            }
        }
        return tropfenImKreis;
    }
}
