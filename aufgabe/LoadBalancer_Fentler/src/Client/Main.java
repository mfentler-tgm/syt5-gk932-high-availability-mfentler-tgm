package Client;

import JavaLogger.JLogger;
import JavaLogger.Logger;
import Modules.Fibonacci;
import Modules.Pi;
import Modules.Statics;
import Modules.Task;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Main {
    enum TaskType {
        Pi,
        Fibonacci
    }

    private static Arguments BuildArguments(String[] args) {
        try {
            Arguments arguments = new Arguments();
            arguments.host = args[0];
            arguments.type = TaskType.valueOf(args[1]);
            arguments.parameter = Integer.parseInt(args[2]);
            JLogger.Instance.Log(Logger.Severity.Debug,
                    "Host: " + arguments.host +
                            " | Type: " + arguments.type +
                            " | Parameter: " + arguments.parameter);
            return arguments;
        } catch (Exception e) {
            JLogger.Instance.Log(Logger.Severity.Error,
                    "Invalid program args format!");
            JLogger.Instance.Log(e);
            throw new IllegalArgumentException("Invalid program args!");
        }
    }

    private static void RunTask(Client client, TaskType type, int parameter)
            throws RemoteException {
        switch (type) {
            case Fibonacci:
                BigInteger fib = client.run(new Fibonacci(parameter));
                JLogger.Instance.Log(Logger.Severity.Info,
                        "Calculated Fibonacci: " + fib);
                break;
            case Pi:
                BigDecimal pi = client.run(new Pi(parameter));
                JLogger.Instance.Log(Logger.Severity.Info,
                        "Calculated Pi: " + pi);
                break;
        }
    }

    public static void main(String[] args) throws NotBoundException {
        Logger<String> logger = JLogger.Instance;
        Arguments arguments = BuildArguments(args);

        try {
            Client client = new Client(arguments.host, Statics.LOAD_BALANCER);
            RunTask(client, arguments.type, arguments.parameter);
        } catch (RemoteException e) {
            logger.Log(Logger.Severity.Error, "Could not get registry!");
            e.printStackTrace();
            throw new IllegalArgumentException("Hostname (arg[0]) invalid!");
        } catch (NotBoundException e) {
            logger.Log(Logger.Severity.Error, "Stub is not bound to Registry!");
            e.printStackTrace();
            throw e;
        }
    }
}


class Arguments {
    public String host = "localhost";
    public Main.TaskType type = Main.TaskType.Fibonacci;
    public int parameter = 1099;
}

