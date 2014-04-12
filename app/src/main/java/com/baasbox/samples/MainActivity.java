package com.baasbox.samples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.baasbox.android.BaasUser;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private static final String SAVED_TEXT = "saved_text";
    public static final int REQUEST_ID = 1;

    private final Callbacks listener = new Callbacks(this);

    private ArrayList<String> mContent;
    private ListView mListView;
    private StringsAdapter mAdapter;

//github.com/eliantor/CorsoCamerino
    void launch(){
        Intent intent = new Intent(this,EditActivity.class);
        startActivityForResult(intent,REQUEST_ID);
    }

    private void startLoginScreen(){
        Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BaasUser.current()==null){
            startLoginScreen();
            return;
        }

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
        mListView.setOnItemClickListener(listener);
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


    /**
    * Created by eto on 11/04/14.
    */
    static class Callbacks
            implements View.OnClickListener,
            AdapterView.OnItemClickListener{
        private MainActivity mainActivity;

        public Callbacks(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onClick(View v) {
            mainActivity.launch();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
