package hackerbois.bruinstrooms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
	private RecyclerView mReviewView;
	private ReviewAdapter mAdapter;
	private TextView mNoReview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_activity);

		Intent intent = getIntent();

		restroomName = intent.getStringExtra("ROOM_NAME");
		mDatabase = FirebaseDatabase.getInstance();
		ReviewsList = new ArrayList<>(); //make a new array list
		reviewsRef = mDatabase.getReference().child("reviews/" + restroomName); //grab all the Reviews
		ReviewsList.clear(); //clear array list
		mNoReview = findViewById(R.id.tv_no_review);

		//setup RecyclerView
		mReviewView = findViewById(R.id.rv_comment_list);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		mReviewView.setLayoutManager(llm);
		mAdapter = new ReviewAdapter(ReviewsList);
		mReviewView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();

		setTitle(Helper.getLongName(restroomName) + " Reviews");

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
					mAdapter.notifyDataSetChanged();
					if(ReviewsList.size() > 0){
						mNoReview.setVisibility(View.INVISIBLE);
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

	}
}
