package dk.tec.tropical_webshop.Product;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import dk.tec.tropical_webshop.Cart.Cart;
import dk.tec.tropical_webshop.DataModel.Product;
import dk.tec.tropical_webshop.R;

public class ProductDetail extends AppCompatActivity {
    private Product product;
    private Cart cart;
    private static final String TAG = "ProductDetail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        // Retrieve the product from the intent
        product = (Product) getIntent().getSerializableExtra("product");

        // Check if product is null
        if (product == null) {
            Log.e(TAG, "Product is null");
            Toast.makeText(this, "Error: Product not found", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
            return;
        }

        //Find views
        ImageView productImage = findViewById(R.id.product_detail_image);
        TextView productName = findViewById(R.id.product_detail_name);
        TextView productPrice = findViewById(R.id.product_detail_price);
        TextView productDescription = findViewById(R.id.product_detail_description);
        Button addToCartButton = findViewById(R.id.add_to_cart_button);

        // Load the product image using Glide
        //productImage.setImageResource(product.getImageUrl());
        String imageUrl = product.getImageUrl(); // Ensure this returns a valid URL string
        Glide.with(this)
                .load(imageUrl)
                .into(productImage);
        // Set product details
        productName.setText(product.getName());
        double price = product.getPrice();
        String priceText = String.format("Price: %.2f kr", price);
        productPrice.setText(priceText);
        productDescription.setText(product.getDescription());

        // Get the cart instance
        cart = Cart.getInstance();

        // Set up the button click listener
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product != null) {
                    cart.addProduct(product);
                    Toast.makeText(ProductDetail.this, product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProductDetail.this, "Error: Product is null", Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });
    }

}
