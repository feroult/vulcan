package vulcan;

import java.util.List;

public class Order {

    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void simpleMethod() {
        System.out.println(new Order());
    }

    public void callProductMethods() {

        int renameVar = 0;

        for (Product product : products) {
            product.renameMethodTest();
        }
    }

    public int anotherMethod() {
        int renameVar = 1;
        return renameVar;
    }
}
