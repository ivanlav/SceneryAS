package scenery.scenery;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ivan on 7/5/2017.
 */

public class DummyPlaces {

    public static Place[] CreateComedyPlacesArr() {
        Place comedy1 = new Place("Comedy","Cheapshots Comedy", "Monday", "7:30PM", "335 Somerville Ave, Somerville, MA 02143", "Sally O'Briens", 42.380769, -71.097994);
        Place comedy2 = new Place("Comedy","Ziti's Express", "Monday", "5:30PM", "140 Tremont St, Boston, MA 02111", "Ziti's Express", 42.35563, -71.062576);
        Place comedy3 = new Place("Comedy","Show Me the Mic", "Tuesday", "6:00PM", "356 Chestnut Hill Ave, Brighton, MA 02135", "Agoro's Pizza Bar", 42.336812, -71.151572);
        Place comedy4 = new Place("Comedy","MidEastCorn", "Tuesday", "9:00PM", "472-480 Massachusetts Ave, Cambridge, MA 02139", "Middle East Corner Bar", 42.363776, - 71.101327);
        Place comedy5 = new Place("Comedy","Tavern at the End of the World", "Wednesday", "7:30PM", "108 Cambridge St, Charlestown, MA 02129", "Tavern at the End of the World", 42.382159, -71.079398);
        Place comedy6 = new Place("Comedy","Comedy Lottery", "Wednesday", "10:00PM", "40 Prospect St, Cambridge, MA 02139", "ImprovBoston", 42.365936, -71.103337);
        Place comedy7 = new Place("Comedy","Thunderbar", "Thursday", "6:00PM", "186 Harvard Ave, Allston, MA 02134", "WonderBar", 42.350971, -71.131081);
        Place comedy8 = new Place("Comedy","Comics Anonymous Open Mic", "Thursday", "7:30PM", "4 South Market Building, Boston, MA 02109", "The Hideout", 42.359845, -71.054281);

        Place[] newPlaceArr = new Place[8];
        newPlaceArr[0] = comedy1;
        newPlaceArr[1] = comedy2;
        newPlaceArr[2] = comedy3;
        newPlaceArr[3] = comedy4;
        newPlaceArr[4] = comedy5;
        newPlaceArr[5] = comedy6;
        newPlaceArr[6] = comedy7;
        newPlaceArr[7] = comedy8;

        return newPlaceArr;
    }

    public static Place[] CreateTriviaPlacesArr()
    {
        Place trivia1 = new Place("Trivia","Stump Trivia Quiz","Tuesday","7:30 PM","173 Portland St, Boston 02114","Porters Bar",42.364351, -71.061644);
        Place trivia2 = new Place("Trivia","Stump Trivia Quiz","Tuesday","8:00 PM","101 Atlantic Ave, Boston MA 02110", "Living Room",42.361748, -71.052309);
        Place trivia3 = new Place("Trivia","Stump Trivia Quiz","Tuesday","7:00 PM", "209 Columbus Ave, Boston 02116", "Club Cafe",42.348412, -71.072322);
        Place trivia4 = new Place("Trivia","Stump Trivia Quiz","Wednesday","8:30 PM","450 Commercial St, Boston MA 02109","Rocco's Cucina & Bar",42.367506, -71.053334);
        Place trivia5 = new Place("Trivia","Stump Trivia Quiz","Thursday","6:00 PM","25 Union St, Boston MA 02108","Hennessey's Boston",42.361003, -71.056695);
        Place trivia6 = new Place("Trivia","Stump Trivia Quiz","Friday","7:30 PM","1592 Tremont St, Boston MA 02120", "The Puddingstone Tavern",42.333464, -71.103145);
        Place trivia7 = new Place("Trivia","Stump Trivia Quiz","Friday", "10:00 PM","1287 Cambridge St, Cambridge MA, 02139","Ginger Exchange",42.373691, -71.098923);
        Place trivia8 = new Place("Trivia","Stump Trivia Quiz","Monday","8:00 PM","289 Causeway St, Boston MA 02114","Ducali Pizza",42.367096, -71.058082);

        Place[] newPlaceArr = new Place[8];
        newPlaceArr[0] = trivia1;
        newPlaceArr[1] = trivia2;
        newPlaceArr[2] = trivia3;
        newPlaceArr[3] = trivia4;
        newPlaceArr[4] = trivia5;
        newPlaceArr[5] = trivia6;
        newPlaceArr[6] = trivia7;
        newPlaceArr[7] = trivia8;

        return newPlaceArr;
    }

