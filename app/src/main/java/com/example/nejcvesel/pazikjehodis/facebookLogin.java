package com.example.nejcvesel.pazikjehodis;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by nejcvesel on 05/12/16.
 */

public class facebookLogin extends Fragment {
    LoginButton loginButton;
    CallbackManager callbackManager;

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fblogin, container, false);

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
            // neki
        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setFragment(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("Login uspesen");

                System.out.println(loginResult.getAccessToken().getToken());

            }

            @Override
            public void onCancel() {
                System.out.println("Canceled");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("Error");
            }
        });
        return view;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }




}
