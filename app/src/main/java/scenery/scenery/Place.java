package scenery.scenery;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Ivan on 7/5/2017.
 */

public class Place implements Serializable{

    public String Type;

    public String Name;

    public String Day;

    public String Time;

    public String Address;

    public String Establishment;

    public Double Latitude;

    public Double Longitude;

    public Place(String Type, String Name, String Day, String Time, String Address, String Establishment, Double Lat, Double Long) {
        this.Name = Name;
        this.Day = Day;
        this.Time = Time;
        this.Address = Address;
        this.Establishment = Establishment;
        this.Latitude = Lat;
        this.Longitude = Long;
        this.Type = Type;

    }

    public Place(String Type, String Name, String Day, String Time, String Address, Double Lat, Double Long) {
        this.Type = Type;
        this.Name = Name;
        this.Day = Day;
        this.Time = Time;
        this.Address = Address;
        this.Latitude = Lat;
        this.Longitude = Long;
    }

    public Place(String Type, String Name, String Day, String Time, String Address, String Establishment){
        this.Type = Type;
        this.Name = Name;
        this.Day = Day;
        this.Time = Time;
        this.Address = Address;
        this.Establishment = Establishment;


    }

    public void setType(String type){
        this.Type = type;
    }


}