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
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletwitter.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import me.ishan.apps.simpletwitter.activities.ProfileActivity;
import me.ishan.apps.simpletwitter.activities.TimelineActivity;

/**
 * Created by ithukral on 6/23/14.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
    Tweet tweet;

    public TweetArrayAdapter(FragmentActivity context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        tweet = getItem(position);

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
                User user = tweet.getUser();
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra("user", user);
                context.startActivity(i);
            }
        });

        TextView tvScreenName = (TextView) v.findViewById(R.id.tvName);
        TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
        TextView tvTimestamp = (TextView) v.findViewById(R.id.tvTimestamp);

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
