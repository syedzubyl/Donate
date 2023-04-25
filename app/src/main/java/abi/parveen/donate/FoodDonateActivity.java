package abi.parveen.donate;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FoodDonateActivity extends AppCompatActivity {
    ImageView uploadimage;
    EditText uploadname, uploadlocation, uploadgmail;
    Spinner spinner;
    Button uploadready;

    String imageURL;

    Uri uri;

    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_donate);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        uploadimage = findViewById(R.id.foodimage);
        uploadname = findViewById(R.id.foodname);
        uploadgmail = findViewById(R.id.foodgmail);
        uploadlocation = findViewById(R.id.foodlocation);
        spinner = findViewById(R.id.foodspinner);
        uploadready = findViewById(R.id.foodready);

        progressDialog = new ProgressDialog(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.quantity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadimage.setImageURI(uri);
                        } else {
                            Toast.makeText(FoodDonateActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent photopicker = new Intent(Intent.ACTION_PICK);
                photopicker.setType("image/*");
                activityResultLauncher.launch(photopicker);
            }
        });
        uploadready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadall();
            }
        });

    }

    public void uploadall(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Food Photo").child(uri.getLastPathSegment());
        AlertDialog.Builder builder =new AlertDialog.Builder(FoodDonateActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog =builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage=uriTask.getResult();
                imageURL =urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
}
    public void uploadData(){
        String name =uploadname.getText().toString();
        String location =uploadlocation.getText().toString();
        String bloodgroup =spinner.getSelectedItem().toString();
        String gmail=uploadgmail.getText().toString();

        FooDataClass dataClass =new FooDataClass(name, location, bloodgroup, gmail ,imageURL);

        FirebaseDatabase.getInstance().getReference("Food Data").child(name).setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(FoodDonateActivity.this, "Congratulations, successfully registered", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FoodDonateActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

}}