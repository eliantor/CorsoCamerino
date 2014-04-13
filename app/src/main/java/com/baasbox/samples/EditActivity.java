package com.baasbox.samples;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.baasbox.android.*;
import com.baasbox.android.json.JsonArray;

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
    private static final BaasQuery MY_STORED_QUERY =
            BaasQuery.builder()
                    .where("text like \"%hello world%\"")
                    .projection("text","length(text)")
                    .groupBy("")
                    .pagination(0,10)
                    .orderBy("text asc")
                    .build();

    private void storeDocumentOnServer(String content){
        BaasDocument comment  = new BaasDocument("comments");
        comment.putString("text",content);
//               .putArray("my_other_values",new JsonArray().add("elemento1")
//                                                          .add(true));
        comment.save(SaveMode.IGNORE_VERSION,new BaasHandler<BaasDocument>() {
            @Override
            public void handle(BaasResult<BaasDocument> result) {
                if (result.isSuccess()){
                    result.value().getAuthor();
                }
            }
        });


        BaasQuery.Builder b = BaasQuery.builder()
                .where("text like \"%hello world%\"")
                .projection("text","length(text)")
                .groupBy("")
                .pagination(0,10)
                .orderBy("text asc");
        BaasQuery q = b.collection("comments").build();
        q.query(null);

        BaasDocument.fetchAll("comments", b.filter(), null);
        //{ "text": content,
        //  "my_ohter_values": ["elemento1",true]}
        // id _ @

    }
}
