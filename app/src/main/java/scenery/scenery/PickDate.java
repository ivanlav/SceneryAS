package scenery.scenery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;

import android.support.v7.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ivan on 7/22/2017.
 */

public class PickDate extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datelayout);

        dateView = (TextView) findViewById(R.id.textView3);

        Bundle b = getIntent().getExtras();
        calendar = (Calendar) b.getSerializable("cal");

        showDate();

        Button setTodayButton = (Button) findViewById(R.id.todaybutton);
        Button pickDateButton = (Button) findViewById(R.id.pickdatebutton);
        Button updateButton = (Button) findViewById(R.id.updatebutton);

        setTodayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                showDate();
            }
        });

        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDate();
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            //ContextThemeWrapper dt = new ContextThemeWrapper(this,R.style.CustomDatePickerDialogTheme);
            return new DatePickerDialog(this, R.style.CustomDatePickerDialogTheme,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day

                    calendar.clear();
                    calendar.set(arg1,arg2,arg3);
                    showDate();


                }
            };

    private void showDate() {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        dateView.setText(new StringBuilder().append(month + 1).append("/")
                .append(day).append("/").append(year));


    }

    private void saveDate(){

        Bundle b = new Bundle();
        b.putSerializable("cal",calendar);

        Intent resultIntent = new Intent();
        resultIntent.putExtras(b);
        setResult(MapsActivity.RESULT_OK, resultIntent);
        finish();
    }
}
