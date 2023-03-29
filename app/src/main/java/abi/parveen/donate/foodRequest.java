package abi.parveen.donate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class foodRequest extends AppCompatActivity  {
     Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spinner =findViewById(R.id.spinnerquantity);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.quantity, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

    }
}