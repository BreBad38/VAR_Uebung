package uebungVAR2;

import java.rmi.Naming;

public class MonteCarloClient {
    public static void main(final String[] args) {
        // Server Startup
        String name = "//localhost/ComputePi";
        try {
            Naming.rebind(name, new MonteCarloServerImpl());
            System.out.println("Server (re)bound");
        } catch (Exception e) {
            System.err.println("Server Exception: " + e.getMessage());
            e.printStackTrace();
        }

        // Client Startup
        int gesamtZahl = 100000;
        int tropfenImKreis = 0;
        try {
            MonteCarloServer server = (MonteCarloServer) Naming.lookup(name);
            tropfenImKreis += server.berechneZufallstropfen(gesamtZahl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        double pi = 4 * (double) tropfenImKreis / gesamtZahl;
        System.out.println(gesamtZahl + " Tropfen, davon " + tropfenImKreis + " Tropfen im Viertelkreis, Pi etwa " + pi);
    }
}