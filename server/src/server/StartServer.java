package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {

    public static void main(final String[] args) {
        // Der Security Manager wird konfiguriert und gestartet, falls noch nicht geschehen
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "policy\\rmi.policy");
            System.setSecurityManager(new SecurityManager());
        }

        // Erstellen der Registry
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        } catch (Exception e) {
            System.out.println("Could not create RMIRegistry");
            e.printStackTrace();
        }

        // Jeder Server erhält einen Namen und eine Angabe für die Genauigkeit
        String name = "//" + args[0] + "/ComputePi";
        int genauigkeit = Integer.parseInt(args[1]);
        try {
            Naming.rebind(name, new MonteCarloServerImpl(genauigkeit));
            System.out.println("Server (re)bound");
        } catch (Exception e) {
            System.err.println("Server Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}