package info.matsumana.tsujun.model;

import java.util.Objects;

// TODO lombok doesn't support Java9 yet.
// https://github.com/rzwitserloot/lombok/issues/985
public class Request {

    private int sequence;
    private String sql;

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
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
