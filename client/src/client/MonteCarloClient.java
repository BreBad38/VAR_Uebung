package client;

import java.math.BigDecimal;
import java.net.URL;
import java.rmi.Naming;

import server.MonteCarloServer;

public class MonteCarloClient {
    // Die Start-Parameter müssen wie folgt gesetzt werden:
    // 1. - n. Parameter: Name/Adresse des Servers (String)
    public static void main(final String[] args) {
        // Anlegen und Konfigurieren des Security Managers, falls noch nicht geschehen
        if (System.getSecurityManager() == null) {
            URL url = MonteCarloClient.class.getClassLoader().getResource("rmi.policy");
            System.setProperty("java.security.policy", url.getPath());
            System.setSecurityManager(new SecurityManager());
        }

        MonteCarloServer server;
        long wiederholungen = 100000;
        int nachkommastellen = 4;
        int identifizierteZahlen = 0;
        int index = 0;
        BigDecimal[] pi = new BigDecimal[2];
        BigDecimal delta;
        try {
            while (identifizierteZahlen < nachkommastellen + 1) {
                for (int i = 0; i < 2; i++) {
                    if (index < args.length - 1) {
                        index++;
                    } else {
                        index = 0;
                    }
                    server = (MonteCarloServer) Naming.lookup("//" + args[index] + "/ComputePi");
                    pi[i] = new BigDecimal(4 * (double) server.berechnePi(wiederholungen) / wiederholungen);
                }
                delta = pi[0].subtract(pi[1]);
                identifizierteZahlen = delta.scale() - delta.precision() + 1;
                // To be continued...
                // Folgendes muss jetzt passieren:
                // Angenommen wir konnten 5 Zahlen identifizieren, können wir nun die ersten 5 Ziffern von pi[0] (oder pi[1]) in unser Endergebnis
                // übernehmen
            }
        } catch (Exception e) {
            System.out.println("Hoppla, da ist etwas schiefgelaufen...");
            e.printStackTrace();
        }

        System.out.println("\nDie finale Annäherung an Pi lautet:");
        System.out.println();
    }
}