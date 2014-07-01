package me.ishan.apps.simpletwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by ithukral on 6/23/14.
 */
public class User implements Serializable {
    private static final long serialVersionUID = -4683527024227773891L;
    private String name;
    private Long uId;
    private String screenName;
    private String profileImageUrl;
    private String profileBackgroundUrl;
    private String tagline;
    private int followers;
    private int following;

    public static User fromJson(JSONObject jsonObject){
        User u = new User();
        try {
            u.name = jsonObject.getString("name");
            u.uId = jsonObject.getLong("id");
            u.screenName = jsonObject.getString("screen_name");
            u.profileImageUrl = jsonObject.getString("profile_image_url");
            u.profileBackgroundUrl = jsonObject.getString("profile_background_image_url");
            u.tagline = jsonObject.getString("description");

            u.followers = jsonObject.getInt("followers_count");
            u.following = jsonObject.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return u;
    }

    public String getName() {
        return name;
    }

    public Long getuId() {
        return uId;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getProfileBackgroundUrl() {
        return profileBackgroundUrl;
    }

    public String getTagline() {
        return tagline;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }
}
