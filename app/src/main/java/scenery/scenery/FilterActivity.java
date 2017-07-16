package scenery.scenery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 7/14/2017.
 */

public class FilterActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private ArrayList<FilterItem> filterItemArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filterlayout);

        Bundle b = getIntent().getExtras();
        filterItemArrayList = (ArrayList<FilterItem>) b.getSerializable("fil");

        mRecyclerView = (RecyclerView) findViewById(R.id.filter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new FilterAdapter(filterItemArrayList);
        mRecyclerView.setAdapter(mAdapter);

        Button setFilterButton = (Button) findViewById(R.id.setFilterButton);
        setFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<FilterItem> fiList = ((FilterAdapter) mAdapter)
                        .getFilterList();

                Intent resultIntent = new Intent();
                Bundle b = new Bundle();
                b.putSerializable("FilterItems", fiList);
                resultIntent.putExtras(b);
                setResult(MapsActivity.RESULT_OK, resultIntent);
                finish();
            }
        });



    }


}
