package info.matsumana.tsujun.model;

import java.util.Arrays;

public class ResponseTable extends ResponseBase {

    private Object[] data;

    public Object[] getData() {
        return data;
    }

    public void setData(Object[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        if (!super.equals(o)) { return false; }
        ResponseTable that = (ResponseTable) o;
        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "ResponseTable{" +
               "data=" + Arrays.toString(data) +
               '}';
    }
}
