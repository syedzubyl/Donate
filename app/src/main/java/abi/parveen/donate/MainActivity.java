package abi.parveen.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView ihavealreadybtn;
    EditText inputEmail, inputPassword;
    Button Loginbtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    TextView Forgotbtn;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    Button googlebtn;
    public  static String PREFS_NAME="MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ihavealreadybtn = findViewById(R.id.ihavealreadybtn);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        Loginbtn = findViewById(R.id.Loginbtn);

        Forgotbtn = findViewById(R.id.Forgotbtn);


        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        googlebtn = findViewById(R.id.googlebtn);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);


        ihavealreadybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
        final SharedPreferences share =getSharedPreferences("Data", Context.MODE_PRIVATE);
        final String type = share.getString("Email","");
        if(type.isEmpty()){
            Toast.makeText(MainActivity.this, "please Login ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent i =new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(i);
        }

        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editt =share.edit();
                editt.putString("Email",inputPassword.getText().toString());
                editt.putString("Password",inputPassword.getText().toString());
                editt.commit();





                SharedPreferences sharedPreferences =getSharedPreferences(MainActivity.PREFS_NAME,0);
                SharedPreferences.Editor editor=sharedPreferences.edit();

                editor.putBoolean("HasLoggedIn",true);
                editor.commit();

                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (!email.matches(emailPattern)) {
                    inputEmail.setError("Enter value Email");
                } else if (password.isEmpty() || password.length() < 6) {
                    inputPassword.setError("Enter right password");
                } else {
                    progressDialog.setMessage("Please wait while Login....");
                    progressDialog.setTitle("Login..");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                progressDialog.dismiss();
                                sendUserToNextActivity();
                                Toast.makeText(MainActivity.this, "Login Successfully ", Toast.LENGTH_SHORT).show();

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "error check" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        Boolean isLoggedIn = sharedPref.getBoolean("is_logged_in", false);

        if (!isLoggedIn) {
            Toast.makeText(MainActivity.this, " Login ", Toast.LENGTH_SHORT).show();

        } else {
            // Show main screen
        }
        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });






    }

    void signIn() {
        Intent signInIntent =googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1000){
            Task<GoogleSignInAccount>task=GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);

                Intent intent =new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);

                Toast.makeText(MainActivity.this, "Login Successfully ", Toast.LENGTH_SHORT).show();


            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(),"Something went Wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sendUserToNextActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }




    private void HomeActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
}