package com.baasbox.samples;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.baasbox.android.BaasBox;
import com.baasbox.android.BaasHandler;
import com.baasbox.android.BaasResult;
import com.baasbox.android.BaasUser;
import com.baasbox.android.json.JsonObject;
import com.baasbox.samples.gcm.Constants;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by eto on 12/04/14.
 */
public class LoginActivity extends FragmentActivity {

    private EditText mUsernameInput;
    private EditText mPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mUsernameInput = (EditText)findViewById(R.id.input_username);
        mPasswordInput = (EditText)findViewById(R.id.input_password);
        findViewById(R.id.btn_signin).setOnClickListener(listener);
        findViewById(R.id.btn_signup).setOnClickListener(listener);
    }

    private BaasUser createValidUser(String username,String password){
        if (TextUtils.isEmpty(username)){
            return null;
        }
        if (TextUtils.isEmpty(password)){
            return null;
        }
        BaasUser user = BaasUser.withUserName(username)
                                .setPassword(password);
        return user;
    }

    private void goNext(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private static class RegisterPushNotifications
        extends AsyncTask<Void,Void,Boolean>{
        private final LoginActivity mContext;
        private RegisterPushNotifications(LoginActivity context){
            mContext = context;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            GoogleCloudMessaging gcm =
                    GoogleCloudMessaging.getInstance(mContext);
            try {
                String regId =gcm.register(Constants.SENDER_ID);
                BaasResult<Void> res =
                        BaasBox.getDefault().enablePushSync(regId);
                return res.isSuccess();
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean value) {
            super.onPostExecute(value);
            Log.d("LOG","All went good? "+value);
            mContext.goNext();
        }
    }

    private int getAppVersion(){
        try {
            PackageInfo p = getPackageManager()
                            .getPackageInfo(getPackageName(), 0);
            return p.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // never happens
            throw new RuntimeException(e);
        }
    }

    private void enablePushNotifications(){
        RegisterPushNotifications task =
                new RegisterPushNotifications(this);
        task.execute();
    }

    private void sendSelfNotification(){
        BaasUser me = BaasUser.current();
        me.send(new JsonObject().putString("message","text"),new BaasHandler<Void>() {
            @Override
            public void handle(BaasResult<Void> result) {

            }
        });
    }
    private final BaasHandler<BaasUser> baasCallback =
            new BaasHandler<BaasUser>() {
                @Override
                public void handle(BaasResult<BaasUser> outcome) {
                    if (outcome.isSuccess()){
//                        BaasUser current  = outcome.value();
//                        BaasUser.current();
                        enablePushNotifications();
                    } else {
                        Toast.makeText(LoginActivity.this,outcome.error().getMessage()
                                ,Toast.LENGTH_LONG).show();
                    }
                }
            };

    private final View.OnClickListener listener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = mUsernameInput.getText().toString();
                    String password = mPasswordInput.getText().toString();
                    BaasUser user = createValidUser(username,password);
                    if (user != null) {
                        switch (v.getId()) {
                            case R.id.btn_signin:
                                user.login(baasCallback);
                                break;
                            case R.id.btn_signup:
                                user.signup(baasCallback);
                                break;
                        }
                    } else {
                        mUsernameInput.setText("");
                        mPasswordInput.setText("");
                        Toast.makeText(LoginActivity.this,"Campi non validi",Toast.LENGTH_LONG).show();
                    }
                }
            };
}
