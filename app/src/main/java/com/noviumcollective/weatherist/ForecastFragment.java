package com.noviumcollective.weatherist;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);

        ListView list = (ListView)rootView.findViewById(R.id.listview_forecast);

        ArrayList<String> fakeWeather = new ArrayList<>();
        fakeWeather.add("Today - Clear - 35/29");
        fakeWeather.add("Tomorrow - Clear - 35/29");
        fakeWeather.add("Wednesday - Clear - 35/29");
        fakeWeather.add("Thursday - Clear - 35/29");
        fakeWeather.add("Friday - Clear - 35/29");
        fakeWeather.add("Saturday - Clear - 35/29");
        fakeWeather.add("Sunday - Clear - 35/29");

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this.getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                fakeWeather
        );

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                Intent showDetailIntent = new Intent(getActivity(), DetailActivity.class);
                showDetailIntent.putExtra(Intent.EXTRA_TEXT, item);
                startActivity(showDetailIntent);
            }
        });

        return rootView;
    }
}
