package scenery.scenery;

import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;

/**
 * Created by Ivan on 7/5/2017.
 */

public class Place implements Serializable, ClusterItem {

    public String Type;

    public String Name;

    public String Day;

    public String Time;

    public String Address;

    public String Establishment;

    public Double Latitude;

    public Double Longitude;

    public int Icon;

    public float Distance;

    public float getDistance(){
        return Distance;
    }

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

    @Override
    public LatLng getPosition() {
        return new LatLng(Latitude,Longitude);
    }

    @Override
    public String getTitle() {
        return Name;
    }

    @Override
    public String getSnippet() {
        return Address;
    }
}