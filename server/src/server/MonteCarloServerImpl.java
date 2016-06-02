package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class MonteCarloServerImpl extends UnicastRemoteObject implements MonteCarloServer {

    MonteCarloServerImpl() throws RemoteException {
        super();
    }

    @Override
    public long berechneTropfenImKreis(final long anzahlZufallszahlen) throws RemoteException {
        long tropfenImKreis = 0;
        double x, y;
        for (int i = 1; i <= anzahlZufallszahlen; i++) {
            x = Math.random();
            y = Math.random();
            if (Math.hypot(x, y) <= 1) {
                tropfenImKreis++;
            }
        }
        return tropfenImKreis;
    }
}