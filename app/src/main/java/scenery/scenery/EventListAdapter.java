package scenery.scenery;

/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/



        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private static final String TAG = "EventListAdapter";

    private ArrayList<Place> mDataSet;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private final TextView textView;

        public TextView EventTitle;

        public TextView EventInfo;

        public ImageView Icon;

        public TextView Distance;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });

            EventTitle = (TextView) v.findViewById(R.id.eventListTitle);
            EventInfo = (TextView) v.findViewById(R.id.eventListInfo);
            Icon = (ImageView) v.findViewById(R.id.eventListIcon);
            Distance = (TextView) v.findViewById(R.id.eventDistance);
        }

        /*
        public TextView getTextView() {
            return textView;
        }
        */
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public EventListAdapter(ArrayList<Place> dataSet) {
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.eventlistitem, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        Place place = mDataSet.get(position);

        viewHolder.EventTitle.setText(place.Name);
        viewHolder.EventInfo.setText(place.Day + ", " + place.Time + "\n" + place.Establishment);
        viewHolder.Icon.setImageResource(place.Icon);

        float mile = 1609;
        viewHolder.Distance.setText(String.format("%.2f",place.Distance/1609) + " Mi");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        //viewHolder.getTextView().setText(mDataSet[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}