    public static ArrayList<Place> parseSheet() throws JSONException {

        JSONArray jsonArray = new JSONArray("[{\"Type\":\"Trivia\",\"Event Name\":\"Stump Trivia Quiz\",\"Location\":\"The Living Room\",\"Day\":\"Tuesday\",\"List Time\":\"\",\"Start Time\":\"8:00 PM\",\"Address\":\"101 Atlantic Ave\",\"City\":\"Boston MA\",\"Latitude\":42.361517,\"Longitude\":-71.051955},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"Tavern at the End of the World\",\"Location\":\"Tavern at the End of the World\",\"Day\":\"Wednesday\",\"List Time\":\"7:30:00 PM\",\"Start Time\":\"8:00 PM\",\"Address\":\"108 Cambridge Street\",\"City\":\"Charlestown MA\",\"Latitude\":42.382269,\"Longitude\":-71.078754},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"Green Dragon\",\"Location\":\"Green Dragon\",\"Day\":\"Monday\",\"List Time\":\"\",\"Start Time\":\"9:00 PM\",\"Address\":\"11 Marshall Street\",\"City\":\"Boston MA\",\"Latitude\":42.361144,\"Longitude\":-71.057062},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Stump Trivia Quiz\",\"Location\":\"Ginger Exchange\",\"Day\":\"Friday\",\"List Time\":\"\",\"Start Time\":\"10:00 PM\",\"Address\":\"1287 Cambridge St\",\"City\":\"Cambridge MA\",\"Latitude\":42.373644,\"Longitude\":-71.098696},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"Ziti's Express\",\"Location\":\"Ziti's Express\",\"Day\":\"Monday\",\"List Time\":\"5:00:00 PM\",\"Start Time\":\"5:30 PM\",\"Address\":\"140 Tremont Street\",\"City\":\"Boston MA\",\"Latitude\":42.355582,\"Longitude\":-71.062663},\n" +
                "{\"Type\":\"Meal Deals\",\"Event Name\":\"$7 Sandwiches with Fries or Salad\",\"Location\":\"Harry's Bar and Grill\",\"Day\":\"Friday\",\"List Time\":\"\",\"Start Time\":\"11:30 AM - 2:00 PM\",\"Address\":\"1430 Commonwealth Avenue\",\"City\":\"Brighton MA\",\"Latitude\":42.348442,\"Longitude\":-71.140147},\n" +
                "{\"Type\":\"Meal Deals\",\"Event Name\":\"$7 Sandwiches with Fries or Salad\",\"Location\":\"Harry's Bar and Grill\",\"Day\":\"Monday\",\"List Time\":\"\",\"Start Time\":\"11:30 AM - 2:00 PM\",\"Address\":\"1430 Commonwealth Avenue\",\"City\":\"Brighton MA\",\"Latitude\":42.348442,\"Longitude\":-71.140147},\n" +
                "{\"Type\":\"Meal Deals\",\"Event Name\":\"$7 Sandwiches with Fries or Salad\",\"Location\":\"Harry's Bar and Grill\",\"Day\":\"Thursday\",\"List Time\":\"\",\"Start Time\":\"11:30 AM - 2:00 PM\",\"Address\":\"1430 Commonwealth Avenue\",\"City\":\"Brighton MA\",\"Latitude\":42.348442,\"Longitude\":-71.140147},\n" +
                "{\"Type\":\"Meal Deals\",\"Event Name\":\"$6 Cheeseburgers w/ Fries\",\"Location\":\"Harry's Bar and Grill\",\"Day\":\"Tuesday\",\"List Time\":\"\",\"Start Time\":\"ALL DAY\",\"Address\":\"1430 Commonwealth Avenue\",\"City\":\"Brighton MA\",\"Latitude\":42.348442,\"Longitude\":-71.140147},\n" +
                "{\"Type\":\"Meal Deals\",\"Event Name\":\"$7 Sandwiches with Fries or Salad\",\"Location\":\"Harry's Bar and Grill\",\"Day\":\"Tuesday\",\"List Time\":\"\",\"Start Time\":\"11:30 AM - 2:00 PM\",\"Address\":\"1430 Commonwealth Avenue\",\"City\":\"Brighton MA\",\"Latitude\":42.348442,\"Longitude\":-71.140147},\n" +
                "{\"Type\":\"Meal Deals\",\"Event Name\":\"$7 Sandwiches with Fries or Salad\",\"Location\":\"Harry's Bar and Grill\",\"Day\":\"Wednesday\",\"List Time\":\"\",\"Start Time\":\"11:30 AM - 2:00 PM\",\"Address\":\"1430 Commonwealth Avenue\",\"City\":\"Brighton MA\",\"Latitude\":42.348442,\"Longitude\":-71.140147},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Geeks Who Drink Trivia\",\"Location\":\"Harry's Bar and Grill\",\"Day\":\"Monday\",\"List Time\":\"\",\"Start Time\":\"8:00 PM\",\"Address\":\"1430 Commonwealth Avenue\",\"City\":\"Brighton MA\",\"Latitude\":42.348442,\"Longitude\":-71.140147},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"Iron Furnace\",\"Location\":\"Iron Furnace\",\"Day\":\"Sunday\",\"List Time\":\"\",\"Start Time\":\"\",\"Address\":\"1495 Hancock St\",\"City\":\"Quincy MA\",\"Latitude\":42.24836,\"Longitude\":-71.002088},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Stump Trivia Quiz\",\"Location\":\"The Puddingstone Tavern\",\"Day\":\"Friday\",\"List Time\":\"\",\"Start Time\":\"7:30 PM\",\"Address\":\"1592 Tremont St\",\"City\":\"Boston MA\",\"Latitude\":42.333349,\"Longitude\":-71.102791},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Stump Trivia\",\"Location\":\"Tavern in the Square\",\"Day\":\"Tuesday\",\"List Time\":\"\",\"Start Time\":\"8:00 PM - 10:00 PM\",\"Address\":\"161 Brighton Avenue\",\"City\":\"Allston MA\",\"Latitude\":42.352857,\"Longitude\":-71.132601},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Stump Trivia Quiz\",\"Location\":\"Coogan's\",\"Day\":\"Thursday\",\"List Time\":\"\",\"Start Time\":\"7:00 PM\",\"Address\":\"171 Milk St\",\"City\":\"Boston MA\",\"Latitude\":42.358275,\"Longitude\":-71.05337},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Stump Trivia Quiz\",\"Location\":\"Porters Bar\",\"Day\":\"Tuesday\",\"List Time\":\"\",\"Start Time\":\"7:30 PM\",\"Address\":\"173 Portland St\",\"City\":\"Boston MA\",\"Latitude\":42.364089,\"Longitude\":-71.061146},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"Thunderbar Comedy\",\"Location\":\"Wonderbar\",\"Day\":\"Thursday\",\"List Time\":\"6:00:00 PM\",\"Start Time\":\"6:30 PM\",\"Address\":\"186 Harvard Avenue\",\"City\":\"Allston MA\",\"Latitude\":42.350768,\"Longitude\":-71.131446},\n" +
                "{\"Type\":\"Dancing\",\"Event Name\":\"Thirsty Thursday at Wonder Bar\",\"Location\":\"Wonder Bar - Allston\",\"Day\":\"Thursday\",\"List Time\":\"\",\"Start Time\":\"\",\"Address\":\"186 Harvard Avenue\",\"City\":\"Allston MA\",\"Latitude\":42.350768,\"Longitude\":-71.131446},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Stump Trivia Quiz\",\"Location\":\"Kinsale\",\"Day\":\"Wednesday\",\"List Time\":\"\",\"Start Time\":\"7:00 PM\",\"Address\":\"2 Center Plz\",\"City\":\"Boston MA\",\"Latitude\":42.330187,\"Longitude\":-71.091441},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Stump Trivia Quiz\",\"Location\":\"Club Cafe\",\"Day\":\"Tuesday\",\"List Time\":\"\",\"Start Time\":\"7:00 PM\",\"Address\":\"209 Columbus Ave\",\"City\":\"Boston MA\",\"Latitude\":42.347496,\"Longitude\":-71.073454},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"The Winner's Circle\",\"Location\":\"The Winner's Circle\",\"Day\":\"Tuesday\",\"List Time\":\"Pre-Booked\",\"Start Time\":\"8:00 PM\",\"Address\":\"211 Elm Street\",\"City\":\"Salisbury MA\",\"Latitude\":42.844101,\"Longitude\":-70.896576},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"The Burren\",\"Location\":\"The Burren\",\"Day\":\"Wednesday\",\"List Time\":\"Pre-Booked\",\"Start Time\":\"10:00 PM\",\"Address\":\"247 Elm Street\",\"City\":\"Somerville MA\",\"Latitude\":42.395085,\"Longitude\":-71.121442},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Stump Trivia Quiz\",\"Location\":\"Hennessey's Boston\",\"Day\":\"Thursday\",\"List Time\":\"\",\"Start Time\":\"6:00 PM\",\"Address\":\"25 Union St\",\"City\":\"Boston MA\",\"Latitude\":42.361287,\"Longitude\":-71.056877},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Questionnairey Survey Game\",\"Location\":\"Modern Underground Bar\",\"Day\":\"Wednesday\",\"List Time\":\"\",\"Start Time\":\"7:30 PM\",\"Address\":\"263 Hanover St\",\"City\":\"Boston MA\",\"Latitude\":42.36736,\"Longitude\":-71.052472},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Stump Trivia Quiz\",\"Location\":\"Modern Underground Bar\",\"Day\":\"Thursday\",\"List Time\":\"\",\"Start Time\":\"7:30 PM\",\"Address\":\"263 Hanover St\",\"City\":\"Boston MA\",\"Latitude\":42.36736,\"Longitude\":-71.052472},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Stump Trivia Quiz\",\"Location\":\"Ducali Pizza\",\"Day\":\"Monday\",\"List Time\":\"\",\"Start Time\":\"8:00 PM\",\"Address\":\"289 Causeway St\",\"City\":\"Boston MA\",\"Latitude\":42.366989,\"Longitude\":-71.058264},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Stump Trivia Quiz\",\"Location\":\"99 Restaurant - Charlestown\",\"Day\":\"Thursday\",\"List Time\":\"\",\"Start Time\":\"7:30 PM\",\"Address\":\"29-31 Austin St\",\"City\":\"Charlestown MA\",\"Latitude\":42.374765,\"Longitude\":-71.066564},\n" +
                "{\"Type\":\"Live Music\",\"Event Name\":\"Live Music at O'Briens Pub\",\"Location\":\"O'Brien's Pub\",\"Day\":\"Friday\",\"List Time\":\"\",\"Start Time\":\"Varies\",\"Address\":\"3 Harvard Avenue\",\"City\":\"Allston MA\",\"Latitude\":42.355463,\"Longitude\":-71.132906},\n" +
                "{\"Type\":\"Live Music\",\"Event Name\":\"Live Music at O'Briens Pub\",\"Location\":\"O'Brien's Pub\",\"Day\":\"Monday\",\"List Time\":\"\",\"Start Time\":\"Varies\",\"Address\":\"3 Harvard Avenue\",\"City\":\"Allston MA\",\"Latitude\":42.355463,\"Longitude\":-71.132906},\n" +
                "{\"Type\":\"Live Music\",\"Event Name\":\"Live Music at O'Briens Pub\",\"Location\":\"O'Brien's Pub\",\"Day\":\"Saturday\",\"List Time\":\"\",\"Start Time\":\"Varies\",\"Address\":\"3 Harvard Avenue\",\"City\":\"Allston MA\",\"Latitude\":42.355463,\"Longitude\":-71.132906},\n" +
                "{\"Type\":\"Live Music\",\"Event Name\":\"Live Music at O'Briens Pub\",\"Location\":\"O'Brien's Pub\",\"Day\":\"Sunday\",\"List Time\":\"\",\"Start Time\":\"Varies\",\"Address\":\"3 Harvard Avenue\",\"City\":\"Allston MA\",\"Latitude\":42.355463,\"Longitude\":-71.132906},\n" +
                "{\"Type\":\"Live Music\",\"Event Name\":\"Live Music at O'Briens Pub\",\"Location\":\"O'Brien's Pub\",\"Day\":\"Thursday\",\"List Time\":\"\",\"Start Time\":\"Varies\",\"Address\":\"3 Harvard Avenue\",\"City\":\"Allston MA\",\"Latitude\":42.355463,\"Longitude\":-71.132906},\n" +
                "{\"Type\":\"Live Music\",\"Event Name\":\"Live Music at O'Briens Pub\",\"Location\":\"O'Brien's Pub\",\"Day\":\"Tuesday\",\"List Time\":\"\",\"Start Time\":\"Varies\",\"Address\":\"3 Harvard Avenue\",\"City\":\"Allston MA\",\"Latitude\":42.355463,\"Longitude\":-71.132906},\n" +
                "{\"Type\":\"Live Music\",\"Event Name\":\"Live Music at O'Briens Pub\",\"Location\":\"O'Brien's Pub\",\"Day\":\"Wednesday\",\"List Time\":\"\",\"Start Time\":\"Varies\",\"Address\":\"3 Harvard Avenue\",\"City\":\"Allston MA\",\"Latitude\":42.355463,\"Longitude\":-71.132906},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Questionnairey Survey Game\",\"Location\":\"Ocean Kai\",\"Day\":\"Friday\",\"List Time\":\"\",\"Start Time\":\"7:00 PM\",\"Address\":\"300 Lincoln St\",\"City\":\"Hingham MA\",\"Latitude\":42.249211,\"Longitude\":-70.91808},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"Cheapshots Comedy\",\"Location\":\"Sally O'Briens\",\"Day\":\"Monday\",\"List Time\":\"\",\"Start Time\":\"7:30 PM\",\"Address\":\"335 Somerville Avenue\",\"City\":\"Somerville MA\",\"Latitude\":42.380917,\"Longitude\":-71.098255},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"Comics Anonymous Open Mic\",\"Location\":\"The Hideout\",\"Day\":\"Thursday\",\"List Time\":\"7:30:00 PM\",\"Start Time\":\"8:00 PM\",\"Address\":\"340 Faneuil Hall Marketplace\",\"City\":\"Boston MA\",\"Latitude\":42.352063,\"Longitude\":-71.16419},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"Midway or the Highway\",\"Location\":\"Midway CafÃ©\",\"Day\":\"Sunday\",\"List Time\":\"Lotto\",\"Start Time\":\"9:00 PM\",\"Address\":\"3496 Washington St\",\"City\":\"Boston MA\",\"Latitude\":42.306127,\"Longitude\":-71.107543},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"Show Me The Mic\",\"Location\":\"Agoro's Pizzabar\",\"Day\":\"Tuesday\",\"List Time\":\"6:30:00 PM\",\"Start Time\":\"7:00 PM\",\"Address\":\"356 Chestnut Hill Ave\",\"City\":\"Brighton MA\",\"Latitude\":42.336717,\"Longitude\":-71.151451},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"Comedy Lottery\",\"Location\":\"ImprovBoston\",\"Day\":\"Wednesday\",\"List Time\":\"9:30:00 PM\",\"Start Time\":\"10:00 PM\",\"Address\":\"40 Prospect Street\",\"City\":\"Cambridge MA\",\"Latitude\":42.365801,\"Longitude\":-71.103593},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"Turn Down That Racquet Open Mic\",\"Location\":\"406 VFW Drive\",\"Day\":\"Tuesday\",\"List Time\":\"6:30:00 PM\",\"Start Time\":\"7:00 PM\",\"Address\":\"406 VFW Drive\",\"City\":\"Rockland MA\",\"Latitude\":42.150048,\"Longitude\":-70.913338},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Stump Trivia Quiz\",\"Location\":\"Rocco's Cucina & Bar\",\"Day\":\"Wednesday\",\"List Time\":\"\",\"Start Time\":\"8:30 PM\",\"Address\":\"450 Commercial Street\",\"City\":\"Boston MA\",\"Latitude\":42.367317,\"Longitude\":-71.053172},\n" +
                "{\"Type\":\"Games\",\"Event Name\":\"Bar Bingo\",\"Location\":\"Article 24\",\"Day\":\"Wednesday\",\"List Time\":\"\",\"Start Time\":\"8:00 PM\",\"Address\":\"458 Western Avenue\",\"City\":\"Brighton MA\",\"Latitude\":42.361994,\"Longitude\":-71.142129},\n" +
                "{\"Type\":\"Karaoke\",\"Event Name\":\"Article Karaoke\",\"Location\":\"Article 24\",\"Day\":\"Thursday\",\"List Time\":\"\",\"Start Time\":\"10:00 PM\",\"Address\":\"458 Western Avenue\",\"City\":\"Brighton MA\",\"Latitude\":42.361994,\"Longitude\":-71.142129},\n" +
                "{\"Type\":\"Mixed Open Mic\",\"Event Name\":\"Article Open Mic\",\"Location\":\"Article 24\",\"Day\":\"Sunday\",\"List Time\":\"\",\"Start Time\":\"9:00 PM\",\"Address\":\"458 Western Avenue\",\"City\":\"Brighton MA\",\"Latitude\":42.361994,\"Longitude\":-71.142129},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Stump Trivia\",\"Location\":\"Article 24\",\"Day\":\"Tuesday\",\"List Time\":\"\",\"Start Time\":\"9:00 PM\",\"Address\":\"458 Western Avenue\",\"City\":\"Brighton MA\",\"Latitude\":42.361994,\"Longitude\":-71.142129},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"#MidEastCorn\",\"Location\":\"Middle East Corner Bar\",\"Day\":\"Tuesday\",\"List Time\":\"Pre-Booked\",\"Start Time\":\"9:00 PM\",\"Address\":\"472 Mass. Ave\",\"City\":\"Cambridge MA\",\"Latitude\":42.535734,\"Longitude\":-71.266893},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Questionnairey Survey Game\",\"Location\":\"6B Lounge\",\"Day\":\"Monday\",\"List Time\":\"\",\"Start Time\":\"8:00 PM\",\"Address\":\"6B Beacon St\",\"City\":\"Boston MA\",\"Latitude\":42.356453,\"Longitude\":-71.067641},\n" +
                "{\"Type\":\"Dancing\",\"Event Name\":\"90's Night with DJ Phat Mike\",\"Location\":\"Common Ground\",\"Day\":\"Friday\",\"List Time\":\"\",\"Start Time\":\"10:00 PM - Close\",\"Address\":\"85 Harvard Ave\",\"City\":\"Boston MA\",\"Latitude\":42.3541,\"Longitude\":-71.133087},\n" +
                "{\"Type\":\"Dancing\",\"Event Name\":\"Millennium Night with DJ Phat Mike\",\"Location\":\"Common Ground\",\"Day\":\"Saturday\",\"List Time\":\"\",\"Start Time\":\"10:00 PM - Close\",\"Address\":\"85 Harvard Ave\",\"City\":\"Boston MA\",\"Latitude\":42.3541,\"Longitude\":-71.133087},\n" +
                "{\"Type\":\"Live Music\",\"Event Name\":\"Super Sessions\",\"Location\":\"Common Ground\",\"Day\":\"Wednesday\",\"List Time\":\"\",\"Start Time\":\"Varies\",\"Address\":\"85 Harvard Ave\",\"City\":\"Allston MA\",\"Latitude\":42.353418,\"Longitude\":-71.132349},\n" +
                "{\"Type\":\"Meal Deals\",\"Event Name\":\"All you can eat Breakfast for $5.99\",\"Location\":\"Common Ground\",\"Day\":\"Saturday\",\"List Time\":\"\",\"Start Time\":\"10:00 AM - 2:00 PM\",\"Address\":\"85 Harvard Ave\",\"City\":\"Allston MA\",\"Latitude\":42.353418,\"Longitude\":-71.132349},\n" +
                "{\"Type\":\"Meal Deals\",\"Event Name\":\"Brunch Buffet for $10.99\",\"Location\":\"Common Ground\",\"Day\":\"Sunday\",\"List Time\":\"\",\"Start Time\":\"10:00 AM - 2:00 PM\",\"Address\":\"85 Harvard Ave\",\"City\":\"Allston MA\",\"Latitude\":42.353418,\"Longitude\":-71.132349},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Sex, Drugs & Rock and Roll Trivia\",\"Location\":\"Common Ground\",\"Day\":\"Monday\",\"List Time\":\"\",\"Start Time\":\"8:00 PM - 10:00 PM\",\"Address\":\"85 Harvard Ave\",\"City\":\"Allston MA\",\"Latitude\":42.353418,\"Longitude\":-71.132349},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"ThinkTank Trivia\",\"Location\":\"Common Ground\",\"Day\":\"Thursday\",\"List Time\":\"\",\"Start Time\":\"8:00 PM - 10:00 PM\",\"Address\":\"85 Harvard Ave\",\"City\":\"Allston MA\",\"Latitude\":42.353418,\"Longitude\":-71.132349},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Geeks Who Drink Trivia\",\"Location\":\"Common Ground\",\"Day\":\"Tuesday\",\"List Time\":\"\",\"Start Time\":\"8:00 PM - 10:00 PM\",\"Address\":\"85 Harvard Ave,\",\"City\":\"Allston MA\",\"Latitude\":42.353418,\"Longitude\":-71.132349},\n" +
                "{\"Type\":\"Trivia\",\"Event Name\":\"Questionnairey Survey Game\",\"Location\":\"T's Pub\",\"Day\":\"Thursday\",\"List Time\":\"\",\"Start Time\":\"8:00 PM\",\"Address\":\"973 Commonwealth Avenue\",\"City\":\"Boston MA\",\"Latitude\":42.351837,\"Longitude\":-71.119614},\n" +
                "{\"Type\":\"Comedy Open Mic\",\"Event Name\":\"The Open Mic at Mikey B's\",\"Location\":\"Mikey B's\",\"Day\":\"Tuesday\",\"List Time\":\"7:30:00 PM\",\"Start Time\":\"8:00 PM\",\"Address\":\"989 Victoria Street\",\"City\":\"New Bedford MA\",\"Latitude\":41.697586,\"Longitude\":-70.934167}]");

        ArrayList<Place> placeArr = new ArrayList<Place>();

        for(int i=0; i<jsonArray.length(); i++){


            JSONObject obj = jsonArray.getJSONObject(i);

            String name = obj.getString("Event Name");
            String type = obj.getString("Type");
            String location = obj.getString("Location");
            String day = obj.getString("Day");
            String time = obj.getString("Start Time");
            Double latitude = obj.getDouble("Latitude");
            Double longitude = obj.getDouble("Longitude");

            String address = obj.getString("Address");
            String city = obj.getString("City");

            String fulladdress = address + ", " + city;

            Place newPlace = new Place(type,name,day,time,fulladdress,location,latitude,longitude);

            placeArr.add(newPlace);

            /*
            Log.e("json",name);
            Log.e("json",type);
            Log.e("json",location);
            Log.e("json",day);
            Log.e("json",address);
            */


        }
        return placeArr;
    }


}
