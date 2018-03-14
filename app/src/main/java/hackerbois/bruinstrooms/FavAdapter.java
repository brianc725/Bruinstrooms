package hackerbois.bruinstrooms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CheckableImageButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Vince Wu on 3/14/2018.
 */

public class FavAdapter extends Adapter{
	private ArrayList<Restroom> FavRoomList;

	FavAdapter(ArrayList<Restroom> arg){
		FavRoomList = arg;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final ViewGroup parentCopy = parent;
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_item, parent, false);
		final NearbyViewHolder nvh = new NearbyViewHolder(view);
		nvh.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(parentCopy.getContext(), RestroomActivity.class);
				intent.putExtra("ROOM_NAME", FavRoomList.get(nvh.getLayoutPosition()).getName());
				parentCopy.getContext().startActivity(intent);
			}
		});
		return nvh;
	}

	public ArrayList<Restroom> getFavRoomList(){
		return FavRoomList;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		NearbyViewHolder nvh = (NearbyViewHolder) holder;
		Restroom rest = FavRoomList.get(position);
		String unformatted = rest.getName();
		//String gender = RestroomList.get(position).getGender();
		nvh.roomName.setText(Helper.getLongName(unformatted)); //update room name to proper format
		nvh.roomBuilding.setText(Helper.getBuilding(unformatted)); //update room building to proper format
		if (!rest.getEmergencyStatus().equals("none")) { //if restroom has emergency turn red
			nvh.roomName.setTextColor(Color.RED);
		}
		else {
			nvh.roomName.setTextColor(Color.BLACK);
		}
		nvh.favStar.setVisibility(View.GONE);
		nvh.roomRating.setVisibility(View.GONE);
	}

	@Override
	public int getItemCount() {
		return FavRoomList.size();
	}

	//a method to refresh data source
	public void refreshDataSource(ArrayList<Restroom> newSource){
		FavRoomList = newSource;
		notifyDataSetChanged();
	}

	private class NearbyViewHolder extends RecyclerView.ViewHolder{
		CardView cv;
		CheckableImageButton favStar;
		TextView roomName;
		TextView roomBuilding;
		TextView roomRating;

		NearbyViewHolder(View itemView) {
			super(itemView);
			cv = itemView.findViewById(R.id.cv_room_card);
			favStar = itemView.findViewById(R.id.iv_room_photo);
			roomName = itemView.findViewById(R.id.tv_room_name);
			roomBuilding = itemView.findViewById(R.id.tv_room_building);
			roomRating = itemView.findViewById(R.id.tv_room_rating);
		}
	}
}
