package hackerbois.bruinstrooms;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by brianchan on 2/15/18.
 */

public class Restroom {

    private String name;
    private double sumRating; //add up the ratings
    private double numberOfRatings; //how many ratings this restroom has
    private String gender;
    // some type of private comment class to hold the message, date and time, and username

    /**
     * Creates a new <code>Restroom</code> instance.
     *
     * @param restroomname   a <code>String</code> value containing the
     *                      name of the restroom
     *
     * @param gendermf  a <code>String</code> value containing whether
     *                  the restroom is male, female, or all-gender
    */
    public Restroom(String restroomname, String gendermf) {
        name = restroomname; //update the restroom name
        gender = gendermf; //determine gender of restroom
        sumRating = 0;
        numberOfRatings = 0;
    }

    //no setter functions for name or gender since those should never change

    /**
     * Access this Restroom's name.
     * @return this Restroom's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Access this Restroom's gender
     * @return the Restroom's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Submit a new rating to the Restroom
     * Adds 1 to the number of ratings to find correct average and adds the new rating to sum
     * @param newRating to update the rating of the restroom as users submit feedback
     */
    public void setRating(double newRating) {
        sumRating += newRating;
        numberOfRatings++;
    }

    /**
     * Get the average rating by dividing the sum of all the ratings by the number of ratings
     * @return
     */
    public double getRating() {
        double avg = sumRating/numberOfRatings;
        return avg;
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
        room.put("sumRating", Double.toString(sumRating)); //convert to string
        room.put("numberOfRatings", Double.toString(numberOfRatings));

        return room;
    }


}
