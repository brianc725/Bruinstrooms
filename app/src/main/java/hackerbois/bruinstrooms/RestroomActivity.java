package hackerbois.bruinstrooms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RestroomActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private DatabaseReference thisRestroom;
    private String restroomName;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restroom);

        restroomName = "MS4317M"; //TODO: temporary place holder, when intent calls this activity pass in the restroom name

        database = FirebaseDatabase.getInstance();

        ref = database.getReference();
        thisRestroom = ref.child("restrooms/" + restroomName);
    } //end of onCreate

    @Override
    protected void onResume() {
        super.onResume();

        thisRestroom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Restroom rest = dataSnapshot.getValue(Restroom.class); //grab our Restroom object
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
                TextView title = (TextView) findViewById(R.id.RestroomName);
                title.setText(name + addon); //TODO: Remove this later, no longer needed
                setTitle(name + addon); //set the top toolbar to the restroom name

                TextView features = (TextView) findViewById(R.id.RestroomFeatures);

                String message = "Urinals: " + urinals + "\nUrinal Dividers: " + urinalDividers + "\nStalls: " + stalls + "\nHandicap Accesible: " + hasHandicap + "\n Sinks: " + sinks+ "\nMirrors: " + mirrors;
                features.setText(message); //set the new text
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //error
            }
        });
    } //end of onResume

}
