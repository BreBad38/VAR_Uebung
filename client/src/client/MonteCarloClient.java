package client;

import java.math.BigDecimal;
import java.rmi.Naming;

import server.MonteCarloServer;

public class MonteCarloClient {

    // Die Start-Parameter m�ssen wie folgt gesetzt werden:
    // 1. - n. Parameter: Name/Adresse des Servers (String)
    public static void main(final String[] args) {
        // Anlegen und Konfigurieren des Security Managers, falls noch nicht geschehen
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "policy\\rmi.policy");
            System.setSecurityManager(new SecurityManager());
        }

        MonteCarloServer server;
        long wiederholungen = 50000000;
        BigDecimal[] piArray = new BigDecimal[25];
        long[] tropfenImKreis = new long[5];
        double summe = 0.0;

        int index = 0;

        try {
            for (int p = 0; p < piArray.length; p++) {

                System.out.println(p);

                summe = 0.0;
                for (int i = 0; i < tropfenImKreis.length; i++) {
                    if (index < args.length - 1) {
                        index++;
                    } else {
                        index = 0;
                    }
                    server = (MonteCarloServer) Naming.lookup("//" + args[index] + "/ComputePi");
                    tropfenImKreis[i] = server.berechnePi(wiederholungen);
                }
                for (long l : tropfenImKreis) {
                    summe += l;
                }
                summe = summe / tropfenImKreis.length;
                // System.out.println(summe);
                piArray[p] = new BigDecimal(4 * summe / wiederholungen);
            }
        } catch (Exception e) {
            System.out.println("Hoppla, da ist etwas schiefgelaufen...");
            e.printStackTrace();
        }

        System.out.println("Die finale Ann�herung an Pi lautet:");

        BigDecimal durchschnitt = new BigDecimal(0);
        for (int i = 0; i < piArray.length; i++) {
            durchschnitt = durchschnitt.add(piArray[i]);
        }
        durchschnitt = durchschnitt.divide(new BigDecimal(piArray.length));
        System.out.println(durchschnitt);

    }

}