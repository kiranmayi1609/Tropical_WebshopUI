package dk.tec.tropical_webshop.Cart;

import java.util.ArrayList;
import java.util.List;

import dk.tec.tropical_webshop.DataModel.Product;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> products;
    private static Cart instance;
    private static final String TAG = "Cart";

    private Cart() {
        products = new ArrayList<>();
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public void addProduct(Product product) {
        if (product != null) {
            products.add(product);
            Log.d(TAG, "Product added: " + product.getName());
        } else {
            // Log an error message
            Log.e(TAG, "Attempted to add a null product to the cart.");
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    public void clearCart() {
        products.clear();
    }
}
