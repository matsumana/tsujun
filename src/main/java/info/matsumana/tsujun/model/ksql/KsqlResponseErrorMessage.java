package info.matsumana.tsujun.model.ksql;

public class KsqlResponseErrorMessage {
    String message;
    String stackTrace;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    @Override
    public String toString() {
        return "KsqlResponseErrorMessage{" +
               "message='" + message + '\'' +
               ", stackTrace='" + stackTrace + '\'' +
               '}';
    }
}
