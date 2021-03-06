package hackerbois.bruinstrooms;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RestroomActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private DatabaseReference thisRestroom;
    private String restroomName;
    private Restroom rest; //the current restroom object
    private RatingBar ratingBar;

    private LinearLayout featuresLayout;
    private boolean featuresOpened = false;
    private String formattedMessage;

    private TextView featuresCollapse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restroom);

        Intent intent = getIntent();
        restroomName = intent.getStringExtra("ROOM_NAME"); //TODO: temporary place holder, when intent calls this activity pass in the restroom name
        database = FirebaseDatabase.getInstance();

        ref = database.getReference();
        thisRestroom = ref.child("restrooms/" + restroomName);

        ratingBar = (RatingBar) findViewById(R.id.RatingBar); //get the rating bar object

        featuresLayout = (LinearLayout) findViewById(R.id.featuresContainer);

        featuresCollapse = (TextView) findViewById(R.id.featuresCollapse);

        /*
        featuresLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView featuresCollapse = (TextView) findViewById(R.id.featuresCollapse);
                TextView featuresFull = (TextView) findViewById(R.id.featuresFull);

                if (featuresOpened == false) {
                    //expand
                    featuresCollapse.setText("Amenities \n" + formattedMessage);
                 //   featuresCollapse.setMaxLines(Integer.MAX_VALUE); //expand to add more lines
                    featuresOpened = false; //say that the box is opened now
                 //   featuresFull.setText("SHOW LESS"); //update the status for user
                }

                else {
                    //collapse
                    featuresCollapse.setMaxLines(2); //reset lines to 1
                    featuresOpened = false; //say box is closed again
                    featuresFull.setText("SHOW MORE"); //update status for user
                }
            }
        }); */

    } //end of onCreate

    @Override
    protected void onResume() {
        super.onResume();
        final Button EmergencyButton = (Button) findViewById(R.id.EmergencyButton);
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

                String building = Helper.getBuilding(name); //EX: Math Sciences Building
                String roomNumber = Helper.getRoomNumber(name); //EX: Floor: 4, Room: 4317

                //String gender = rest.getGender();
                String fullname = Helper.getLongName(name); //get expanded restroom name
                TextView bldg = (TextView) findViewById(R.id.RestroomBuilding);
                bldg.setText(building);
                setTitle(fullname); //set the top toolbar to the restroom name

                TextView rmNum = (TextView) findViewById(R.id.RestroomFloor);
                rmNum.setText(roomNumber);

                //format the strings for output
                urinals = "Urinals: " + urinals;
                urinalDividers = "Urinal Dividers: " + urinalDividers;
                stalls = "Stalls:  " + stalls;
                hasHandicap = " Handicap Accessible: " + hasHandicap;
                mirrors = "Mirrors: " + mirrors;
                sinks = "Sinks: " + sinks;

                //format message for amenities section
                formattedMessage = String.format("%-30.30s  %-30.30s%n", urinals, urinalDividers);
                formattedMessage += String.format("%-30.30s  %-30.30s%n", stalls, hasHandicap);
                formattedMessage += String.format("%-30.30s  %-30.30s", mirrors, sinks);

                featuresCollapse.setText("Amenities \n" + formattedMessage);

                float rating = Float.parseFloat(rest.getAverageReview()); //use average score to put stars
                ratingBar.setRating(rating); //set rating takes in a float only

                TextView numberReviews = (TextView) findViewById(R.id.textNumReviews);
                int numRates = Integer.parseInt(rest.getNumberOfRatings());
                if (numRates == 1) {
                    numberReviews.setText(numRates + " rating"); //1 rating
                }
                else {
                    numberReviews.setText(numRates + " ratings"); //0 or multiple number ratings
                }

                if (!rest.getEmergencyStatus().equals("none")){ //we have emergency
                    EmergencyButton.setText("View Emergency"); //change button to view current emergency
                    EmergencyButton.setTextColor(Color.RED);
                }
                else {
                    EmergencyButton.setText("Send Emergency"); //change button to view current emergency
                    EmergencyButton.setTextColor(Color.BLACK);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //error
            }
        });
    } //end of onResume

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

    public void ReviewSubmission(View view) {
        EditText revMessage = (EditText) findViewById(R.id.editReview);
        final String message = revMessage.getText().toString(); //get the message from the text box

        if (message.matches("")) //make sure not empty string
        {
            Toast.makeText(this, "You did not enter a review.", Toast.LENGTH_SHORT).show();
            return;
        }

        Date currentTime = Calendar.getInstance().getTime();
        final SimpleDateFormat formatTime = new SimpleDateFormat("MM-dd-yy HH:mm:ss");
        final String timestamp = formatTime.format(currentTime);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(RestroomActivity.this);
        edittext.setHint("Name");

        builder.setMessage("Enter a name with your review: ");
        builder.setTitle("Name Entry (Optional)");

        LinearLayout parentLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        int left = Helper.dpToPx(20); //layout params takes px values not dp
        int top = Helper.dpToPx(0);
        int right = Helper.dpToPx(20);
        int bottom = Helper.dpToPx(0);

        layoutParams.setMargins(left, top, right, bottom); //make edit text more centered
        edittext.setLayoutParams(layoutParams); //set the layout params
        parentLayout.addView(edittext); //add the edit text to the linear layout

        builder.setView(parentLayout); //add the entire linear layout with edittext inside to the dialog box
//        builder.setView(edittext);

        builder.setPositiveButton("Send Name", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String reviewer = edittext.getText().toString();

                if (reviewer.matches(""))
                {
                    reviewer = "anonymous"; //if no name entered but they still pressed entered just make anon
                }

                final Reviews review = new Reviews(restroomName, reviewer, message, timestamp); //make a review with reviewer name
                final DatabaseReference currentRef = ref.child("reviews").child(restroomName);

                currentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long offset = dataSnapshot.getChildrenCount(); //find the offset value so no overwrite
                        String off = String.valueOf(offset);
                        currentRef.child(off).setValue(review); //write the new review without overwriting
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        builder.setNegativeButton("Be Anonymous", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final Reviews review = new Reviews(restroomName, "anonymous", message, timestamp); //make a review anonymous
                final DatabaseReference currentRef = ref.child("reviews").child(restroomName);

                currentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long offset = dataSnapshot.getChildrenCount(); //find the offset value so no overwrite
                        String off = String.valueOf(offset);
                        currentRef.child(off).setValue(review); //write the new review without overwriting
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        revMessage.setText(""); //reset edit text

    }

    public void viewReview(View view){
    	Intent intent = new Intent(this, ReviewActivity.class);
    	intent.putExtra("ROOM_NAME", restroomName);
    	this.startActivity(intent);
	}

    public void showEmergencyDialog(View view) {
        // setup the alert builder
        final Button EmergencyButton = (Button) findViewById(R.id.EmergencyButton);
        if (rest.getEmergencyStatus().equals("none")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose an Emergency");

            // add a list
            String[] options = {"No toilet paper", "No paper towels", "Clogged toilet", "Flooded floor", "Clogged sink", "General Emergency"}; //
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            rest.setEmergencyStatus("No toilet paper"); //set the new status based on the user choice
                            ref.child("restrooms").child(rest.getName()).setValue(rest); //update the db
                            EmergencyButton.setText("View Emergency"); //change button to view current emergency
                            EmergencyButton.setTextColor(Color.RED);
                            break;
                        case 1:
                            rest.setEmergencyStatus("No paper towels");
                            ref.child("restrooms").child(rest.getName()).setValue(rest);
                            EmergencyButton.setText("View Emergency"); //change button to view current emergency
                            EmergencyButton.setTextColor(Color.RED);
                            break;
                        case 2:
                            rest.setEmergencyStatus("Clogged toilet");
                            ref.child("restrooms").child(rest.getName()).setValue(rest);
                            EmergencyButton.setText("View Emergency"); //change button to view current emergency
                            EmergencyButton.setTextColor(Color.RED);
                            break;
                        case 3:
                            rest.setEmergencyStatus("Flooded floor");
                            ref.child("restrooms").child(rest.getName()).setValue(rest);
                            EmergencyButton.setText("View Emergency"); //change button to view current emergency
                            EmergencyButton.setTextColor(Color.RED);
                            break;
                        case 4:
                            rest.setEmergencyStatus("Clogged sink");
                            ref.child("restrooms").child(rest.getName()).setValue(rest);
                            EmergencyButton.setText("View Emergency"); //change button to view current emergency
                            EmergencyButton.setTextColor(Color.RED);
                            break;
                        case 5:
                            rest.setEmergencyStatus("General emergency");
                            ref.child("restrooms").child(rest.getName()).setValue(rest);
                            EmergencyButton.setText("View Emergency"); //change button to view current emergency
                            EmergencyButton.setTextColor(Color.RED);
                            break;
                        default:
                            rest.setEmergencyStatus("none");
                            ref.child("restrooms").child(rest.getName()).setValue(rest);
                    }
                }
            });

            // create and show the alert dialog
            AlertDialog dialog = builder.create(); //create the dialog box to pop up
            dialog.show(); //show it to user

        }
        else { //already is an emergency so resolve it
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Current Emergency:\n" + rest.getEmergencyStatus());

            builder.setPositiveButton("Mark as Resolved", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    rest.setEmergencyStatus("none"); //reset emergency status to none
                    ref.child("restrooms").child(rest.getName()).setValue(rest); //update the db

                    EmergencyButton.setText("Send Emergency"); //change button to view current emergency
                    EmergencyButton.setTextColor(Color.BLACK);
                }
            });

            // create and show the alert dialog
            AlertDialog dialog = builder.create(); //create the dialog box to pop up
            dialog.show(); //show it to user


        }
    }


}
