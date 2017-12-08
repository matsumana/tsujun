package info.matsumana.tsujun.model;

public class Request {
    private int sequence;
    private String sql;

    // TODO lombok doesn't support Java9 yet.
    // https://github.com/rzwitserloot/lombok/issues/985

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
    public String toString() {
        return "Request{" +
               "sequence=" + sequence +
               ", sql='" + sql + '\'' +
               '}';
    }
}
