package com.baasbox.samples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.baasbox.android.BaasHandler;
import com.baasbox.android.BaasResult;
import com.baasbox.android.BaasUser;

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

    private final BaasHandler<BaasUser> baasCallback =
            new BaasHandler<BaasUser>() {
                @Override
                public void handle(BaasResult<BaasUser> outcome) {
                    if (outcome.isSuccess()){
                        BaasUser current  = outcome.value();
                        BaasUser.current();

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
