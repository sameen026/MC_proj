package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class FilterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private Spinner spin;
    private Button subBtn;
    private TextView tv;
    private NumberPicker np;
    private RelativeLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setTitle("Apply Range Filter");
        //rl=findViewById(R.id.rel_layout);

       // rl.setBackgroundColor((ContextCompat.getColor(getApplicationContext(), R.color.normal_rating_star)));
        tv = (TextView) findViewById(R.id.tv_range);
        np = (NumberPicker) findViewById(R.id.np);
        spin = (Spinner) findViewById(R.id.spinner);
        subBtn=(Button)findViewById(R.id.range_btn);
        np.setMinValue(1);
        np.setMaxValue(100);

        np.setWrapSelectorWheel(true);
        spin.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Km");
        categories.add("Miles");
 //       categories.add("Metre");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapter);
        subBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (R.id.range_btn == v.getId()) {
            Intent i = new Intent(this, MainScreenActivity.class);
            startActivity(i);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}

