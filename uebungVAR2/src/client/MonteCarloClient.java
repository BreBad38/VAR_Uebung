package client;

import java.rmi.Naming;

public class MonteCarloClient {
    public static void main(final String[] args) {
        int gesamtZahl = 100000;
        int tropfenImKreis = 0;
        MonteCarloServer server;
        try {
            for (String name : args) {
                server = (MonteCarloServer) Naming.lookup(name);
                tropfenImKreis += server.berechneZufallstropfen(gesamtZahl);
            }
        } catch (Exception e) {
            System.out.println("Hoppla, da ist etwas schiefgelaufen...");
            e.printStackTrace();
        }

        double pi = 4 * (double) tropfenImKreis / gesamtZahl;
        System.out.println(gesamtZahl + " Tropfen, davon " + tropfenImKreis + " Tropfen im Viertelkreis, Pi etwa " + pi);
    }
}