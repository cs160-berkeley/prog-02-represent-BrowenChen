package com.cs160.joleary.catnip;

/**
 * Created by owenchen on 3/1/16.
 */

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CustomFragment extends Fragment {

    private final int title;
    private final int subTitle;

    //        mFeedBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
//                startService(sendIntent);
//            }
//        });

    public CustomFragment (int title, int subTitle){
        this.title = title;
        this.subTitle = subTitle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.custom_fragment, container, false);
        TextView text = (TextView) result.findViewById(R.id.text);
        text.setText(this.title);

        TextView text2 = (TextView) result.findViewById(R.id.text2);
        text2.setText(this.subTitle);

        result.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // put your onClick logic here
//                Intent intent = getIntent();
//                Bundle extras = intent.getExtras();
                Intent startSelected = new Intent(getActivity(), Card.class);
                startActivity(startSelected);
//                Also change to detailed view on the phone
                Log.d("t", "On click for card congressman");
                System.out.print("test");

                Log.d("t", "Sending intent to Watch to Phone Servnce!!!!");
                Intent sendIntent = new Intent(getActivity().getBaseContext(), WatchToPhoneService.class);
                getActivity().startService(sendIntent);
                //                //Sending Intent with getContext()
//                Intent sendIntent = new Intent(getContext(), WatchToPhoneService.class);
//                getContext().startService(sendIntent);
                // Send the message with the cat name
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //first, connect to the apiclient
//                        mApiClient.connect();
//                        //now that you're connected, send a massage with the cat name
//                        sendMessage("/" + catName, catName);
////                or send a messaage with the zipcode
//                        sendMessage("/zipCode", zip_Code);
//                    }
//                }).start();


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

        return result;
    }

//    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View result = inflater.inflate(R.layout.custom_fragment, container, false);
//        result.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // put your onClick logic here
//                Log.d("t", "teSSSSSSSSSSSst");
//                System.out.print("test");
//
//            }
//        });
//    }
};

