package me.ishan.apps.simpletwitter.fragments;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import me.ishan.apps.simpletwitter.TwitterApplication;
import me.ishan.apps.simpletwitter.TwitterClient;
import me.ishan.apps.simpletwitter.models.Tweet;

public class MentionsTimelineFragment extends TweetListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
    }

    @Override
    void downloadTweets(String startId, String maxId, JsonHttpResponseHandler handler) {
        client.getMentionsTimeline(null, maxId, handler);
    }

    public void downloadMoreMentions(String maxId) {
        client.getMentionsTimeline(null, maxId, new JsonHttpResponseHandler() {
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
