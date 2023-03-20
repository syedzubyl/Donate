package abi.parveen.donate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BloodDonateActivity extends AppCompatActivity {
Button newblooddonate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donate);
           newblooddonate =findViewById(R.id.readytoblooddonate);
            newblooddonate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(BloodDonateActivity.this, newreadytodonateActivity.class);
                    startActivity(intent);
                }
            });
    }
}