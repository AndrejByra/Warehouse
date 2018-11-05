package JavaFx;

public class TableModel {

    private String buyer,product,country;
    private int pieces;

    public TableModel(String product,String buyer,int pieces,String country) {
        this.product = product;
        this.buyer = buyer;
        this.pieces = pieces;
        this.country = country;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getProduct() {
        return product;
    }

    public String getCountry() {
        return country;
    }

    public int getPieces() {
        return pieces;
    }
}
