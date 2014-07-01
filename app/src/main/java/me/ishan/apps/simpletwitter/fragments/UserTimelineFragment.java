package me.ishan.apps.simpletwitter.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.simpletwitter.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import me.ishan.apps.simpletwitter.TwitterApplication;
import me.ishan.apps.simpletwitter.TwitterClient;
import me.ishan.apps.simpletwitter.models.Tweet;

public class UserTimelineFragment extends TweetListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
    }

    @Override
    void downloadTweets(String startId, String maxId, JsonHttpResponseHandler handler) {

    }

    public void downloadTweetForUserId(String userId) {
        client.getUserTimeline(userId, null, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {

                addAll(Tweet.fromJsonArray(json));
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s.toString());
            }
        });
    }

    public void downloadTweetsForSelf(String maxId) {
        client.getUserTimeline(null, null, maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray json) {
                addAll(Tweet.fromJsonArray(json));
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
                Log.d("debug", s.toString());
            }
        });
    }
}
