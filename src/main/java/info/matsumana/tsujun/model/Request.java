package info.matsumana.tsujun.model;

import java.util.Objects;

// TODO lombok doesn't support Java9 yet.
// https://github.com/rzwitserloot/lombok/issues/985
public class Request {

    private final int sequence;
    private final String sql;

    public Request(int sequence, String sql) {
        this.sequence = sequence;
        this.sql = sql;
    }

    public int getSequence() {
        return sequence;
    }

    public String getSql() {
        return sql;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Request request = (Request) o;
        return sequence == request.sequence &&
               Objects.equals(sql, request.sql);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sequence, sql);
    }

    @Override
    public String toString() {
        return "Request{" +
               "sequence=" + sequence +
               ", sql='" + sql + '\'' +
               '}';
    }
}
