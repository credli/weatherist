package com.noviumcollective.weatherist;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            TextView detailText = (TextView)rootView.findViewById(R.id.weather_detail_textview);
            detailText.setText(text);
        }

        return rootView;
    }
}
