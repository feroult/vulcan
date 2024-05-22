package vulcan;

public class Product {

    private int price = 100;

    public void extractMethodTest() {
        Product p = new Product();

        if (p != null) {
            System.out.println(p);
        }

        System.out.println("price is: " + price);
    }

    public void renameMethodTest() {
        System.out.println("something");
    }

    public void renameMethodTest(String arg) {
        System.out.println("arg is: " + arg);
    }
}
