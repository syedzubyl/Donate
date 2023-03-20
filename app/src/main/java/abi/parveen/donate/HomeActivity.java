package abi.parveen.donate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
Button blooddonate ;
Button fooddonate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        blooddonate =findViewById(R.id.requestblooddonate);
        fooddonate =findViewById(R.id.readytoblooddonate);

        blooddonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this, BloodDonateActivity.class);
                startActivity(intent);
            }
        });
        fooddonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this,FoodDonateActivity.class);
                startActivity(intent);
            }
        });
    }
}