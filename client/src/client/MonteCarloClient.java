package client;

import java.math.BigDecimal;
import java.rmi.Naming;

import server.MonteCarloServer;

public class MonteCarloClient {
    public static void main(final String[] args) {
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "policy\\rmi.policy");
            System.setSecurityManager(new SecurityManager());
        }
        MonteCarloServer server;
        BigDecimal[] piValues = new BigDecimal[args.length];
        try {
            for (int i = 0; i < args.length; i++) {
                server = (MonteCarloServer) Naming.lookup("//" + args[i] + "/ComputePi");
                piValues[i] = server.berechnePi();
            }
        } catch (Exception e) {
            System.out.println("Hoppla, da ist etwas schiefgelaufen...");
            e.printStackTrace();
        }
        BigDecimal pi = new BigDecimal(0);
        for (BigDecimal bigDecimal : piValues) {
            pi = pi.add(bigDecimal);
        }
        pi = pi.divide(new BigDecimal(piValues.length));
        System.out.println(pi);
    }
}