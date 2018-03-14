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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Vince Wu on 3/14/2018.
 */

public class FavFragment extends android.support.v4.app.Fragment {
	private RecyclerView mRoomView;
	private ProgressBar mPBar;
	private ArrayList<Restroom> FavRoomList;
	private FavAdapter mAdaptor;
	private NearbyFragment nearbyFragment;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.nearby_fragment, container, false);

		FavRoomList = new ArrayList<>(); //make a new array list
		FavRoomList.clear(); //clear array list

		//initialize all member view variables
		initViews(view);

		return view;
	}

	private void initViews(View view) {
		mPBar = view.findViewById(R.id.pb_pBar);
		mRoomView = view.findViewById(R.id.rv_room_list);

		LinearLayoutManager llm = new LinearLayoutManager(getActivity());
		mRoomView.setLayoutManager(llm);

		mPBar.setVisibility(View.VISIBLE); //show loading circle

		FavAdapter adapter = new FavAdapter(nearbyFragment.getFavRoomList());
		mAdaptor = adapter;
		mRoomView.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		mPBar.setVisibility(View.INVISIBLE);
	}

	public void passParam(NearbyFragment nearbyFragment) {
		this.nearbyFragment = nearbyFragment;
	}

	public void refresh() {
		mAdaptor.refreshDataSource(nearbyFragment.getFavRoomList());
		mAdaptor.notifyDataSetChanged();
	}

	@Override
	public void onResume() { //run all the time in background

		super.onResume();
	}


}
