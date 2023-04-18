package abi.parveen.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class requestblooddonateActivity extends AppCompatActivity {

    FloatingActionButton fab;

    RecyclerView recyclerView;

    List<DataClass> dataClassList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    SearchView searchView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestblooddonate);
       fab=findViewById(R.id.fab);
        recyclerView=findViewById(R.id.recyclerView);
        searchView =findViewById(R.id.search);
        searchView.clearFocus();


        GridLayoutManager gridLayoutManager =new GridLayoutManager(requestblooddonateActivity.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder =new AlertDialog.Builder(requestblooddonateActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress1layout);
        AlertDialog dialog =builder.create();
        dialog.show();

        dataClassList =new ArrayList<>();

         adapter= new MyAdapter(requestblooddonateActivity.this,dataClassList);
        recyclerView.setAdapter(adapter);
        databaseReference=FirebaseDatabase.getInstance().getReference("Android Data");
        dialog.show();

        eventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               dataClassList.clear();
               for(DataSnapshot itemSnapshot : snapshot.getChildren()){
                   DataClass dataClass =itemSnapshot.getValue(DataClass.class);
                   dataClassList.add(dataClass);
               }
               adapter.notifyDataSetChanged();
               dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               searchList(newText);
                return true;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(requestblooddonateActivity.this,uploadblooddetailsActivity.class);
                startActivity(intent);
            }
        });





        BottomNavigationView newbottmNavigationView =findViewById(R.id.newNavigationView);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        newbottmNavigationView.setSelectedItemId(R.id.request);

        newbottmNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.newaccount:
                        startActivity(new Intent(getApplicationContext(), uploadblooddetailsActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.request:
                        return true;


                }
                return false;
            }
        });

    }

    public void searchList(String text){
        ArrayList<DataClass> searchList=new ArrayList<>();
        for(DataClass dataClass : dataClassList){
            if(dataClass.getCdataSpinner().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }
}