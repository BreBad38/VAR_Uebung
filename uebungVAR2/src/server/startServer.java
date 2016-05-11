package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class startServer {

    public static void main(final String[] args) {
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        } catch (Exception e) {
            System.out.println("Could not create RMIRegistry");
        }
        // Server Startup
        String name = args[0];
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