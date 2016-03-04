package com.cs160.joleary.catnip;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.WearableListView;

/**
 * Created by owenchen on 3/1/16.
 */
public class List extends Activity implements WearableListView.ClickListener{

    String[] elements = { "List Item 1", "List Item 2" };
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        // Get the list component from the layout of the activity
        WearableListView listView =
                (WearableListView) findViewById(R.id.wearable_list);

        // Assign an adapter to the list
        listView.setAdapter(new Adapter(this, elements));

        // Set a click listener
        listView.setClickListener(this);

    }

    // WearableListView click listener
    @Override
    public void onClick(WearableListView.ViewHolder v) {
        Integer tag = (Integer) v.itemView.getTag();
        // use this data to complete some action ...
    }

    @Override
    public void onTopEmptyRegionClick() {
    }
}

