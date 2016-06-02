package client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.Naming;

import server.MonteCarloServer;

public class MonteCarloClient {

    // Die Start-Parameter müssen wie folgt gesetzt werden:
    // 1. - n. Parameter: Name/Adresse des Servers (String)
    public static void main(final String[] args) {
        // Anlegen und Konfigurieren des Security Managers, falls noch nicht geschehen
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "policy\\rmi.policy");
            System.setSecurityManager(new SecurityManager());
        }

        MonteCarloServer server;
        long wiederholungen = 100000;
        int nachkommastellen = 3;
        int anzahlVergleiche = 3;
        BigDecimal[] piArray = new BigDecimal[anzahlVergleiche];
        long[] tropfenImKreis = new long[anzahlVergleiche];

        boolean nichtFertig = true;

        int index = 0;
        int zaehler = 0;

        try {
            while (nichtFertig) {
                zaehler++;
                System.out.println("Wiederholungen: " + wiederholungen);
                for (int i = 0; i < tropfenImKreis.length; i++) {
                    if (index < args.length - 1) {
                        index++;
                    } else {
                        index = 0;
                    }
                    server = (MonteCarloServer) Naming.lookup("//" + args[index] + "/ComputePi");
                    tropfenImKreis[i] = server.berechneTropfenImKreis(wiederholungen);
                }
                for (int i = 0; i < piArray.length; i++) {
                    piArray[i] = new BigDecimal(4 * (double) tropfenImKreis[i] / wiederholungen);
                    piArray[i] = piArray[i].setScale(nachkommastellen, RoundingMode.FLOOR);
                }
                for (int i = 0; i < piArray.length - 1; i++) {
                    if (piArray[i].equals(piArray[i + 1]) == false) {
                        if (zaehler % 10 == 0) {
                            wiederholungen += 300000;
                        }
                        nichtFertig = true;
                        break;
                    } else {
                        nichtFertig = false;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Hoppla, da ist etwas schiefgelaufen...");
            e.printStackTrace();
        }

        System.out.println("Die finale Annäherung an Pi lautet:");
        System.out.println(piArray[0]);

    }

}