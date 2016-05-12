package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {

    // Die Start-Parameter müssen wie folgt gesetzt werden:
    // 1. Parameter: Name/Adresse des Servers (String)
    // 2. Parameter: Genauigkeit (Int)
    // 3. Parameter (optional): Port der Registry
    public static void main(final String[] args) {
        // Der Security Manager wird konfiguriert und gestartet, falls noch nicht geschehen
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "policy\\rmi.policy");
            System.setSecurityManager(new SecurityManager());
        }

        // Erstellen der Registry
        try {
            int port;
            // Hat der Nutzer keinen Registry Port angegeben wird der Standard-Port verwendet
            if (args.length != 3) {
                port = Registry.REGISTRY_PORT;
            } else {
                port = Integer.parseInt(args[2]);
            }
            LocateRegistry.createRegistry(port);
        } catch (Exception e) {
            System.out.println("Could not create RMIRegistry");
            e.printStackTrace();
        }

        // Jeder Server erhält einen Namen und eine Angabe für die Genauigkeit
        String name = "//" + args[0] + "/ComputePi";
        long genauigkeit = Long.parseLong(args[1]);
        try {
            Naming.rebind(name, new MonteCarloServerImpl(genauigkeit));
            System.out.println("Server (re)bound");
        } catch (Exception e) {
            System.err.println("Server Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}