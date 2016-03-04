package com.cs160.joleary.catnip;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by owenchen on 3/1/16.
 */
public class Congressional extends Activity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.congressional);

        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new GridPagerAdapter(this, getFragmentManager()));

    }

}

