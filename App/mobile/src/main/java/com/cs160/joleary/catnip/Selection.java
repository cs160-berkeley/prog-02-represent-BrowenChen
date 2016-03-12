package com.cs160.joleary.catnip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import com.twitter.sdk.android.tweetui.UserTimeline;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.List;

/**
 * Created by owenchen on 2/29/16.
 */
public class Selection extends Activity {

    private Button selectButton;
    private String newString;
    private String geoLoc;
    private String type;
    private TextView zipCode;
    private TextView location;

    private double longitude;
    private double latitude;

    private JsonObject memberOne;
    private JsonObject memberTwo;
    private JsonObject memberThree;



//    51.5034070,-0.1275920

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection);

        selectButton = (Button) findViewById(R.id.select_btn);
        zipCode = (TextView) findViewById(R.id.zipTextView);
        location = (TextView) findViewById(R.id.textViewLocation);

        //Get ZipCode
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
                geoLoc= null;
            } else {
                newString= extras.getString("zipcode");
                geoLoc= extras.getString("location");
                latitude= extras.getDouble("latitude");
                longitude= extras.getDouble("longitude");
                type= extras.getString("type");


            }
        } else {
            newString= (String) savedInstanceState.getSerializable("zipcode");
            geoLoc= (String) savedInstanceState.getSerializable("location");
            latitude= savedInstanceState.getDouble("latitude");
            longitude= savedInstanceState.getDouble("longitude");
            type= savedInstanceState.getString("type");
        }

        if (newString.equals("94704")){
            geoLoc = "Berkeley";
        } else if (newString.equals("92130")) {
            geoLoc = "San Diego";
        }

        zipCode.setText("Zipcode: " + newString);
        location.setText("Current Location: " + geoLoc);

        Log.d("t", "Got extras in selection screen?");
        Log.d("t", newString);
        Log.d("t", geoLoc);
        Log.d("t", String.valueOf(latitude));
        Log.d("t", String.valueOf(longitude));
        Log.d("t", type);
////        API for sunlight
//        String urlEndpoint = "congress.api.sunlightfoundation.com/legislators/locate?latitude="+latitude+"&longitude="+longitude+"&apikey=d928d392d5a64a28bedbffeda122d0e6";
//        String urlEndpoint = "http://congress.api.sunlightfoundation.com/legislators/locate?zip="+zipcode+"&apikey=d928d392d5a64a28bedbffeda122d0e6";
        String urlEndpoint;

        if (type.equals("zipcode")){
             Log.d("T","zipcode btn clicked hit this API");
              urlEndpoint = "http://congress.api.sunlightfoundation.com/legislators/locate?zip="+newString+"&apikey=d928d392d5a64a28bedbffeda122d0e6";
//            urlEndpoint = "http://congress.api.sunlightfoundation.com/legislators/locate?zip=94704&apikey=d928d392d5a64a28bedbffeda122d0e6";
        } else if (type.equals("location")){
            //        String urlEndpoint = "congress.api.sunlightfoundation.com/legislators/locate?latitude="+latitude+"&longitude="+longitude+"&apikey=d928d392d5a64a28bedbffeda122d0e6";
            urlEndpoint = "http://congress.api.sunlightfoundation.com/legislators/locate?zip=94704&apikey=d928d392d5a64a28bedbffeda122d0e6";
        } else {
            urlEndpoint = "http://congress.api.sunlightfoundation.com/legislators/locate?zip=94704&apikey=d928d392d5a64a28bedbffeda122d0e6";
        }

//        //        Get Request
        Ion.with(Selection.this)
                .load(urlEndpoint)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        System.out.println("Result of the HTTP GET for congressmen for given location");

                        JsonObject curCongressman;
                        JsonArray congressmen = result.get("results").getAsJsonArray();
                        Iterator<JsonElement> congressIter = congressmen.iterator();


                        if (congressIter.hasNext()) {
                            curCongressman = congressIter.next().getAsJsonObject();
                            memberOne = curCongressman;

                            TextView name1 = (TextView) findViewById(R.id.nameView1);
                            name1.setText(curCongressman.get("first_name") + " " + curCongressman.get("last_name"));

                            TextView party1 = (TextView) findViewById(R.id.partyView1);
                            party1.setText(String.valueOf(curCongressman.get("chamber")));

//                            ImageView profileImage = (ImageView) findViewById(R.id.imageButton1);
//                            Ion.with(profileImage)
//                                    .placeholder(R.drawable.abc_ab_share_pack_mtrl_alpha)
//                                    .error(R.drawable.abc_ab_share_pack_mtrl_alpha)
//                                    .load(result.data.user.profileImageUrl);


//                            Get Request for Twitter User
                            UserTimeline userTimeline = new UserTimeline.Builder().screenName(curCongressman.get("twitter_id").toString()).build();

                            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
                            StatusesService statusesService = twitterApiClient.getStatusesService();
                            String twitterName = curCongressman.get("twitter_id").toString();
//                            statusesService.userTimeline(null, twitterName, 5, null, null, null, true, null, false, new Callback<List<Tweet>>() {
//                                @Override
//                                public void success(Result<List<Tweet>> result) {
//
////                                    for(Tweet Tweet : result.data) {
////                                        System.out.println(Tweet);
////                                        System.out.println("Congressmen tweet");
////                                    }
////                                    createListView();
//                                }
//                                public void failure(TwitterException exception) {
//                                    Log.e("Failure", exception.toString());
//                                    exception.printStackTrace();
//                                }
//                            });

                                System.out.println(curCongressman.get("first_name"));
                                System.out.println(curCongressman.get("last_name"));
                                System.out.println(curCongressman.get("party"));
                                System.out.println(curCongressman.get("twitter_id"));
                                System.out.println(curCongressman.get("website"));
                            }


