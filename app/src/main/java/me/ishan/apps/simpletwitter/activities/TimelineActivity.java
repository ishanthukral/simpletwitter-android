package me.ishan.apps.simpletwitter.activities;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.codepath.apps.simpletwitter.R;

import me.ishan.apps.simpletwitter.fragments.ComposeDialogFragment;
import me.ishan.apps.simpletwitter.fragments.HomeTimelineFragment;
import me.ishan.apps.simpletwitter.fragments.MentionsTimelineFragment;
import me.ishan.apps.simpletwitter.fragments.TweetListFragment;
import me.ishan.apps.simpletwitter.listeners.FragmentTabListener;
import me.ishan.apps.simpletwitter.models.Tweet;
import me.ishan.apps.simpletwitter.models.TweetArrayAdapter;
import me.ishan.apps.simpletwitter.models.User;

public class TimelineActivity extends FragmentActivity implements ComposeDialogFragment.OnTweetButtonListener{

    ImageButton btnComposeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setupViews();
        setupTabs();
    }

    public void setupViews() {
        btnComposeButton = (ImageButton) findViewById(R.id.btnComposeButton);
        Drawable dr = getResources().getDrawable(R.drawable.ic_compose_button);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 190, 190, true));
        btnComposeButton.setImageDrawable(d);
    }

    @Override
    public void sendTweet(String tweetString) {
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
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

    public void composeTweet(MenuItem menuItem) {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivity(i);
    }

    public void onProfileView(MenuItem menuItem) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void composeTweet(View v) {
        FragmentManager fm = getSupportFragmentManager();
        ComposeDialogFragment cdf = new ComposeDialogFragment();
        cdf.show(fm, "fragment_compose_dialog");
    }

    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab tab1 = actionBar
                .newTab()
                .setText("Home")
                .setIcon(R.drawable.ic_home)
                .setTag("HomeTimelineFragment")
                .setTabListener(
                        new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
                                HomeTimelineFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setText("Mentions")
                .setIcon(R.drawable.ic_mentions)
                .setTag("MentionsTimelineFragment")
                .setTabListener(
                        new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions",
                                MentionsTimelineFragment.class));

        actionBar.addTab(tab2);
    }
}
