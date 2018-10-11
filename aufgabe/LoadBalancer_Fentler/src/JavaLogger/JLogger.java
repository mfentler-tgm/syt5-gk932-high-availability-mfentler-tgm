package JavaLogger;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JLogger implements Logger<String> {
    public static JLogger Instance = new JLogger(System.out);

    private PrintStream stream;

    public JLogger(PrintStream stream) {
        if (stream == null){
            this.stream = System.out;
        } else{
            this.stream = stream;
        }
    }

    @Override
    public void Log(Severity severity, String message) {
        LocalDateTime now = LocalDateTime.now();

        String buffer = String.format("[%s] [%s]: %s", now.toString(), severity.toString(), message);
        stream.println(buffer);
    }

    @Override
    public void Log(Exception exception) {
        LocalDateTime now = LocalDateTime.now();

        String buffer = String.format("[%s] [%s]: %s", now.toString(), Severity.Error.toString(), exception.getMessage());
        stream.println(buffer);
    }
}