//                        Second row

                            if (congressIter.hasNext()) {
                                curCongressman = congressIter.next().getAsJsonObject();
                                memberTwo = curCongressman;
                                TextView name1 = (TextView) findViewById(R.id.nameView2);
                                name1.setText(curCongressman.get("first_name") + " " + curCongressman.get("last_name"));

                                TextView party1 = (TextView) findViewById(R.id.partyView2);
                                party1.setText(String.valueOf(curCongressman.get("chamber")));

//                            ImageView profileImage = (ImageView) findViewById(R.id.imageButton1);
//                            Ion.with(profileImage)
//                                    .placeholder(R.drawable.abc_ab_share_pack_mtrl_alpha)
//                                    .error(R.drawable.abc_ab_share_pack_mtrl_alpha)
//                                    .load(result.data.user.profileImageUrl);

                                System.out.println(curCongressman.get("first_name"));
                                System.out.println(curCongressman.get("last_name"));
                                System.out.println(curCongressman.get("party"));
                                System.out.println(curCongressman.get("twitter_id"));
                                System.out.println(curCongressman.get("website"));
                            }

//                        Third Row
                            if (congressIter.hasNext()) {
                                curCongressman = congressIter.next().getAsJsonObject();
                                memberThree = curCongressman;
                                TextView name1 = (TextView) findViewById(R.id.nameView3);
                                name1.setText(curCongressman.get("first_name") + " " + curCongressman.get("last_name"));

                                TextView party1 = (TextView) findViewById(R.id.partyView3);
                                party1.setText(String.valueOf(curCongressman.get("chamber")));

//                            ImageView profileImage = (ImageView) findViewById(R.id.imageButton1);
//                            Ion.with(profileImage)
//                                    .placeholder(R.drawable.abc_ab_share_pack_mtrl_alpha)
//                                    .error(R.drawable.abc_ab_share_pack_mtrl_alpha)
//                                    .load(result.data.user.profileImageUrl);

                                System.out.println(curCongressman.get("first_name"));
                                System.out.println(curCongressman.get("last_name"));
                                System.out.println(curCongressman.get("party"));
                                System.out.println(curCongressman.get("twitter_id"));
                                System.out.println(curCongressman.get("website"));
                            }
                        }

                    }

                    );


                    selectButton.setOnClickListener(new View.OnClickListener()

                    {
                        @Override
                        public void onClick (View v){
                            Intent startSelected = new Intent(Selection.this, SelectedMembers.class);

//                            Sending First Member
                            String nameOne = memberOne.get("first_name").toString() + " " + memberOne.get("last_name").toString();
                            startSelected.putExtra("nameOne",nameOne);
                            startSelected.putExtra("chamberOne", memberOne.get("chamber").toString());
                            startSelected.putExtra("emailOne", memberOne.get("oc_email").toString());
                            startSelected.putExtra("websiteOne", memberOne.get("website").toString());
                            startSelected.putExtra("twitterIdOne", memberOne.get("twitter_id").toString());


                            String nameTwo = memberTwo.get("first_name").toString() + " " + memberTwo.get("last_name").toString();
                            startSelected.putExtra("nameTwo",nameTwo);
                            startSelected.putExtra("chamberTwo", memberTwo.get("chamber").toString());
                            startSelected.putExtra("emailTwo", memberTwo.get("oc_email").toString());
                            startSelected.putExtra("websiteTwo", memberTwo.get("website").toString());
                            startSelected.putExtra("twitterIdTwo", memberTwo.get("twitter_id").toString());

                            String nameThree = memberThree.get("first_name").toString() + " " + memberThree.get("last_name").toString();
                            startSelected.putExtra("nameThree",nameThree);
                            startSelected.putExtra("chamberThree", memberThree.get("chamber").toString());
                            startSelected.putExtra("emailThree", memberThree.get("oc_email").toString());
                            startSelected.putExtra("websiteThree", memberThree.get("website").toString());
                            startSelected.putExtra("twitterIdThree", memberThree.get("twitter_id").toString());


                            startActivity(startSelected);



                        }
                    }

                    );

                };
}
