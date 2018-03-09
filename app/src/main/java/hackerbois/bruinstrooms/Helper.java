package hackerbois.bruinstrooms;

import android.content.res.Resources;

/**
 * Created by brianchan on 3/2/18.
 */

public class Helper {

    /**
     * get the full name of the building using the abbreviation code
     * @param name name of the Restroom in the abbreviated format
     * @return full building name
     */
    public static String getBuilding(String name) {
        String building = "";
        if (name.matches("MS(.*)")) { //regex expression for MS<blah>
            building = "Math Sciences Building";
        }
        if (name.matches("BH(.*)")) {
            building = "Boelter Hall";
        }
        if (name.matches("ACK(.*)")) {
            building = "Ackerman Union";
        }
        if (name.matches("PAB(.*)")) {
            building = "Physics and Astronomy Building";
        }
        if (name.matches("EIV(.*)")) {
            building = "Engineering 4";
        }
        return building;
    }

    /**
     * get the room number formatted using abbreviation code
     * @param name name of the restroom in abbreviated format
     * @return formatted room number and floor
     */
    public static String getRoomNumber(String name) {
        int i = Integer.parseInt(name.replaceAll(("\\D"), "")); //get the number out of the abbreviation
        int firstDigit = Integer.parseInt(Integer.toString(i).substring(0,1)); //get the first digit by pulling it out of string

        return ("Floor: " + firstDigit + ", Room: " + i);
    }

    /**
     * Get the long name of the restroom. eg. MS4317M turns into MS4317 - MALE
     * @param shortname the original ID code of the restroom, eg. MS4317M
     * @return the long name of the restroom, eg. MS4317 - MALE
     */
    public static String getLongName(String shortname) {
        //substring out the M, F, or A off the end of the abbreviation
        //TODO: remember that all-gender restroom abbrev. is A NOT AG as previously planned
        char lastLetter = shortname.charAt(shortname.length()-1);

        if (shortname != null && shortname.length() > 0) { //ensure not null and length > 0
            shortname = shortname.substring(0, shortname.length() -1); //keep everything but that last letter
        }

        String addon;
        if (lastLetter == 'M') {
            addon = " - MALE";
        }
        else if (lastLetter == 'F') {
            addon = " - FEMALE";
        }
        else {
            addon = " - ALL GEN";
        }
        return shortname + addon;
    }

    /**
     * Converts dp values into px values
     * @param dp - dp value that needs to be converted
     * @return int representing the px value
     */
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
