package com.cs160.joleary.catnip;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.List;

/**
 * Created by owenchen on 3/1/16.
 */

public class GridPagerAdapter extends FragmentGridPagerAdapter {

    private final Context mContext;
    private List mRows;

    // Create a static set of pages in a 2D array
    private final Page[][] PAGES = {  };

    public GridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
    }

    static final int[] BG_IMAGES = new int[] {
            R.drawable.debug_background_1,
            R.drawable.debug_background_2
    };

    // A simple container for static data in each page
    private static class Page {
        // static resources
        int titleRes;
        int textRes;
        int iconRes;
    };

    // Override methods in FragmentGridPagerAdapter

    // Obtain the number of pages (vertical)

    @Override
    public int getRowCount() {
        return PAGES.length;
    }

    // Obtain the number of pages (horizontal)
    @Override
    public int getColumnCount(int rowNum) {
        return PAGES[rowNum].length;
    }


    // Obtain the UI fragment at the specified position
    @Override
    public Fragment getFragment(int row, int col) {
        Page page = PAGES[row][col];
        String title =
                page.titleRes != 0 ? mContext.getString(page.titleRes) : null;
        String text =
                page.textRes != 0 ? mContext.getString(page.textRes) : null;
        CardFragment fragment = CardFragment.create(title, text, page.iconRes);

        // Advanced settings (card gravity, card expansion/scrolling)
//        fragment.setCardGravity(page.cardGravity);
//        fragment.setExpansionEnabled(page.expansionEnabled);
//        fragment.setExpansionDirection(page.expansionDirection);
//        fragment.setExpansionFactor(page.expansionFactor);
        return fragment;
    }

    // Obtain the background image for the row
    @Override
    public Drawable getBackgroundForRow(int row) {
        return mContext.getResources().getDrawable(
                (BG_IMAGES[row % BG_IMAGES.length]), null);
    }


}