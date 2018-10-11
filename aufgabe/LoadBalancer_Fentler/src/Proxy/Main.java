package Proxy;

import JavaLogger.JLogger;
import JavaLogger.Logger;
import Modules.Helper;
import Modules.Statics;
import Server.Server;

public class Main {
    private static final int PORT = 1099;
    private static Logger<String> _logger = JLogger.Instance;


    private static void ShutdownHook(LoadBalancer balancer) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                balancer.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
                _logger.Log(e);
            }
        }));
    }

    public static void main(String[] args) {
        Helper.SecurityManagerCheck();
        Helper.HostnameCheck();
        int port = PORT;
        String name = Statics.LOAD_BALANCER;

        try {
            LoadBalancer balancer = new Proxy(name, port);
            _logger.Log(Logger.Severity.Info,
                    "Balancer exported to registry.");

            for (int i = 0; i < 4; i++) {
                _logger.Log(Logger.Severity.Debug, "Creating Processor #" + i);
                int range = (10000 - 1000) + 1;
                int weight =  (int)(Math.random() * range) + 500;
                Server newServer = new Server("Server" + i, port);
                newServer.setWeight(weight);
                balancer.add(newServer);
            }

            ShutdownHook(balancer);
            _logger.Log(Logger.Severity.Info,
                    "Round robin load balancer successfully bound.");
        } catch (Exception e) {
            e.printStackTrace();
            _logger.Log(e);
        }
    }
}
