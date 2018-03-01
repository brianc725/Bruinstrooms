package hackerbois.bruinstrooms;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class RestroomActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private DatabaseReference thisRestroom;
    private String restroomName;
    private Restroom rest; //the current restroom object
    private RatingBar ratingBar;

    private LinearLayout featuresLayout;
    private boolean featuresOpened = false;

    /**
     * get the full name of the building using the abbreviation code
     * @param name name of the Restroom in the abbreviated format
     * @return full building name
     */
    private String getBuilding(String name) {
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
    private String getRoomNumber(String name) {
        int i = Integer.parseInt(name.replaceAll(("\\D"), "")); //get the number out of the abbreviation
        int firstDigit = Integer.parseInt(Integer.toString(i).substring(0,1)); //get the first digit by pulling it out of string

        return ("Floor: " + firstDigit + ", Room: " + i);
    }

    public void showAlertDialogButtonClicked(View view) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Rating");

        // add a list
        String[] nums = {"1", "2", "3", "4", "5"}; //
        builder.setItems(nums, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                which++; //1 actually is in index 0, 5 is in index 4; add 1 to make consistent
                String newRating = String.valueOf(which);
                rest.setRating(newRating); //set the new rating based on the user choice
                ref.child("restrooms").child(rest.getName()).setValue(rest); //update the db
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create(); //create the dialog box to pop up
        dialog.show(); //show it to user
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restroom);

        restroomName = "MS4317M"; //TODO: temporary place holder, when intent calls this activity pass in the restroom name

        database = FirebaseDatabase.getInstance();

        ref = database.getReference();
        thisRestroom = ref.child("restrooms/" + restroomName);

        ratingBar = (RatingBar) findViewById(R.id.RatingBar); //get the rating bar object

        featuresLayout = (LinearLayout) findViewById(R.id.featuresContainer);

        featuresLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView featuresCollapse = (TextView) findViewById(R.id.featuresCollapse);
                TextView featuresFull = (TextView) findViewById(R.id.featuresFull);

                if (featuresOpened == false) {
                    //expand
                    featuresCollapse.setText("Amenities\nStuff\nStuff\nStuff");
                    featuresCollapse.setMaxLines(Integer.MAX_VALUE); //expand to add more lines
                    featuresOpened = true; //say that the box is opened now
                    featuresFull.setText("SHOW LESS"); //update the status for user
                }
                else {
                    //collapse
                    featuresCollapse.setMaxLines(1); //reset lines to 1
                    featuresOpened = false; //say box is closed again
                    featuresFull.setText("SHOW MORE"); //update status for user
                }
            }
        });

    } //end of onCreate

    @Override
    protected void onResume() {
        super.onResume();

        thisRestroom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rest = dataSnapshot.getValue(Restroom.class); //grab our Restroom object
                String urinals = rest.getUrinals();
                String name = rest.getName();
                String hasHandicap = rest.getHasHandicap();  //get all the features from the clas
                String mirrors = rest.getMirrors();
                String sinks = rest.getSinks();
                String stalls = rest.getStalls();
                String urinalDividers = rest.getUrinalDividers();

                String building = getBuilding(name); //EX: Math Sciences Building
                String roomNumber = getRoomNumber(name); //EX: Floor: 4, Room: 4317

                //substring out the M, F, or A off the end of the abbreviation
                //TODO: remember that all-gender restroom abbrev. is A NOT AG as previously planned
                if (name != null && name.length() > 0) { //ensure not null and length > 0
                    name = name.substring(0, name.length() -1); //keep everything but that last letter
                }

                String gender = rest.getGender();
                String addon;                       //for labeling which gender restroom it is
                if (gender.equals("male")) {
                    addon = " - MALE";
                }
                else if (gender.equals("female")) {
                    addon = " - FEMALE";
                }
                else {
                    addon = " - ALL GENDER";
                }
                TextView bldg = (TextView) findViewById(R.id.RestroomBuilding);
                bldg.setText(building);
                setTitle(name + addon); //set the top toolbar to the restroom name

                TextView rmNum = (TextView) findViewById(R.id.RestroomFloor);
                rmNum.setText(roomNumber);

                TextView features = (TextView) findViewById(R.id.RestroomFeatures);

                String message = "Urinals: " + urinals + "\nUrinal Dividers: " + urinalDividers + "\nStalls: " + stalls + "\nHandicap Accesible: " + hasHandicap + "\n Sinks: " + sinks+ "\nMirrors: " + mirrors;
                features.setText(message); //set the new text

                float rating = Float.parseFloat(rest.getAverageReview()); //use average score to put stars
                ratingBar.setRating(rating); //set rating takes in a float only

                TextView numberReviews = (TextView) findViewById(R.id.textNumReviews);
                int numRates = Integer.parseInt(rest.getNumberOfRatings());
                if (numRates == 1) {
                    numberReviews.setText(numRates + " review"); //1 review
                }
                else {
                    numberReviews.setText(numRates + " reviews"); //0, multiple number reviews
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //error
            }
        });
    } //end of onResume

}
