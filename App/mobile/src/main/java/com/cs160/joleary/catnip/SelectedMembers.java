package com.cs160.joleary.catnip;

/**
 * Created by owenchen on 3/1/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SelectedMembers extends Activity {

    private ImageView detailedButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_members);

        detailedButton = (ImageView) findViewById(R.id.detail_btn);


        detailedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startSelected = new Intent(SelectedMembers.this, DetailedMember.class);
                startActivity(startSelected);
            }
        });
    }
}