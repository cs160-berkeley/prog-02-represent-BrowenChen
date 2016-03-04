package com.cs160.joleary.catnip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by owenchen on 2/29/16.
 */
public class Selection extends Activity {

    private Button selectButton;
    private String newString;
    private TextView zipCode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection);

        selectButton = (Button) findViewById(R.id.select_btn);
        zipCode = (TextView) findViewById(R.id.zipTextView);

        //Get ZipCode

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("zipcode");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("zipcode");
        }

        zipCode.setText("Zipcode: " + newString);
        Log.d("t", "Selection screen, get extra?");
        Log.d("t", newString);



        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startSelected = new Intent(Selection.this, SelectedMembers.class);
                startActivity(startSelected);
            }
        });

    };
}
