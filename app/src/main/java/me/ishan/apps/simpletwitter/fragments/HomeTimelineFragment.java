package me.ishan.apps.simpletwitter.fragments;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import me.ishan.apps.simpletwitter.TwitterApplication;
import me.ishan.apps.simpletwitter.TwitterClient;
import me.ishan.apps.simpletwitter.models.Tweet;

public class HomeTimelineFragment extends TweetListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
    }

    public void downloadTweets(String startId, String maxId, JsonHttpResponseHandler handler) {
        client.getHomeTimeline(startId, maxId, handler);
    }

//    private void updateWithNewTweets() {
//        client.getHomeTimeline(tweets.get(0).gettId().toString(), null, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(JSONArray jsonArray) {
//                tweets.addAll(0, Tweet.fromJsonArray(jsonArray));
//                aTweets.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Throwable throwable, JSONArray jsonArray) {
//                Log.d("Debug", throwable.toString());
//            }
//        });
//    }

//    public void composeTweet(MenuItem mi) {
//        Intent i = new Intent(this, ComposeActivity.class);
//        startActivityForResult(i, COMPOSE_RESULT_CODE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == COMPOSE_RESULT_CODE && resultCode == RESULT_OK) {
//            client.postTweet(data.getStringExtra("status").trim(), new JsonHttpResponseHandler() {
//                @Override
//                public void onSuccess(JSONObject jsonObject) {
//                    updateWithNewTweets();
//                }
//
//                @Override
//                public void onFailure(Throwable throwable, JSONArray jsonArray) {
//                    Log.d("Debug", throwable.toString());
//                }
//            });
//        }
//    }
}
