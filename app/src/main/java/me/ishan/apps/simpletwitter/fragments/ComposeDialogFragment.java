package me.ishan.apps.simpletwitter.fragments;



import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codepath.apps.simpletwitter.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import me.ishan.apps.simpletwitter.TwitterApplication;
import me.ishan.apps.simpletwitter.TwitterClient;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ComposeDialogFragment extends DialogFragment {

    public interface OnTweetButtonListener {
        void sendTweet(String tweetString);
    }

    private EditText etComposeTweet;
    private TextView tvCharCount;
    private ImageButton btnComposeTweet;

    private TwitterClient client;


    public ComposeDialogFragment() {
        // Required empty public constructor
    }

    public void onTweetButton() {
        btnComposeTweet.setBackgroundResource(R.drawable.send_button_active);

        final OnTweetButtonListener listener = (OnTweetButtonListener) getActivity();
        client.postTweet(etComposeTweet.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                listener.sendTweet(etComposeTweet.getText().toString());
                dismiss();
            }

            @Override
            public void onFailure(Throwable throwable, JSONObject jsonObject) {
                super.onFailure(throwable, jsonObject);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_compose_dialog, container, false);

        etComposeTweet = (EditText) v.findViewById(R.id.etComposeTweet);
        tvCharCount = (TextView) v.findViewById(R.id.tvCharCount);
        btnComposeTweet = (ImageButton) v.findViewById(R.id.btnComposeTweet);
        btnComposeTweet.setBackgroundResource(R.drawable.send_button);
        btnComposeTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTweetButton();
            }
        });

        client = TwitterApplication.getRestClient();

        etComposeTweet.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return v;
    }


}
