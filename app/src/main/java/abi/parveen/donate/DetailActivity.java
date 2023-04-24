package abi.parveen.donate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessaging;


import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity  {
TextView detailName, detailLocation, detailBlood ,detailGmail;
ImageView detailImage;

    FirebaseMessaging firebaseMessaging;

Button sendRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailName =findViewById(R.id.detailName);
        detailGmail =findViewById(R.id.detailgmail);
        detailBlood=findViewById(R.id.detailBloodGroup);
        detailLocation=findViewById(R.id.detailocation);
        detailImage=findViewById(R.id.detailImage);

        sendRequest=findViewById(R.id.sendRequest);

        Bundle bundle =getIntent().getExtras();
        if (bundle!= null){
            Glide.with(this).load(bundle.getString("image")).into(detailImage);
            detailName.setText(bundle.getString("name"));
            detailGmail.setText(bundle.getString("gmail"));
            detailLocation.setText(bundle.getString("location"));
            detailBlood.setText(bundle.getString("blood group"));
        }

    sendRequest.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });
    }
}