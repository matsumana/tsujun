package info.matsumana.tsujun.model.ksql;

import java.util.Objects;

public class KsqlRequest {

    private String ksql;

    public KsqlRequest(String ksql) {
        this.ksql = ksql;
    }

    public String getKsql() {
        return ksql;
    }

    public void setKsql(String ksql) {
        this.ksql = ksql;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        KsqlRequest that = (KsqlRequest) o;
        return Objects.equals(ksql, that.ksql);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ksql);
    }

    @Override
    public String toString() {
        return "KsqlRequest{" +
               "ksql='" + ksql + '\'' +
               '}';
    }
}
