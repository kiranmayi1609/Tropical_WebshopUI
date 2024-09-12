package dk.tec.tropical_webshop.Forms;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import dk.tec.tropical_webshop.DataModel.Product;
import dk.tec.tropical_webshop.Product.ProductApi;
import dk.tec.tropical_webshop.R;
import dk.tec.tropical_webshop.Retrofit.RetrofitClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFormActivity extends AppCompatActivity {

    private EditText etProductName, etProductDescription, etProductPrice, etImageUrl;
    private Button btnSubmitProduct;
    private static final String TAG = "ProductFormActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        //Intilize the UI elements
        etProductName = findViewById(R.id.nameInput);
        etProductDescription = findViewById(R.id.descriptionInput);
        etProductPrice = findViewById(R.id.priceInput);
        etImageUrl = findViewById(R.id.imageUrlInput);
        btnSubmitProduct = findViewById(R.id.submitButton);
       //set the submit button click listner
        btnSubmitProduct.setOnClickListener(view->{

            //get the values from the edit text fields
            String name=etProductName.getText().toString();
            String description = etProductDescription.getText().toString();
            String priceStr = etProductPrice.getText().toString();
            String imageUrl = etImageUrl.getText().toString();

            // Check if all fields are filled
            if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty() || imageUrl.isEmpty()) {
                Toast.makeText(ProductFormActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Parse price to double
            double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                Toast.makeText(ProductFormActivity.this, "Invalid price format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Submit the product using the Retrofit call
            submitProduct(name, description, price, imageUrl);

        });
    }
    // Method to submit the product data to the API
    private void submitProduct(String name, String description, double price, String imageUrl) {
        ProductApi productApi = RetrofitClient.getClient("http://192.168.0.189:9090/").create(ProductApi.class);

        // Create a Product object with the input data
        Product product = new Product(name, description, price, imageUrl);
        //Product product = new Product("Test Product", "This is a description", 29.99, "http://example.com/image.jpg");

        // Call the API method with the Product object as the body
        Call<ResponseBody> call = productApi.uploadProductWithImageUrl(product);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //System.out.println("Product uploaded successfully");
                   Toast.makeText(ProductFormActivity.this, "Product Created Successfully", Toast.LENGTH_SHORT).show();
                    finish(); //close the activity after success
                } else {
                    Toast.makeText(ProductFormActivity.this, "Failed to Create Product", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Response Code: " + response.code());
                   // System.out.println("Failed to upload product: " + response.code());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Network or server failure
                //t.printStackTrace();
                Toast.makeText(ProductFormActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ProductFormActivity", "Error: " + t.getMessage());
            }
        });
    }



    private void onClick(View v) {
        // Get input values
        String name = etProductName.getText().toString();
        String description = etProductDescription.getText().toString();
        double price = 0.0;
        try {
            price = Double.parseDouble(etProductPrice.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
            return;
        }
        String imageUrl = etImageUrl.getText().toString();

        // Validate inputs
        if (name.isEmpty() || description.isEmpty() || imageUrl.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            submitProduct(name, description, price, imageUrl);
        }
    }
}
