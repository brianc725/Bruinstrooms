package hackerbois.bruinstrooms;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Vince Wu on 3/1/2018.
 */

public class NearbyFragment extends Fragment {

	private RecyclerView mRoomView;
	private ProgressBar mPBar;
	private FirebaseDatabase mDatabase;
	private DatabaseReference restroomsRef;
	private ArrayList<Restroom> RestroomList;
	private NearbyAdapter mAdaptor;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.nearby_fragment, container, false);

		mDatabase = FirebaseDatabase.getInstance();
		RestroomList = new ArrayList<>(); //make a new array list
		restroomsRef = mDatabase.getReference("restrooms"); //grab all the restrooms
		RestroomList.clear(); //clear array list

		//initialize all member view variables
		initViews(view);

		//initialize pull to refresh (low priority)
		initLoad();

		return view;
	}


	private void initViews(View view) {
		mPBar = view.findViewById(R.id.pb_pBar);
		mRoomView = view.findViewById(R.id.rv_room_list);

		LinearLayoutManager llm = new LinearLayoutManager(getActivity());
		mRoomView.setLayoutManager(llm);

		//TODO: write the load routine to pull data from firebase (pass the resulting arraylist to adapter)
		mPBar.setVisibility(View.VISIBLE); //show loading circle


		NearbyAdapter adapter = new NearbyAdapter(RestroomList);
		mAdaptor = adapter;
		mRoomView.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		mPBar.setVisibility(View.INVISIBLE);
	}

	public void passParam(FirebaseDatabase mDatabase) {
		this.mDatabase = mDatabase;
	}

/*
	public ArrayList<Restroom> pullRoomData() {
		final ArrayList<Restroom> RestroomList = new ArrayList<>();
	//	RestroomList.clear();

		final DatabaseReference restroomsRef = mDatabase.getReference("restrooms"); //grab all the restrooms
		restroomsRef.orderByChild("name").addChildEventListener(new ChildEventListener() { //sort by name, doesn't really matter though
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
				Restroom room = dataSnapshot.getValue(Restroom.class); //get the restroom
				RestroomList.add(room); //add it to the array list
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String s) {
				Restroom room = dataSnapshot.getValue(Restroom.class);
				RestroomList.add(room);
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				Restroom room = dataSnapshot.getValue(Restroom.class);
				RestroomList.add(room);
			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String s) {
				Restroom room = dataSnapshot.getValue(Restroom.class);
				RestroomList.add(room);
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

		return RestroomList;
	}
*/

	@Override
	public void onResume() { //run all the time in background
		super.onResume();

		restroomsRef.orderByKey().addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				RestroomList.clear(); //clear ALL old onees
				for (DataSnapshot data: dataSnapshot.getChildren()) {
					Restroom room = data.getValue(Restroom.class);
					RestroomList.add(room); //load in all restrooms refreshed
				}
				mAdaptor.notifyDataSetChanged(); //update screen
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

		/*
		restroomsRef.orderByChild("name").addChildEventListener(new ChildEventListener() { //sort by name, doesn't really matter though
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
				Restroom room = dataSnapshot.getValue(Restroom.class); //get the restroom
				RestroomList.add(room); //add it to the array list
				mAdaptor.notifyDataSetChanged();
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String s) {
				Restroom room = dataSnapshot.getValue(Restroom.class);
				RestroomList.add(room); //add it to the array list
				mAdaptor.notifyDataSetChanged();
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				Restroom room = dataSnapshot.getValue(Restroom.class);
				RestroomList.add(room); //add it to the array list
				mAdaptor.notifyDataSetChanged();
			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String s) {
				Restroom room = dataSnapshot.getValue(Restroom.class);
				RestroomList.add(room); //add it to the array list
				mAdaptor.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});*/

	}

	private void initLoad(){
		//to be implemented later
		return;
	}

//	public void passTask(AsyncTaskru)
}


