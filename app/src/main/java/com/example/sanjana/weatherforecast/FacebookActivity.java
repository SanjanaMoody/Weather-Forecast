package com.example.sanjana.weatherforecast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sanjana on 12/10/2015.
 */
public class FacebookActivity extends ActionBarActivity {

    private CallbackManager callbackManager;
    private LoginManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        FacebookSdk.sdkInitialize(getApplicationContext());


        callbackManager = CallbackManager.Factory.create();

        List<String> permissionNeeds = Arrays.asList("publish_actions");

        manager = LoginManager.getInstance();

        manager.logInWithPublishPermissions(this, permissionNeeds);
        manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                fbShare();
            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
                finish();
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
                finish();
            }
        });
    }
    public void fbShare() {
        try {
            Intent intent = getIntent();
            String summaryText = intent.getExtras().getString("SUMMARY");
            String city = intent.getExtras().getString("CITY");
            String state = intent.getExtras().getString("STATE");
            String tempUnitsInDEG = intent.getExtras().getString("UNITS");
            String currTemp = intent.getExtras().getString("TEMPERATURE");
            String images = intent.getExtras().getString("IMAGE");

            ShareDialog shareDialog = new ShareDialog(this);
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(FacebookActivity.this, "Facebook Posted Successfully", Toast.LENGTH_SHORT).show();

                        finish();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(FacebookActivity.this, "Facebook Post Cancelled", Toast.LENGTH_SHORT).show();

                        finish();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(FacebookActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();

                        exception.printStackTrace();
                        finish();
                    }
                });

                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle("Current weather in "+city+", "+state)
                        .setContentDescription(summaryText+", "+currTemp+tempUnitsInDEG)
                        .setContentUrl(Uri.parse("forecast.io"))
                        .setImageUrl(Uri.parse("http://cs-server.usc.edu:45678/hw/hw8/images/" + images + ".png"))
                        .build();
                shareDialog.show(linkContent);

            } else {

            }
        }
        catch (Exception e){
            Log.d("Facebook Exception", e.toString());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int responseCode,    Intent data)
    {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }

}