package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

public class ComposeActivity extends Activity {

    EditText etTweetBar;
    Button btnTweet;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        etTweetBar = (EditText) findViewById(R.id.etTweetBar);
        btnTweet = (Button) findViewById(R.id.btnTweet);
        client = TwitterApplication.getRestClient();
    }

    public void sendTweet(View v) {
        client.postTweet(etTweetBar.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Log.d("Debug", "TWEET POSTED");
            }

            @Override
            public void onFailure(Throwable throwable, JSONArray jsonArray) {
                Log.d("Debug", throwable.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
