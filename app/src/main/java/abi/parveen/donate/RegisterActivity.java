package abi.parveen.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
EditText regname ,regemail ,regpass ,regcompass;
Button regbtn;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressdialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
TextView ihaveanacc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ihaveanacc=findViewById(R.id.ihaveanacc);
        regname =findViewById(R.id.regname);
        regemail =findViewById(R.id.regemail);
        regpass =findViewById(R.id.regpass);
        regcompass =findViewById(R.id.regcompass);
        regbtn=findViewById(R.id.regbtn);
        mAuth =FirebaseAuth.getInstance();
        mUser =mAuth.getCurrentUser();
        progressdialog =new ProgressDialog(this);

        ihaveanacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =regname.getText().toString();
                String email=regemail.getText().toString();
                String pass=regpass.getText().toString();
                String compass=regcompass.getText().toString();

                if(name.isEmpty()){
                    regname.setError("please Enter name");
                }else if(!email.matches(emailPattern)){
                    regemail.setError("enter Context Email");
                }else if(pass.isEmpty() || pass.length()<6){
                    regpass.setError("Enter password more 6 char");
                }
                else if(!pass.equals(compass)){
                    regcompass.setError("Password is not matching");

                }else{
                    progressdialog.setMessage("Wait while for Registation");
                    progressdialog.setTitle("Register");
                    progressdialog.setCanceledOnTouchOutside(false);
                    progressdialog.show();


                    mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressdialog.dismiss();
                                successful();
                                Toast.makeText(RegisterActivity.this ,"Registation completed",Toast.LENGTH_SHORT).show();
                            }else{
                               progressdialog.dismiss();
                               Toast.makeText(RegisterActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void successful() {
        Intent intent =new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
    }
}