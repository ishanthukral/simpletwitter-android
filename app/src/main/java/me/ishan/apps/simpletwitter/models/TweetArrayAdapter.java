package me.ishan.apps.simpletwitter.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletwitter.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import me.ishan.apps.simpletwitter.TwitterApplication;
import me.ishan.apps.simpletwitter.TwitterClient;
import me.ishan.apps.simpletwitter.activities.ProfileActivity;
import me.ishan.apps.simpletwitter.activities.TimelineActivity;


/**
 * Created by ithukral on 6/23/14.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

    TwitterClient client;

    public TweetArrayAdapter(FragmentActivity context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Tweet tweet = getItem(position);
        final User user = tweet.getUser();

        View v;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.tweet_item, parent, false);
        } else {
            v = convertView;
        }

        ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = TweetArrayAdapter.this.getContext();
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra("user", user);
                context.startActivity(i);
            }
        });

        TextView tvScreenName = (TextView) v.findViewById(R.id.tvName);
        TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
        TextView tvTimestamp = (TextView) v.findViewById(R.id.tvTimestamp);

        final TextView tvRetweetCount = (TextView) v.findViewById(R.id.tvRetweetCount);
        tvRetweetCount.setText(String.valueOf(tweet.getRetweetCount()));

        final ImageButton ibStar = (ImageButton) v.findViewById(R.id.ibStar);
        if (tweet.getFavorited()) {
            ibStar.setBackgroundResource(R.drawable.ic_star_active);
        } else {
            ibStar.setBackgroundResource(R.drawable.ic_star);
        }
        ibStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (client == null) {
                    client = TwitterApplication.getRestClient();
                }
                client.favouriteTweet(tweet.gettId().toString(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);
                        ibStar.setBackgroundResource(R.drawable.ic_star_active);
                        tweet.setFavorited(true);
                    }

                    @Override
                    public void onFailure(Throwable throwable, JSONObject jsonObject) {
                        super.onFailure(throwable, jsonObject);
                    }
                });
            }
        });


        final ImageButton ibRetweet = (ImageButton) v.findViewById(R.id.ibRetweet);
        if (tweet.getRetweeted()) {
            ibRetweet.setBackgroundResource(R.drawable.ic_retweet_active);
        } else {
            ibRetweet.setBackgroundResource(R.drawable.ic_retweet);
        }
        ibRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (client == null) {
                    client = TwitterApplication.getRestClient();
                }
                client.retweetTweet(tweet.gettId().toString(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);
                        ibRetweet.setBackgroundResource(R.drawable.ic_retweet_active);
                        tweet.setRetweetCount(tweet.getRetweetCount()+1);
                        tvRetweetCount.setText(String.valueOf(tweet.getRetweetCount()));
                        tweet.setRetweeted(true);
                    }

                    @Override
                    public void onFailure(Throwable throwable, JSONObject jsonObject) {
                        super.onFailure(throwable, jsonObject);
                    }
                });
            }
        });

        ImageButton ibReply = (ImageButton) v.findViewById(R.id.ibReply);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);

        tvScreenName.setText("@"+tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvTimestamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));

        return v;
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        relativeDate = relativeDate.replaceAll("seconds", "s");
        relativeDate = relativeDate.replaceAll("second", "s");
        relativeDate = relativeDate.replaceAll("minutes", "m");
        relativeDate = relativeDate.replaceAll("minute", "m");
        relativeDate = relativeDate.replaceAll("hours", "h");
        relativeDate = relativeDate.replaceAll("hour", "h");

        return relativeDate;
    }
}
