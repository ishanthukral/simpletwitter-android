package me.ishan.apps.simpletwitter.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.simpletwitter.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import me.ishan.apps.simpletwitter.EndlessScrollListener;
import me.ishan.apps.simpletwitter.activities.TimelineActivity;
import me.ishan.apps.simpletwitter.models.Tweet;
import me.ishan.apps.simpletwitter.models.TweetArrayAdapter;

public abstract class TweetListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    private ArrayAdapter<Tweet> aTweets;
    private ListView lvTweets;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(getActivity(), tweets);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tweet_list, container, false);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Long lastTweetId = tweets.get(tweets.size()-1).gettId()-1;
                String maxId = lastTweetId.toString();
                fetchOldTweets(maxId);
            }
        });

        fetchOldTweets(null);

        return v;
    }

    public void fetchOldTweets(String maxId) {
        downloadTweets(null, maxId, new JsonHttpResponseHandler() {
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

    public void fetchNewTweets() {
        downloadTweets(tweets.get(0).gettId().toString(), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                tweets.addAll(0, Tweet.fromJsonArray(jsonArray));
                aTweets.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable, JSONArray jsonArray) {
                Log.d("Debug", throwable.toString());
            }
        });
    }

    public void addAll(ArrayList<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    abstract void downloadTweets(String startId, String maxId, JsonHttpResponseHandler handler);

}
