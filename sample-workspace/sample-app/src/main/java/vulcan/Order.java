package vulcan;

import java.util.List;

public class Order {

    private List<Product> productXs;

    public List<Product> getProducts() {
        return productXs;
    }

    public void setProducts(List<Product> products) {
        this.productXs = products;
    }

}
