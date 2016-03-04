package com.cs160.joleary.catnip;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;
    private Button mFeedBtn;

    private String zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Resources res = getResources();
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);


//THIS IS CRASHING
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
////
//        if (extras != null) {
//            Log.d("T", extras.getString("ZIP_CODE"));
//            zip = extras.getString("ZIP_CODE");
//            Log.d("t", "ZIP CODE IS " + zip);
//        }


//        onclicklistener

//        TextView text = (TextView) result.findViewById(R.id.text);
        pager.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                // Adjust page margins:
                //   A little extra horizontal spacing between pages looks a bit
                //   less crowded on a round display.
                final boolean round = insets.isRound();
                int rowMargin = res.getDimensionPixelOffset(R.dimen.page_row_margin);
                int colMargin = res.getDimensionPixelOffset(round ?
                        R.dimen.page_column_margin_round : R.dimen.page_column_margin);
                pager.setPageMargins(rowMargin, colMargin);

                // GridViewPager relies on insets to properly handle
                // layout for round displays. They must be explicitly
                // applied since this listener has taken them over.
                pager.onApplyWindowInsets(insets);
                return insets;
            }
        });
        pager.setAdapter(new SampleGridPagerAdapter(this, getFragmentManager()));
        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);

        pager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("T", "PAGER IS CLICKED BY ON CLICK LISTERN");
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                startService(sendIntent);
            }
        });

//        Cat
//        mFeedBtn = (Button) findViewById(R.id.feed_btn);
//
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//
//        if (extras != null) {
//            String catName = extras.getString("CAT_NAME");
//            mFeedBtn.setText("Feed " + catName);
//        }
//
//        mFeedBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
//                startService(sendIntent);
//            }
//        });

    }
}

