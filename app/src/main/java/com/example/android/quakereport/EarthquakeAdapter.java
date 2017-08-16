package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import android.graphics.drawable.GradientDrawable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Desarrollo on 8/16/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;

        if(listViewItem == null){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item,parent,false);
        }

        Earthquake currentEarthquake = getItem(position);

        //set magnitudeView
        TextView magnitudeView = (TextView) listViewItem.findViewById(R.id.magnitude_text_view);
        magnitudeView.setText(formatMagnitude(currentEarthquake.getMagnitude()));

        //set the magnitudeView color
        GradientDrawable magnitudeCircle = (GradientDrawable)magnitudeView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        //set Distance and placeView
        TextView distanceView = (TextView) listViewItem.findViewById(R.id.distance_text_view);
        TextView placeView = (TextView) listViewItem.findViewById(R.id.place_text_view);

        if(canPlaceBesplit(currentEarthquake.getPlace())){
            distanceView.setText(formatDistance(currentEarthquake.getPlace()));
            placeView.setText(formatPlace(currentEarthquake.getPlace()));
        }else{
            distanceView.setVisibility(View.INVISIBLE);
            placeView.setText(currentEarthquake.getPlace());
        }

        //Time in milliseconds
        long timeInMilliSeconds = currentEarthquake.getTimeInMilliseconds();
        Date dateObject = new Date(timeInMilliSeconds);

        //set Date
        TextView dateView = (TextView) listViewItem.findViewById(R.id.date_text_view);
        dateView.setText(formatDate(dateObject));

        //set timeView
        TextView timeView = (TextView) listViewItem.findViewById(R.id.time_text_view);
        timeView.setText(formatTime(dateObject));


        return listViewItem;
    }

    private int getMagnitudeColor(Double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);

        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
        }

        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }

    private boolean canPlaceBesplit(String place){
        if(place.contains("of")){
            return true;
        }
        return false;
    }

    private String formatPlace(String place){
        return place.substring(place.indexOf("f")+2);
    }

    private String formatDistance(String place){
        return place.substring(0,place.indexOf("f")+1);
    }

    private String formatDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(Double magnitude){
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
}
