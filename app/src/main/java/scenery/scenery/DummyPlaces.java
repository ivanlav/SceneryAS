package scenery.scenery;

/**
 * Created by Ivan on 7/5/2017.
 */

public class DummyPlaces {

    public static Place[] CreatePlacesArr()
    {
        Place comedy1 = new Place("Sally O'Briens", "Monday", "7:30PM", "335 Somerville Ave, Somerville, MA 02143", "Shawn Carter", 42.380769, -71.097994);
        Place comedy2 = new Place("Ziti's Express", "Monday", "5:30PM", "140 Tremont St, Boston, MA 02111", "Anthony Massa", 42.35563, -71.062576);
        Place comedy3 = new Place("Agoro's Show Me the Mic", "Tuesday", "6:00PM", "356 Chestnut Hill Ave, Brighton, MA 02135", "Mike Tannian", 42.336812, -71.151572);
        Place comedy4 = new Place("MidEastCorn", "Tuesday", "9:00PM", "472-480 Massachusetts Ave, Cambridge, MA 02139", "John Paul Rivera", 42.363776, - 71.101327);
        Place comedy5 = new Place("Tavern at the End of the World", "Wednesday", "7:30PM", "108 Cambridge St, Charlestown, MA 02129", "Drunk Lady", 42.382159, -71.079398);
        Place comedy6 = new Place("ImprovBoston", "Wednesday", "10:00PM", "40 Prospect St, Cambridge, MA 02139", "Old Lady", 42.365936, -71.103337);
        Place comedy7 = new Place("Thunderbar", "Thursday", "6:00PM", "186 Harvard Ave, Allston, MA 02134", "Owen Linders", 42.350971, -71.131081);
        Place comedy8 = new Place("The Hideout", "Thursday", "7:30PM", "4 South Market Building, Boston, MA 02109", "Zach Brazao", 42.359845, -71.054281);

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
}
