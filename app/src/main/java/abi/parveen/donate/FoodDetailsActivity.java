package abi.parveen.donate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FoodDetailsActivity extends AppCompatActivity {
    TextView detailName, detailLocation, detailBlood ,detailGmail;
    ImageView detailImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        detailName =findViewById(R.id.detailName);
        detailGmail =findViewById(R.id.detailgmail);
        detailBlood=findViewById(R.id.detailBloodGroup);
        detailLocation=findViewById(R.id.detailocation);
        detailImage=findViewById(R.id.detailImage);
        Bundle bundle =getIntent().getExtras();
        if (bundle!= null){
            Glide.with(this).load(bundle.getString("image")).into(detailImage);
            detailName.setText(bundle.getString("name"));
            detailGmail.setText(bundle.getString("gmail"));
            detailLocation.setText(bundle.getString("location"));
            detailBlood.setText(bundle.getString("blood group"));
        }


    }
}