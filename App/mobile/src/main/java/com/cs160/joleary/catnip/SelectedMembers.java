package com.cs160.joleary.catnip;

/**
 * Created by owenchen on 3/1/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SelectedMembers extends Activity {

    private static final String DEBUG_TAG = "http://google.com";
    private ImageView detailedButton;


    private String nameO;
    private String chamberO;
    private String emailO;
    private String websiteO;

    private String nameT;
    private String chamberT;
    private String emailT;
    private String websiteT;

    private String nameTh;
    private String chamberTh;
    private String emailTh;
    private String websiteTh;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_members);


        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            nameO= null;
        } else {
            nameO= extras.getString("nameOne");
            chamberO= extras.getString("chamberOne");
            emailO= extras.getString("emailOne");
            websiteO= extras.getString("websiteOne");

            nameT= extras.getString("nameTwo");
            chamberT= extras.getString("chamberTwo");
            emailT= extras.getString("emailTwo");
            websiteT= extras.getString("websiteTwo");

            nameTh= extras.getString("nameThree");
            chamberTh= extras.getString("chamberThree");
            emailTh= extras.getString("emailThree");
            websiteTh= extras.getString("websiteThree");

        }

        detailedButton = (ImageView) findViewById(R.id.detail_btn);
        detailedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startSelected = new Intent(SelectedMembers.this, DetailedMember.class);
                startActivity(startSelected);
            }
        });
//        Get Request
//        https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=twitterapi&count=2
//        Ion.with(SelectedMembers.this)
//                .load("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=twitterapi&count=2")
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        // do stuff with the result or error
//                        System.out.println("Result of the HTTP GET for twitter timeline");
//                        System.out.println(result.toString());
//                    }
//                });

//        Twitter API
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        StatusesService statusesService = twitterApiClient.getStatusesService();

//        statusesService.userTimeline();


        //        Get User
        long tweetId = 708183163969740800L;
        long bTweetId = 708124564778135552L;
        long cTweetId = 708149176723492864L;
        long dTweetId = 708381506809438209L;
        long eTweetId = 702947800418492418L;
        long fTweetId = 708361675494330368L;
        long[] tweetIdArray = {tweetId, bTweetId, cTweetId, dTweetId};

        final TextView tweetText = (TextView) findViewById(R.id.tweetView1);
        final ImageView profileImage = (ImageView) findViewById(R.id.profileImage1);
        final TextView name1 = (TextView) findViewById(R.id.memberName1);
        final TextView position1 = (TextView) findViewById(R.id.memberPosition1);
        final TextView email1 = (TextView) findViewById(R.id.memberEmail1);
        final TextView website1 = (TextView) findViewById(R.id.memberWebsite1);

        TweetUtils.loadTweet(eTweetId, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
//                System.out.println(result.data.text);
//                Setting profile image
                Ion.with(profileImage)
                        .placeholder(R.drawable.abc_ab_share_pack_mtrl_alpha)
                        .error(R.drawable.abc_ab_share_pack_mtrl_alpha)
                        .load(result.data.user.profileImageUrl);

                name1.setText(result.data.user.name);
                position1.setText(chamberO);
                email1.setText(emailO);
                website1.setText(websiteO);
                tweetText.setText("Tweet ' " + result.data.text + " ' ");
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Load Tweet failure", exception);
            }
        });


//        Second section
        final TextView tweetText2 = (TextView) findViewById(R.id.tweetView2);
        final ImageView profileImage2 = (ImageView) findViewById(R.id.profileImage2);
        final TextView name2 = (TextView) findViewById(R.id.memberName2);
        final TextView position2 = (TextView) findViewById(R.id.memberPosition2);
        final TextView email2 = (TextView) findViewById(R.id.memberEmail2);
        final TextView website2 = (TextView) findViewById(R.id.memberWebsite2);

        TweetUtils.loadTweet(dTweetId, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
//                System.out.println(result.data.text);
//                Setting profile image
                Ion.with(profileImage2)
                        .placeholder(R.drawable.abc_ab_share_pack_mtrl_alpha)
                        .error(R.drawable.abc_ab_share_pack_mtrl_alpha)
                        .load(result.data.user.profileImageUrl);

                name2.setText(nameT);
                position2.setText(chamberT);
                email2.setText(emailT);
                website2.setText(websiteT);
                tweetText2.setText("Tweet ' " + result.data.text + " ' ");
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Load Tweet failure", exception);
            }
        });

//        Third section
        final TextView tweetText3 = (TextView) findViewById(R.id.tweetView3);
        final ImageView profileImage3 = (ImageView) findViewById(R.id.profileImage3);
        final TextView name3 = (TextView) findViewById(R.id.memberName3);
        final TextView position3 = (TextView) findViewById(R.id.memberPosition3);
        final TextView email3 = (TextView) findViewById(R.id.memberEmail3);
        final TextView website3 = (TextView) findViewById(R.id.memberWebsite3);

        TweetUtils.loadTweet(fTweetId, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
//                System.out.println(result.data.text);
//                Setting profile image
                Ion.with(profileImage3)
                        .placeholder(R.drawable.abc_ab_share_pack_mtrl_alpha)
                        .error(R.drawable.abc_ab_share_pack_mtrl_alpha)
                        .load(result.data.user.profileImageUrl);

                name3.setText(nameTh);
                position3.setText(chamberTh);
                email3.setText(emailTh);
                website3.setText(websiteTh);
                tweetText3.setText("Tweet ' " + result.data.text + " ' ");
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Load Tweet failure", exception);
            }
        });

    }


    // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
//    private String downloadUrl(String myurl) throws IOException {
//
//        InputStream is = null;
//        // Only display the first 500 characters of the retrieved
//        // web page content.
//        int len = 500;
//
//        try {
//            URL url = new URL(myurl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(10000 /* milliseconds */);
//            conn.setConnectTimeout(15000 /* milliseconds */);
//            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//            // Starts the query
//            conn.connect();
//            int response = conn.getResponseCode();
//            Log.d(DEBUG_TAG, "The response is: " + response);
//            is = conn.getInputStream();
//
//            // Convert the InputStream into a string
//            String contentAsString = readIt(is, len);
//            return contentAsString;
//
//            // Makes sure that the InputStream is closed after the app is
//            // finished using it.
//        } finally {
//            if (is != null) {
//                is.close();
//            }
//        }
//    }
//    // Reads an InputStream and converts it to a String.
//    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
//        Reader reader = null;
//        reader = new InputStreamReader(stream, "UTF-8");
//        char[] buffer = new char[len];
//        reader.read(buffer);
//        return new String(buffer);
//    }



}