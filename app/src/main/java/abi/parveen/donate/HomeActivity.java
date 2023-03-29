package abi.parveen.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
Button blooddonate ;
Button fooddonate;

Button outbtn;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        blooddonate = findViewById(R.id.requestblooddonate);
        fooddonate = findViewById(R.id.readytoblooddonate);
        outbtn =findViewById(R.id.outbtn);

        blooddonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, newreadytodonateActivity.class);
                startActivity(intent);
            }
        });
        fooddonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, foodRequest.class);
                startActivity(intent);
            }
        });
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottomNavigationView);
        BottomNavigationView newbottmNavigationView =findViewById(R.id.newNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.home);
        newbottmNavigationView.setSelectedItemId(R.id.home);

        newbottmNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.home:
                        return true;
                    case R.id.newaccount:
                        startActivity(new Intent(getApplicationContext(),newreadytodonateActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.home:
                        return true;
                    case R.id.gallery:
                        startActivity(new Intent(getApplicationContext(),HomeSettingActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        final SharedPreferences share = getSharedPreferences("Data", Context.MODE_PRIVATE);
        outbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editt = share.edit();
                editt.clear();
                editt.commit();

                signout();


            }
        });
    }
        private void signout() {
            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    finish();

                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    Toast.makeText(HomeActivity.this,"sign out successful",Toast.LENGTH_SHORT).show();
                }
            });
    }}
