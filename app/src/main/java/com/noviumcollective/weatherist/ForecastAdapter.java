package com.noviumcollective.weatherist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nicholas on 9/22/15.
 */
public class ForecastAdapter extends CursorAdapter {
    private final int VIEW_TYPE_TODAY = 0;
    private final int VIEW_TYPE_FUTURE_DAY = 1;

    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    /*
    This method returns a new inflated view for a corresponding "visible" row,
    Remember the cell view recycling mechanism we talked about? Suppose we can only see 5 cells at a
    time, this means newView will be called only 7 times (5 rows + 1 on top + 1 on bottom).
    This also tells us that this method is not suitable for filling data, however it can be used to
    initialize our ViewHolder which caches reference to the subviews, saving us from calling the
    expensive method findViewById several times later on.
     */
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;

        if(viewType == VIEW_TYPE_TODAY) {
            layoutId = R.layout.list_item_forecast_today;
        } else if(viewType == VIEW_TYPE_FUTURE_DAY) {
            layoutId = R.layout.list_item_forecast;
        }
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        //we are caching the viewHolder in the tag, which accepts any kind of object
        //this is to ensure we only call findViewById once per row (7 times in total, if the row
        // capacity is 5) no matter how much data we have in our cursor.
        view.setTag(viewHolder);
        return view;
    }

    @Override
    /*
    This is what fills row view with data in an efficient way
     */
    public void bindView(View view, Context context, Cursor cursor) {
        //notice how we grab the viewHolder instance from the tag we stored it into...
        ViewHolder viewHolder = (ViewHolder)view.getTag();

        int weatherId = cursor.getInt(ForecastFragment.COL_WEATHER_ID);
        //typically, we would have multiple images stored in our mipmap and we
        viewHolder.iconView.setImageResource(R.mipmap.ic_launcher);

        boolean isMetric = Utility.isMetric(context);
        long dateInMillis = cursor.getLong(ForecastFragment.COL_WEATHER_DATE);
        viewHolder.dateView.setText(Utility.getFriendlyDayString(context, dateInMillis));

        viewHolder.descriptionView.setText(cursor.getString(ForecastFragment.COL_WEATHER_DESC));
        viewHolder.highTempView.setText(Utility.formatTemperature(cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP), isMetric));
        viewHolder.lowTempView.setText(Utility.formatTemperature(cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP), isMetric));
    }

    /*
    A common android pattern to help us cache
     */
    public static class ViewHolder {
        public final ImageView iconView;
        public final TextView dateView;
        public final TextView descriptionView;
        public final TextView highTempView;
        public final TextView lowTempView;

        public ViewHolder(View view) {
            iconView = (ImageView)view.findViewById(R.id.list_item_icon);
            dateView = (TextView)view.findViewById(R.id.list_item_date_textview);
            descriptionView = (TextView)view.findViewById(R.id.list_item_forecast_textview);
            highTempView = (TextView)view.findViewById(R.id.list_item_high_textview);
            lowTempView = (TextView)view.findViewById(R.id.list_item_low_textview);
        }
    }
}
