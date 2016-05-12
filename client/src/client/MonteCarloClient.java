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
        BigDecimal[] piValues = new BigDecimal[args.length];
        try {
            // Jeder Server wird mit der Berechnung einer Pi-Annäherung beauftragt
            for (int i = 0; i < args.length; i++) {
                server = (MonteCarloServer) Naming.lookup("//" + args[i] + "/ComputePi");
                // Die Pi-Werte werden in ein Array geschrieben
                piValues[i] = server.berechnePi();
            }
        } catch (Exception e) {
            System.out.println("Hoppla, da ist etwas schiefgelaufen...");
            e.printStackTrace();
        }

        BigDecimal pi = new BigDecimal(0);
        // Die Pi-Annäherungen werden aufaddiert...
        for (BigDecimal bigDecimal : piValues) {
            pi = pi.add(bigDecimal);
        }
        // ...und dann durch ihre Anzahl geteilt, um einen Mittelwert zu ermitteln
        pi = pi.divide(new BigDecimal(piValues.length));
        System.out.println("Die Annäherung für Pi lautet:");
        System.out.println(pi);
    }
}