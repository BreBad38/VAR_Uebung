package client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.Naming;
import java.util.Arrays;
import java.util.HashSet;

import server.MonteCarloServer;

public class MonteCarloClient {

    // Die Start-Parameter m�ssen wie folgt gesetzt werden:
    // 1. Parameter: Anzahl der Nachkommastellen
    // 2. - n. Parameter: Name/Adresse des Servers (String)
    public static void main(final String[] args) {
        // Anlegen und Konfigurieren des Security Managers, falls noch nicht geschehen
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "policy\\rmi.policy");
            System.setSecurityManager(new SecurityManager());
            System.out.println("Der SecurityManager (Client) wurde konfiguriert.");
        } else {
            System.out.println("Der SecurityManager (Client) wurde nicht konfiguriert.");
        }

        int nachkommastellen = Integer.parseInt(args[0]);

        // Startwert f�r Wiederholungen
        long wiederholungen = 100000;
        // Gibt an, wie viele Pi-Werte verglichen werden und �bereinstimmen m�ssen
        int anzahlVergleiche = 3;
        // Pi-Array, dass mit Pi-Ann�herungen gef�llt wird
        BigDecimal[] piArray = new BigDecimal[anzahlVergleiche];

        long tropfenImKreis;

        boolean piGefunden = false;

        int index = 1;
        int zaehler = 0;
        MonteCarloServer server;

        try

        {
            // Wiederhole den Vorgang solange bis Pi gefunden wurde
            do {
                zaehler++;
                System.out.println("Wiederholungen: " + wiederholungen);

                // Die erste For-Schleife bef�llt das Pi-Array mit Pi-Ann�herungen, in dem es die gegebenen Server nacheinander anfragt
                for (int i = 0; i < piArray.length; i++) {
                    // Die Variable Index z�hlt unabh�ngig von der for-Schleife durch das Array, in dem die Server-Adressen gespeichert sind
                    if (index < args.length - 1) {
                        index++;
                    } else {
                        index = 1;
                    }

                    server = (MonteCarloServer) Naming.lookup("//" + args[index] + "/ComputePi");
                    // Die Methode des Servers wird gerufen
                    tropfenImKreis = server.berechneTropfenImKreis(wiederholungen);
                    // Aus dem R�ckgabewert wird eine Pi-Ann�herung berechnet
                    piArray[i] = new BigDecimal(4 * (double) tropfenImKreis / wiederholungen);
                    // Die Pi-Ann�herung wird auf x Nachkommastellen gek�rzt
                    piArray[i] = piArray[i].setScale(nachkommastellen, RoundingMode.FLOOR);
                }

                // Die Pi-Ann�herungen werden verglichen
                if (vergleichePIWerte(piArray)) {
                    // Stimmen alle Werte �berein, wird die while-Schleifen-Bedingung auf true gesetzt
                    piGefunden = true;
                } else {
                    // Ansonsten bleibt sie false
                    piGefunden = false;

                    // Die Anzahl der Wiederholungen soll alle 10 Durchl�ufe erh�ht werden
                    if (zaehler % 10 == 0) {
                        wiederholungen += 300000;
                    }
                }
            } while ((piGefunden == false));

        } catch (

        Exception e)

        {
            System.out.println("Hoppla, da ist etwas schiefgelaufen...");
            e.printStackTrace();
        }

        System.out.println("Die finale Ann�herung an Pi lautet:");
        System.out.println(piArray[0]);

    }

    public static boolean vergleichePIWerte(final BigDecimal[] piArray) {
        if (new HashSet<>(Arrays.asList(piArray)).size() == 1) {
            return true;
        }
        return false;
    }

}