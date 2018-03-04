package hackerbois.bruinstrooms;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Vince Wu on 3/3/2018.
 */

public class ReviewActivity extends AppCompatActivity {
	private FirebaseDatabase mDatabase;
	private DatabaseReference reviewsRef;
	private ArrayList<Reviews> ReviewsList;  //reviews stored oldest --> newest
	private String restroomName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_activity);

		restroomName = "MS4317M"; //TODO: temporary place holder, when intent calls this activity pass in the restroom name
		mDatabase = FirebaseDatabase.getInstance();
		ReviewsList = new ArrayList<>(); //make a new array list
		reviewsRef = mDatabase.getReference().child("reviews/" + restroomName); //grab all the Reviews
		ReviewsList.clear(); //clear array list

		setTitle("Reviews");

	}

	//TODO: VINCE this is never being called.....
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
		setContentView(R.layout.review_activity);

		restroomName = "MS4317M"; //TODO: temporary place holder, when intent calls this activity pass in the restroom name
		mDatabase = FirebaseDatabase.getInstance();
		ReviewsList = new ArrayList<>(); //make a new array list
		reviewsRef = mDatabase.getReference().child("reviews/" + restroomName); //grab all the Reviews
		ReviewsList.clear(); //clear array list

		setTitle("Reviews");
	}

	@Override
	protected void onResume() { //run all the time in background
		super.onResume();

		reviewsRef.orderByValue().addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				int childs = (int) dataSnapshot.getChildrenCount();

				for (int i = 0; i < childs; i++) {
					Reviews rev = dataSnapshot.child(String.valueOf(i)).getValue(Reviews.class); //get each child starting at "0" to "n-1"
					ReviewsList.add(rev); //add to the list
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

	}
}
