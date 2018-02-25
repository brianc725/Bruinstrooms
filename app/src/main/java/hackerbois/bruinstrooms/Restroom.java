package hackerbois.bruinstrooms;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by brianchan on 2/15/18.
 */
@IgnoreExtraProperties
public class Restroom {

    private String name;
    private String sumRating; //add up the ratings, must be String for firebase
    private String numberOfRatings; //how many ratings this restroom has
    private String gender;
    // some type of private comment class to hold the message, date and time, and username

    private String urinals;
    private String urinalDividers;
    private String stalls;
    private String hasHandicap; //has a large stall for wheelchairs
    private String sinks;
    private String mirrors;

    public Restroom() {
        //Default constructor required for calls to DataSnapshat.getValue(User.class)
    }

    /**
     * Creates a new <code>Restroom</code> instance.
     *
     * @param restroomname   a <code>String</code> value containing the
     *                      name of the restroom
     * @param gendermf  a <code>String</code> value containing whether
     *                  the restroom is male, female, or all-gender
     * @param urinals the number of urinals this restrom contains; for female is 0
     * @param urinalDividers whether or not there are dividers between the urinals, for female
     *                       is false
     * @param stalls the number of stalls the restroom contains
     * @param hasHandicap whether or not the restroom has a handicap stall for wheelchairs
     * @param sinks how many sinks this restroom has
     * @param mirrors how many mirrors this restroom has
    */
    /*
    public Restroom(String restroomname, String gendermf, int urinals, boolean urinalDividers,
                    int stalls, boolean hasHandicap, int sinks, int mirrors) {
        this.name = restroomname; //update the restroom name
        this.gender = gendermf; //determine gender of restroom
        this.sumRating = "0";
        this.numberOfRatings = "0";
        this.urinals = String.valueOf(urinals);
        this.urinalDividers = String.valueOf(urinalDividers);
        this.stalls = String.valueOf(stalls);
        this.hasHandicap = String.valueOf(hasHandicap);
        this.sinks = String.valueOf(sinks);
        this.mirrors = String.valueOf(mirrors);
    }
    */

    public Restroom(String restroomname, String gendermf, String urinals, String urinalDividers,
                    String stalls, String hasHandicap, String sinks, String mirrors) {
        this.name = restroomname; //update the restroom name
        this.gender = gendermf; //determine gender of restroom
        this.sumRating = "0";
        this.numberOfRatings = "0";
        this.urinals = urinals;
        this.urinalDividers = urinalDividers;
        this.stalls = stalls;
        this.hasHandicap = hasHandicap;
        this.sinks = sinks;
        this.mirrors = mirrors;
    }

    //no setter functions for name or gender since those should never change

    /**
     * Access this Restroom's name.
     * @return this Restroom's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Access this Restroom's gender
     * @return the Restroom's gender
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Submit a new rating to the Restroom
     * Adds 1 to the number of ratings to find correct average and adds the new rating to sum
     * @param newRating to update the rating of the restroom as users submit feedback A STRING
     */
    public void setRating(String newRating) {
        double sum = Double.parseDouble(sumRating); //convert to double
        double numRate = Double.parseDouble(newRating);
        sum = numRate + sum;
        sumRating = String.valueOf(sum);
        int num = Integer.parseInt(numberOfRatings);
        num++;
        numberOfRatings = String.valueOf(num);
    }

    /**
     * Get the average rating by dividing the sum of all the ratings by the number of ratings
     * @return average rating
     */
    public String getRating() {
        if (numberOfRatings == "0"){
            return "0"; //0 reviews so 0 stars, must return immediately to avoid 0/0
        }

        double sum = Double.parseDouble(sumRating);
        double num = Integer.parseInt(numberOfRatings);
        double avg = sum/num;
        return String.valueOf(avg);
    }

    /**
     * Return the number of urinals this restroom has, female has 0
     * @return String of the number of urinals
     */
    public String getUrinals() {
        if (this.gender == "female") {
            return "0";
        }
        return this.urinals;
    }

    /**
     * Returns true or false for whether this restroom has urinal dividers
     * @return String of t/f
     */
    public String getUrinalDividers() {
        if (this.gender == "female") {
            return "false";
        }
        return this.urinalDividers;
    }

    /**
     * Return how many stalls this restroom has
     * @return number of stalls
     */
    public String getStalls() {
        return this.stalls;
    }

    /**
     * Return whether or not this restroom has a large handicap stall
     * @return t/f for handicap stall
     */
    public String getHasHandicap() {
        return this.hasHandicap;
    }

    /**
     * Return number of sinks this restroom has
     * @return number of sinks
     */
    public String getSinks() {
        return this.sinks;
    }

    /**
     * Return number of mirrors that the restroom has
     * @return string of number
     */
    public String getMirrors () {
        return this.mirrors;
    }

    /**
     * Convert our Class into a firebase object since Firebase can only accept data
     * structures such as a HashMap, which is what we used in this case.
     * Need to ensure when we access again we convert them back to doubles
     * @return Hashmap to the converted restroom
     */
    public HashMap<String,String> toFirebaseObject() {
        HashMap<String,String> room =  new HashMap<String,String>();
        room.put("name", name);
        room.put("gender", gender);
        room.put("sumRating", sumRating);
        room.put("numberOfRatings", numberOfRatings);

        return room;
    }


}
