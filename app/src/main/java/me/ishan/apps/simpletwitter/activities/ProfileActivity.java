package me.ishan.apps.simpletwitter.activities;

import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletwitter.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;
import org.w3c.dom.Text;

import me.ishan.apps.simpletwitter.TwitterApplication;
import me.ishan.apps.simpletwitter.fragments.UserTimelineFragment;
import me.ishan.apps.simpletwitter.models.User;

public class ProfileActivity extends FragmentActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        try {
            user = (User) getIntent().getSerializableExtra("user");
            loadProfileInfo(user);
        } catch (Exception e) {
            loadProfileInfo();
        }
    }

    private void loadProfileInfo(User user) {
        populateProfileHeader();
        UserTimelineFragment profileFragment = (UserTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentUserTimeline);
        if (profileFragment != null) {
            profileFragment.downloadTweetForUserId(user.getuId().toString());
        }
    }

    private void loadProfileInfo() {
        TwitterApplication.getRestClient().getPersonalInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                user = User.fromJson(jsonObject);
                getActionBar().setTitle("@" + user.getScreenName());
                populateProfileHeader();
                UserTimelineFragment profileFragment = (UserTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentUserTimeline);
                if (profileFragment != null) {
                    profileFragment.downloadTweetsForSelf(null);
                }
            }

            @Override
            public void onFailure(Throwable throwable, JSONObject jsonObject) {
                super.onFailure(throwable, jsonObject);
            }
        });
    }

    private void populateProfileHeader() {
        TextView tvName      = (TextView) findViewById(R.id.tvName);
        TextView tvTagline   = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        ImageView ivProfileBackground = (ImageView) findViewById(R.id.ivProfileBackground);

        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(Integer.toString(user.getFollowers()) + " FOLLOWERS");
        tvFollowing.setText(Integer.toString(user.getFollowing()) + " FOLLOWING");

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(user.getProfileImageUrl(), ivProfileImage);
        imageLoader.displayImage(user.getProfileBackgroundUrl(), ivProfileBackground);

        Log.d("Debug", user.getProfileBackgroundUrl().toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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
