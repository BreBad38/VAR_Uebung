package client;

import java.math.BigDecimal;
import java.rmi.Naming;

public class MonteCarloClient {
    public static void main(final String[] args) {
        MonteCarloServer server;
        BigDecimal[] piValues = new BigDecimal[args.length];
        try {
            for (int i = 0; i < args.length; i++) {
                server = (MonteCarloServer) Naming.lookup(args[i]);
                piValues[i] = server.berechneZufallstropfen();
            }
        } catch (Exception e) {
            System.out.println("Hoppla, da ist etwas schiefgelaufen...");
            e.printStackTrace();
        }
        BigDecimal pi = new BigDecimal(0);
        for (BigDecimal bigDecimal : piValues) {
            pi.add(bigDecimal);
        }
        pi.divide(new BigDecimal(piValues.length));
        System.out.println(pi);
    }
}