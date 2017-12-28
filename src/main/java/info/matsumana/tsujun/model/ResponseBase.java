package info.matsumana.tsujun.model;

import java.util.Objects;

public class ResponseBase {

    private int sequence;
    private String sql;
    private int mode;

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

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        ResponseBase that = (ResponseBase) o;
        return sequence == that.sequence &&
               mode == that.mode &&
               Objects.equals(sql, that.sql);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sequence, sql, mode);
    }

    @Override
    public String toString() {
        return "ResponseBase{" +
               "sequence=" + sequence +
               ", sql='" + sql + '\'' +
               ", mode=" + mode +
               '}';
    }
}
