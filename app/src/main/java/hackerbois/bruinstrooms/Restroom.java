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

    public Restroom() {
        //Default constructor required for calls to DataSnapshat.getValue(User.class)
    }

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
        this.name = restroomname; //update the restroom name
        this.gender = gendermf; //determine gender of restroom
        this.sumRating = "0";
        this.numberOfRatings = "0";
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
     * @param newRating to update the rating of the restroom as users submit feedback
     */
    public void setRating(double newRating) {
        double sum = Double.parseDouble(sumRating); //convert to double
        sum = newRating + sum;
        sumRating = String.valueOf(sum);
        int num = Integer.parseInt(numberOfRatings);
        num++;
        numberOfRatings = String.valueOf(num);
    }

    /**
     * Get the average rating by dividing the sum of all the ratings by the number of ratings
     * @return
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
