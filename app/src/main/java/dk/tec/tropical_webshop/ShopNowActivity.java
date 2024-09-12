package dk.tec.tropical_webshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ShopNowActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopnow); // Ensure this is your correct layout

        Button shopNowButton = findViewById(R.id.btnShopNow);

        // Set an OnClickListener for the "Shop Now" button
        shopNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When clicked, move to MainActivity
                Intent intent = new Intent(ShopNowActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
