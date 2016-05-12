package client;

import java.math.BigDecimal;
import java.rmi.Naming;

import server.MonteCarloServer;

public class MonteCarloClient {
    public static void main(final String[] args) {
        // Anlegen und Konfigurieren des Security Managers, falls noch nicht geschehen
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "policy\\rmi.policy");
            System.setSecurityManager(new SecurityManager());
        }

        MonteCarloServer server;
        BigDecimal[] piValues = new BigDecimal[args.length];
        try {
            // Jeder Server wird mit der Berechnung einer Pi-Ann�herung beauftragt
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
        // Die Pi-Ann�herungen werden aufaddiert...
        for (BigDecimal bigDecimal : piValues) {
            pi = pi.add(bigDecimal);
        }
        // ...und dann durch ihre Anzahl geteilt, um einen Mittelwert zu ermitteln
        pi = pi.divide(new BigDecimal(piValues.length));
        System.out.println("Die Ann�herung f�r Pi lautet:");
        System.out.println(pi);
    }
}