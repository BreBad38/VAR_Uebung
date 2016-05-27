package client;

import java.math.BigDecimal;
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
        long wiederholungen = 1000000;
        BigDecimal pi = new BigDecimal(0);
        long[] tropfenImKreis = new long[args.length];
        long summe = 0;
        int index = 0;

        try {
            for (int i = 0; i < 5; i++) {
                if (index < args.length - 1) {
                    index++;
                } else {
                    index = 0;
                }
                server = (MonteCarloServer) Naming.lookup("//" + args[index] + "/ComputePi");
                tropfenImKreis[index] = server.berechnePi(wiederholungen);
            }
            for (long l : tropfenImKreis) {
                summe += l;
            }
            summe = summe / args.length;
            pi = new BigDecimal(4 * (double) summe / wiederholungen);
        } catch (Exception e) {
            System.out.println("Hoppla, da ist etwas schiefgelaufen...");
            e.printStackTrace();
        }

        System.out.println("Die finale Annäherung an Pi lautet:");
        System.out.println(pi);
    }
}