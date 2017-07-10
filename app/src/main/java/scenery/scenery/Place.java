package scenery.scenery;

/**
 * Created by Ivan on 7/5/2017.
 */

public class Place {

    public String Name;

    public String Day;

    public String Time;

    public String Address;

    public String Host;

    public Double Latitude;

    public Double Longitude;

    public Place(String Name, String Day, String Time, String Address, String Host, Double Lat, Double Long){
        this.Name = Name;
        this.Day = Day;
        this.Time = Time;
        this.Address = Address;
        this.Host = Host;
        this.Latitude = Lat;
        this.Longitude = Long;

    }

}

