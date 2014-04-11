package com.baasbox.samples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

/**
 * Created by eto on 11/04/14.
 */
public class EditActivity extends Activity {
    EditText mInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        mInput = (EditText)findViewById(R.id.edit_in);
        findViewById(R.id.btn_change).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = mInput.getText().toString();
                        if (TextUtils.isEmpty(text)){
                            setResult(RESULT_CANCELED);
                        } else {
                            setResult(RESULT_OK,new Intent().putExtra("RESULT",text));
                        }
                        finish();
                    }
                }
        );
    }
}
