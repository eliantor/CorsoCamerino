package com.baasbox.samples;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private static final String SAVED_TEXT = "saved_text";
    public static final int REQUEST_ID = 1;

    private ArrayList<String> mContent;
    private ListView mListView;
    private StringsAdapter mAdapter;

    private final View.OnClickListener listener =
            new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    launch();
                }
            };

    private void launch(){
        Intent intent = new Intent(this,EditActivity.class);
        startActivityForResult(intent,REQUEST_ID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.btn_launch);
        btn.setOnClickListener(listener);

        mListView  = (ListView)findViewById(R.id.lv_out);
        mContent = new ArrayList<String>();

        if (savedInstanceState != null) {
            mContent.addAll(savedInstanceState.getStringArrayList(SAVED_TEXT));
        }

        mAdapter = new StringsAdapter(mContent,this);
        mListView.setAdapter(mAdapter);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_TEXT,mContent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_ID) {
            if (resultCode == RESULT_OK) {
                mContent.add(data.getStringExtra("RESULT"));
                mAdapter.notifyDataSetChanged();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
