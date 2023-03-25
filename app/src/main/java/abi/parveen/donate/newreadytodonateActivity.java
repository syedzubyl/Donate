package abi.parveen.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                String getbloodgroup =email.getText().toString();
                String selectedvalue =spinner.getSelectedItem().toString();
                String getlocation =location.getText().toString();

                HashMap<String,Object> hashMap =new HashMap<>();
                hashMap.put("name",getname);
                hashMap.put("blood group",selectedvalue);
                hashMap.put("email",getbloodgroup);
                hashMap.put("location",getlocation);

                myRef.child("user")
                        .child(getname)
                        .setValue(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(newreadytodonateActivity.this,"Congalation succefully registed",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(newreadytodonateActivity.this,"something went worng"+e.getMessage(),Toast.LENGTH_SHORT).show();
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