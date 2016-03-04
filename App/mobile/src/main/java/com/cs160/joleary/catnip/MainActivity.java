package com.cs160.joleary.catnip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;

public class MainActivity extends Activity {
    //there's not much interesting happening. when the buttons are pressed, they start
    //the PhoneToWatchService with the cat name passed in.

    private Button mFredButton;
    private Button mLexyButton;

    private Button mZipButton;
    private Button mLocationButton;

    private EditText UserZipCode;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserZipCode = (EditText)findViewById(R.id.ZipCode);
        mZipButton = (Button) findViewById(R.id.zipcode_btn);
        mLocationButton = (Button) findViewById(R.id.curLoc_btn);


        mZipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("T", "Zip Listener");


                String newValue = UserZipCode.getText().toString().trim();
                Log.d("T", newValue);

                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("CAT_NAME", "Fred");
                sendIntent.putExtra("zipcode", newValue);

                startService(sendIntent);

                Intent startSelection = new Intent(MainActivity.this, Selection.class);
                startSelection.putExtra("zipcode",newValue);
                startActivity(startSelection);


            }
        });

        mLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String value = new String("location");
//
//                // Make a toast with the String
//                Context context = getApplicationContext();
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(context, value, duration);
//                toast.show();

                Log.d("T", "Location Listener");

                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("CAT_NAME", "Lexy");
                startService(sendIntent);

                Intent startSelection = new Intent(MainActivity.this, Selection.class);
                startActivity(startSelection);

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
