package scenery.scenery;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 7/14/2017.
 */


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder>{

    private ArrayList<FilterItem> fiList;


    public FilterAdapter(ArrayList<FilterItem> filteritems){
        this.fiList = filteritems;
    }

    @Override
    public FilterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.filteritem, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.itemName.setText(fiList.get(position).getName());

        viewHolder.chkSelected.setChecked(fiList.get(position).getChecked());

        viewHolder.chkSelected.setTag(fiList.get(position));

        viewHolder.icon.setImageResource(setMarkerIcon(fiList.get(position).getName()));


        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                FilterItem contact = (FilterItem) cb.getTag();

                contact.setChecked(cb.isChecked());
                fiList.get(pos).setChecked(cb.isChecked());

                /*
                Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
                                */
            }
        });

    }
    // Return the size arraylist
    @Override
    public int getItemCount() {
        return fiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemName;

        public CheckBox chkSelected;

        public ImageView icon;

        public FilterItem singleItem;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            itemName = (TextView) itemLayoutView.findViewById(R.id.filterItemName);

            chkSelected = (CheckBox) itemLayoutView
                    .findViewById(R.id.filterCheckBox);

            icon = (ImageView) itemLayoutView
                    .findViewById(R.id.filterIcon);
        }

    }

    // method to access in activity after updating selection
    public ArrayList<FilterItem> getFilterList() {
        return fiList;
    }


    public int setMarkerIcon(String placeName){

        if(placeName.equals("Comedy Open Mic")){
            return R.mipmap.mic_icon;
        }
        else if(placeName.equals("Trivia")){
            return R.mipmap.trivia_icon;
        }else if(placeName.equals("Meal Deals")){
            return R.mipmap.meals;
        }else if(placeName.equals("Karaoke")){
            return R.mipmap.karaoke;
        }else if(placeName.equals("Live Music")){
            return R.mipmap.livemusic;
        }else if(placeName.equals("Dancing")){
            return R.mipmap.danceicon;
        }
        else return R.mipmap.mic_icon;
    }
}


