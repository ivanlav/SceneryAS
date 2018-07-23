package scenery.scenery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ivan on 12/24/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static DBHandler sInstance;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "places.db";
    public static final String TABLE_PLACES = "places";

    public static  final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_ESTABLISHMENT = "establishment";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE= "longitude";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_TIME = "time";


    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHandler getsInstance(Context context) {
        if(sInstance == null){
            sInstance = new DBHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PLACES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_ESTABLISHMENT + " TEXT, " +
                COLUMN_LATITUDE + " REAL, " +
                COLUMN_LONGITUDE + " REAL, " +
                COLUMN_DAY + " TEXT, " +
                COLUMN_TIME + " TEXT " + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        onCreate(db);
    }

    public void addPlace(Place place){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, place.Name);
        values.put(COLUMN_TYPE, place.Type);
        values.put(COLUMN_ADDRESS, place.Address);
        values.put(COLUMN_ESTABLISHMENT, place.Establishment);
        values.put(COLUMN_LATITUDE, place.Latitude);
        values.put(COLUMN_LONGITUDE, place.Longitude);
        values.put(COLUMN_DAY, place.Day);
        values.put(COLUMN_TIME, place.Time);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PLACES, null, values);
        db.close();
    }

    public void deletePlace(String placeName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_PLACES + " WHERE" + COLUMN_NAME + "=\"" + placeName + "\";");
        db.close();
    }

    public boolean checkEmpty(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCursor = db.query(TABLE_PLACES, null, null, null, null, null, null);
        Boolean rowExists;

        Log.e("COUNT", Integer.toString(mCursor.getCount()));

        if (mCursor.getCount()>0)
        {
            // DO SOMETHING WITH CURSOR
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }

        return rowExists;
    }

    public ArrayList<Place> placeArrayfromDB(){

        ArrayList<Place> placeArray = new ArrayList<Place>();

        String selectQuery = "SELECT * FROM " + TABLE_PLACES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            Log.e("CURSOR", "true");
            do {
                Place place = new Place(cursor.getString(2), cursor.getString(1), cursor.getString(7),cursor.getString(8), cursor.getString(3), cursor.getString(4), Double.parseDouble(cursor.getString(5)), Double.parseDouble(cursor.getString(6)) );
// Adding contact to list
                Log.e("PLACE", place.Name);
                Log.e("PLACE", place.Type);
                Log.e("PLACE", place.Address);
                Log.e("PLACE", place.Establishment);
                Log.e("PLACE", place.Day);
                Log.e("PLACE", place.Time);
                Log.e("PLACE", place.Latitude.toString());
                Log.e("PLACE", place.Longitude.toString());
                if(place != null){
                    placeArray.add((Place) place);
                }

            } while (cursor.moveToNext());
        }
        Log.e("CURSOR", "false");
// return contact list
        return placeArray;

    }
}
