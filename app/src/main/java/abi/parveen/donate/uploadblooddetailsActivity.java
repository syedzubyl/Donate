package abi.parveen.donate;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class uploadblooddetailsActivity extends AppCompatActivity  {

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
        setContentView(R.layout.activity_uploadblooddetails);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        uploadimage=findViewById(R.id.uploadimage);
        uploadname =findViewById(R.id.uploadname);
        uploadgmail=findViewById(R.id.uploadgmail);
        uploadlocation =findViewById(R.id.uploadlocation);
        spinner =findViewById(R.id.uploadspinner);
        uploadready =findViewById(R.id.uploadready);

        progressDialog=new ProgressDialog(this);

        BottomNavigationView newbottmNavigationView =findViewById(R.id.newNavigationView);


        newbottmNavigationView.setSelectedItemId(R.id.newaccount);

        newbottmNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.newaccount:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.request:
                        startActivity(new Intent(getApplicationContext(), requestblooddonateActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });

        // Write a message to the database


       // databaseReference=FirebaseDatabase.getInstance().getReference().child("Donate done").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
       adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
       spinner.setAdapter(adapter);


       ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(
               new ActivityResultContracts.StartActivityForResult(),
               new ActivityResultCallback<ActivityResult>() {
                   @Override
                   public void onActivityResult(ActivityResult result) {
                       if(result.getResultCode() == Activity.RESULT_OK){
                           Intent data=result.getData();
                           uri=data.getData();
                           uploadimage.setImageURI(uri);
                       }else{
                           Toast.makeText(uploadblooddetailsActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
                       }
                   }
               }
       );
       uploadimage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent photopicker =new Intent(Intent.ACTION_PICK);
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
        StorageReference storageReference =FirebaseStorage.getInstance().getReference().child("Android Photo").child(uri.getLastPathSegment());
        AlertDialog.Builder builder =new AlertDialog.Builder(uploadblooddetailsActivity.this);
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

      DataClass dataClass =new DataClass(name, location, bloodgroup, gmail ,imageURL);

      FirebaseDatabase.getInstance().getReference("Android Data").child(name).setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
              if(task.isSuccessful()){
                  Toast.makeText(uploadblooddetailsActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                  finish();
              }
          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Toast.makeText(uploadblooddetailsActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
          }
      });
    }


}