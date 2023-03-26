package abi.parveen.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.Executor;

public class newreadytodonateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText readyname;
    private Spinner spinner;
    private EditText email;
    private EditText location;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newreadytodonate);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        readyname=findViewById(R.id.readyname);

        email=findViewById(R.id.readyemail);
        location =findViewById(R.id.reaadylocation);
        spinner =findViewById(R.id.spinner);
        register =findViewById(R.id.readyregister);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getname = readyname.getText().toString();
                String getbloodgroup = email.getText().toString();
                String selectedvalue = spinner.getSelectedItem().toString();
                String getlocation = location.getText().toString();
                HashMap<String,Object> hashMap =new HashMap<>();
                hashMap.put("name",getname);
                hashMap.put("blood group",selectedvalue);
                hashMap.put("email",getbloodgroup);
                hashMap.put("location",getlocation);

                // Check if the user already exists in the database
                myRef.child("user").orderByChild("name").equalTo(getname).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // User with the same name already exists
                            Toast.makeText(newreadytodonateActivity.this, "User with this name already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            // Check if the user already exists based on email, blood group, and location
                            myRef.child("user").orderByChild("email_blood_location").equalTo(getbloodgroup + "_" + selectedvalue + "_" + getlocation).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        // User with the same email, blood group, and location already exists
                                        Toast.makeText(newreadytodonateActivity.this, "User with this email, blood group, and location already exists", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // User does not exist, add to the database
                                        myRef.child("user").child(getname).setValue(hashMap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(newreadytodonateActivity.this,"Congratulations, successfully registered",Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(newreadytodonateActivity.this,"Something went wrong: " + e.getMessage(),Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Handle databaseError
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle databaseError
                    }
                });
            }
        });
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
       adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
       spinner.setAdapter(adapter);
       spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text =parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}