package info.matsumana.tsujun.model.ksql;

import java.util.Objects;

public class KsqlResponse {

    private KsqlResponseColumns row;

    public KsqlResponseColumns getRow() {
        return row;
    }

    public void setRow(KsqlResponseColumns row) {
        this.row = row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        KsqlResponse response = (KsqlResponse) o;
        return Objects.equals(row, response.row);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row);
    }

    @Override
    public String toString() {
        return "KsqlResponse{" +
               "row=" + row +
               '}';
    }
}
