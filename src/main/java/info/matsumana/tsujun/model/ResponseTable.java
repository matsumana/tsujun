package info.matsumana.tsujun.model;

public class ResponseTable extends ResponseBase {

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseTable{" +
               "data=" + data +
               '}';
    }
}
