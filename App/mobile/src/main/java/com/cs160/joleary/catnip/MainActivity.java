package com.cs160.joleary.catnip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.*;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import org.json.JSONException;

import io.fabric.sdk.android.Fabric;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener  {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "PRPgEJ8epWPqsKyS7y133LwJM";
    private static final String TWITTER_SECRET = "S99JLRYoqFhg3IzLToRZmIk4yzu58iXih4DlrMfEzOjy3o25cI";

    private TwitterLoginButton loginButton;

    private GoogleApiClient mGoogleApiClient;

    //there's not much interesting happening. when the buttons are pressed, they start
    //the PhoneToWatchService with the cat name passed in.

    private Button mFredButton;
    private Button mLexyButton;

    private Button mZipButton;
    private Button mLocationButton;

    private EditText UserZipCode;

    private double latitude;
    private double longitude;

    private String googleApiKey = "AIzaSyB6Wu8TGETAPh9bFQCUl5lHzizhI_-nrDE";

//    "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude" + "&key=" + apiKey

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

//        GoogleAPI
// Create an instance of GoogleAPIClient.


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        //Login
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

//
//
        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> appSessionResult) {
                AppSession session = appSessionResult.data;
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
                twitterApiClient.getStatusesService().userTimeline(null, "elonmusk", 10, null, null, false, false, false, true, new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> listResult) {
                        for (Tweet tweet : listResult.data) {
                            Log.d("fabricstuff", "result: " + tweet.text + "  " + tweet.createdAt);
                        }
                    }

                    @Override
                    public void failure(TwitterException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void failure(TwitterException e) {
                e.printStackTrace();
            }
        });
//        Twitter

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
                startSelection.putExtra("location", newValue);
                startSelection.putExtra("latitude", latitude);
                startSelection.putExtra("longitude", longitude);
                startSelection.putExtra("type", "zipcode");
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

                String newValue = "94704";
                Log.d("T", "Current Location with Google API ");
                displayLocation();
                newValue = String.valueOf(latitude / longitude);

                Log.d("T", "Location Listener + Geocoding get request");

//
                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("CAT_NAME", "Lexy");
                sendIntent.putExtra("zipcode", newValue);
                startService(sendIntent);


                final Intent startSelection = new Intent(MainActivity.this, Selection.class);
                startSelection.putExtra("zipcode", newValue);
                startSelection.putExtra("latitude", latitude);
                startSelection.putExtra("longitude", longitude);
                startSelection.putExtra("type", "location");
//                Geocoding
                //        Get Request
                Ion.with(MainActivity.this)
                        .load("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=" + googleApiKey)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                // do stuff with the result or error
                                System.out.println("Result of the HTTP GET");
                                System.out.println(result.getAsJsonArray("results"));
                                Iterator<JsonElement> results = result.getAsJsonArray("results").iterator();
                                String locationName = results.next().getAsJsonObject().get("address_components").getAsJsonArray().iterator().next().getAsJsonObject().get("long_name").toString();

                                startSelection.putExtra("location", locationName);

                                Log.d("T", "Put in location name");
                                System.out.println(results.next());
                                System.out.println(results.next().getAsJsonObject().get("address_components").getAsJsonArray().iterator().next().getAsJsonObject().get("long_name").toString());
                                System.out.println(results.next());
                                System.out.println(result.toString());

                                startActivity(startSelection);
                            }
                        });



//                startActivity(startSelection);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    private void displayLocation() {

        Log.d("T", "Starting Display Location");

        Location mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            System.out.println(latitude);
            System.out.println(longitude);

            Log.d("T", "Printed out latitude longitude");

        } else {
            Log.d("T", "Last Location returned NULL");
        }
    }    

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d("T", "OnConnected Methods");
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
//            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
//            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("Connection Failed");
    }
}
