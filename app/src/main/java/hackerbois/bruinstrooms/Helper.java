package hackerbois.bruinstrooms;

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
     * @param gender the gender of the restroom
     * @return the long name of the restroom, eg. MS4317 - MALE
     */
    public static String getLongName(String shortname, String gender) {
        //substring out the M, F, or A off the end of the abbreviation
        //TODO: remember that all-gender restroom abbrev. is A NOT AG as previously planned
        if (shortname != null && shortname.length() > 0) { //ensure not null and length > 0
            shortname = shortname.substring(0, shortname.length() -1); //keep everything but that last letter
        }

        String addon;
        if (gender.equals("male")) {
            addon = " - MALE";
        }
        else if (gender.equals("female")) {
            addon = " - FEMALE";
        }
        else {
            addon = " - ALL GENDER";
        }
        return shortname + addon;
    }
}
