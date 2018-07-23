package scenery.scenery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Ivan on 12/24/2017.
 */

public class AddEventActivity extends AppCompatActivity{

    EditText editName;
    EditText editAddress;
    EditText editEstablishment;
    EditText editType;
    EditText editLatitude;
    EditText editLongitude;
    EditText editDay;
    EditText editTime;

    DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        editName = (EditText) findViewById(R.id.editEventName);
        editAddress = (EditText) findViewById(R.id.editEventAddress);
        editEstablishment = (EditText) findViewById(R.id.editEventEstablishment);
        editType = (EditText) findViewById(R.id.editEventType);
        editLatitude = (EditText) findViewById(R.id.editLatitude);
        editLongitude = (EditText) findViewById(R.id.editLongitude);
        editDay = (EditText) findViewById(R.id.editDay);
        editTime =  (EditText) findViewById(R.id.editTime);

        dbHandler = DBHandler.getsInstance(getApplicationContext());

        Button addButton = (Button) findViewById(R.id.addEventButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Place newplace = new Place(editType.getText().toString(), editName.getText().toString(), editDay.getText().toString(), editTime.getText().toString(), editAddress.getText().toString(), editEstablishment.getText().toString(), Double.parseDouble(editLatitude.getText().toString()), Double.parseDouble(editLongitude.getText().toString()));
                dbHandler.addPlace(newplace);
                finish();
            }
        });

    }
}
