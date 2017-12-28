package info.matsumana.tsujun.model.ksql;

import java.util.Arrays;

public class KsqlResponseColumns {

    private Object[] columns;

    public Object[] getColumns() {
        return columns;
    }

    public void setColumns(Object[] columns) {
        this.columns = columns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        KsqlResponseColumns that = (KsqlResponseColumns) o;
        return Arrays.equals(columns, that.columns);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(columns);
    }

    @Override
    public String toString() {
        return "KsqlResponseColumns{" +
               "columns=" + Arrays.toString(columns) +
               '}';
    }
}
