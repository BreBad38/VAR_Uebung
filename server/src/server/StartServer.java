package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {

    // Die Start-Parameter müssen wie folgt gesetzt werden:
    // 1. Parameter: Name/Adresse des Servers (String)
    public static void main(final String[] args) {

        // Der Security Manager wird konfiguriert und gestartet, falls noch nicht geschehen
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "policy/rmi.policy");
            System.setSecurityManager(new SecurityManager());
            System.out.println("Der SecurityManager (Server) wurde konfiguriert.");
        } else {
            System.out.println("Der SecurityManager (Server) wurde nicht konfiguriert.");
        }

        // Erstellen der Registry
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            System.out.println("Die Registry wurde erfolgreich erstellt.");
        } catch (Exception e) {
            System.out.println("Die Registry konnte nicht erstellt werden...");
            e.printStackTrace();
        }

        // Jeder Server erhält einen Namen und eine Angabe für die Genauigkeit
        String name = "//" + args[0] + "/ComputePi";
        try {
            Naming.rebind(name, new MonteCarloServerImpl());
            System.out.println("Der MonteCarloServer an der Adresse " + args[0] + " wurde gestartet.");
        } catch (Exception e) {
            System.err.println("Server Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

}