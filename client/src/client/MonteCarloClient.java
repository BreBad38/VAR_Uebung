package client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.Naming;
import java.util.Arrays;
import java.util.HashSet;

import server.MonteCarloServer;

public class MonteCarloClient {

    // Die Start-Parameter müssen wie folgt gesetzt werden:
    // 1. Parameter: Anzahl der Nachkommastellen
    // 2. - n. Parameter: Name/Adresse des Servers (String)
    public static void main(final String[] args) {

        // Der Security Manager wird konfiguriert und gestartet, falls noch nicht geschehen
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "policy\\rmi.policy");
            System.setSecurityManager(new SecurityManager());
            System.out.println("Der SecurityManager (Client) wurde konfiguriert.");
        } else {
            System.out.println("Der SecurityManager (Client) wurde nicht konfiguriert.");
        }

        MonteCarloServer server;

        // Anzahl der Zufallszahlen
        long anzahlZufallszahlen = 10000;

        // Anzahl der PI-Werte, die übereinstimmen sollen
        int anzahlPiVergleiche = 2;
        System.out.println("Anzahl der Übereinstimmungen (PI-Werte): " + anzahlPiVergleiche);

        // Anzahl der Durchläufe pro Zufallszahl
        int anzahlDurchlaeufe = 10;

        int anzahlNachkommastellen = 3;
        System.out.println("Genauigkeit (Anzahl der Nachkommastellen): " + anzahlNachkommastellen);

        String[] addresses = getAddresses(args);

        if (addresses != null) {
            try {
                for (int i = 0; i < anzahlDurchlaeufe; i++) {

                    BigDecimal[] piArray = new BigDecimal[anzahlPiVergleiche];

                    System.out.println("Zufallszahlen: " + anzahlZufallszahlen);

                    for (int j = 0; j < piArray.length; j++) {
                        server = (MonteCarloServer) Naming.lookup("//" + addresses[0] + "/ComputePi");
                        long tropfenImKreis = server.berechneTropfenImKreis(anzahlZufallszahlen);
                        piArray[j] = berechnePI(tropfenImKreis, anzahlZufallszahlen, anzahlNachkommastellen);
                    }

                    if (checkPIWerteAufUebereinstimmung(piArray)) {
                        System.out.println("Ja hey Mario: Die finale Annäherung an Pi lautet: " + piArray[0]);
                        break;
                    } else if (i == anzahlDurchlaeufe - 1) {
                        // Erhöhung der Zufallszahlen um 300.000 nach dem X. Versuch
                        anzahlZufallszahlen += 300000;
                        // Reset der Duchläufe
                        i = -1;
                    }
                }
            } catch (Exception e) {
                System.out.println("Hoppla Mario, da ist etwas schiefgelaufen...");
                e.printStackTrace();
            }
        } else {
            System.out.println("Keine Pizzagrotte gefunden...");
        }
    }

    public static String[] getAddresses(final String[] args) {

        String[] addresses = new String[args.length];

        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = args[i];
        }

        return addresses;
    }

    public static boolean checkPIWerteAufUebereinstimmung(final BigDecimal[] pis) {
        // Duplikate werden entfernt
        if (new HashSet<>(Arrays.asList(pis)).size() == 1) {
            return true;
        }
        return false;
    }

    public static BigDecimal berechnePI(final long anzahlTropfenImKreis, final long anzahlZufallszahlen, final int anzahlNachkommstellen) {
        return new BigDecimal(4 * (double) anzahlTropfenImKreis / anzahlZufallszahlen).setScale(anzahlNachkommstellen, RoundingMode.FLOOR);
    }

}