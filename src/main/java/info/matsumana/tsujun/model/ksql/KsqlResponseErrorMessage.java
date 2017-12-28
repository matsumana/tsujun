package info.matsumana.tsujun.model.ksql;

import java.util.Objects;

public class KsqlResponseErrorMessage {

    private String message;
    private String stackTrace;

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
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        KsqlResponseErrorMessage that = (KsqlResponseErrorMessage) o;
        return Objects.equals(message, that.message) &&
               Objects.equals(stackTrace, that.stackTrace);
    }

    @Override
    public int hashCode() {

        return Objects.hash(message, stackTrace);
    }

    @Override
    public String toString() {
        return "KsqlResponseErrorMessage{" +
               "message='" + message + '\'' +
               ", stackTrace='" + stackTrace + '\'' +
               '}';
    }
}
