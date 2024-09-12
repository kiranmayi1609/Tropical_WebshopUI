package dk.tec.tropical_webshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import dk.tec.tropical_webshop.Cart.CartActivity;
import dk.tec.tropical_webshop.DataModel.Product;
import dk.tec.tropical_webshop.Forms.ProductFormActivity;
import dk.tec.tropical_webshop.Product.ProductAdapter;
import dk.tec.tropical_webshop.Product.ProductApi;
import dk.tec.tropical_webshop.Product.ProductDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<Product> productList;
    private ProductAdapter adapter;
    private static final int REQUEST_CODE_PRODUCT_FORM = 1;
    //url home
    String url= "http://192.168.0.189:9090/";
    //url Tec
   // String url=  "http://192.168.0.209:9090/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.product_list);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList);

        listView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();

        ProductApi productApi = retrofit.create(ProductApi.class);
        Call<List<Product>> call = productApi.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> data = response.body();
                    productList.clear();  // Clear existing data
                    productList.addAll(data);  // Add new data
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle the case where the response is not successful
                    Log.e("MainActivity", "Response not successful");
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Log.e("MainActivity", "API call failed", throwable);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get clicked product
                Product selectedProduct = productList.get(position);
                // Create an intent to start ProductDetailActivity
                Intent intent = new Intent(MainActivity.this, ProductDetail.class);
                intent.putExtra("product", (CharSequence) selectedProduct);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.main) {
                    // Navigate to HomeActivity
                    Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (item.getItemId() == R.id.menu_item_cart) {
                    // Navigate to CartActivity
                    Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(cartIntent);
                    return true;
                } else if (item.getItemId() == R.id.menu_item_profile) {
                    Intent profileIntnent = new Intent(MainActivity.this, ProductFormActivity.class);
                    startActivity(profileIntnent);
                }
                // Handle other menu items or return false if no item matches
                return false;
            }
        });


        }





    }
