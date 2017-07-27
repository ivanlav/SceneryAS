package scenery.scenery;

/**
 * Created by Ivan on 7/5/2017.
 */

public class DummyPlaces {

    public static Place[] CreateComedyPlacesArr()
    {
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
}
