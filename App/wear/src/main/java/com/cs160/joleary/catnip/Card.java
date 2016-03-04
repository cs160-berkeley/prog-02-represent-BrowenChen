package com.cs160.joleary.catnip;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;

/**
 * Created by owenchen on 3/1/16.
 */
public class Card extends Activity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CardFragment cardFragment = CardFragment.create("2012 Voting Screen",
                "50% Obama | 50% Romney");
        fragmentTransaction.add(R.id.frame_layout, cardFragment);
        fragmentTransaction.commit();

    }
}
