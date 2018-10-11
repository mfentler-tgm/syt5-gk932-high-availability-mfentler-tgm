package JavaLogger;

public interface Logger<T> {
    void Log(Severity severity, T message);
    void Log(Exception exception);

    public enum Severity {
        Debug,
        Info,
        Warning,
        Error
    }
}


