package abi.parveen.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class FoodregisterActivity extends AppCompatActivity {
Button registernnp;
Button registerdonner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodregister);

        registernnp=findViewById(R.id.registernnp);
        registerdonner=findViewById(R.id.registerdoner);

        registernnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(FoodregisterActivity.this,foodRequest.class);
                startActivity(intent);
            }
        });
        registerdonner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(FoodregisterActivity.this,FoodDonateActivity.class);
                startActivity(intent);
            }
        });
        BottomNavigationView foodregister =findViewById(R.id.foodNavigationView);
        foodregister.setSelectedItemId(R.id.register);

        foodregister.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.home:
                      startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                      overridePendingTransition(0,0);
                      return true;

                   case R.id.register:

                       return true;
               }

                return false;
            }
        });
    }
}