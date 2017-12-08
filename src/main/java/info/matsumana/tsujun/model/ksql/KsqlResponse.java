package info.matsumana.tsujun.model.ksql;

public class KsqlResponse {

    Object row;
//    KsqlResponseErrorMessage errorMessage;

    public Object getRow() {
        return row;
    }

    public void setRow(Object row) {
        this.row = row;
    }

//    public KsqlResponseErrorMessage getErrorMessage() {
//        return errorMessage;
//    }
//
//    public void setErrorMessage(KsqlResponseErrorMessage errorMessage) {
//        this.errorMessage = errorMessage;
//    }
//
//    @Override
//    public String toString() {
//        return "KsqlResponse{" +
//               "row=" + row +
//               ", errorMessage=" + errorMessage +
//               '}';
//    }
}
