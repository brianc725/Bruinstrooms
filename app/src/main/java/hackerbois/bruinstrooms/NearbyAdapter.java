package hackerbois.bruinstrooms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CheckableImageButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vince Wu on 3/1/2018.
 */

public class NearbyAdapter extends RecyclerView.Adapter {
	private ArrayList<Restroom> RestroomList;
	private ArrayList<Restroom> FavRoomList;
	public FavFragment favFragment;

	NearbyAdapter(ArrayList<Restroom> arg, FavFragment favFragment){
		RestroomList = arg;
		FavRoomList = new ArrayList<>();
		this.favFragment = favFragment;
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
				intent.putExtra("ROOM_NAME", RestroomList.get(nvh.getLayoutPosition()).getName());
				parentCopy.getContext().startActivity(intent);
			}
		});
		nvh.favStar.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("RestrictedApi")
			@Override
			public void onClick(View view) {
				((CheckableImageButton)view).toggle();
				boolean hasIt = false;
				for(int i = 0; i < FavRoomList.size(); i++){
					if(FavRoomList.get(i).getName().equals(RestroomList.get(nvh.getLayoutPosition()).getName())){
						FavRoomList.remove(FavRoomList.get(i));
						hasIt = true;
					}
				}
				if(!hasIt){
					FavRoomList.add(RestroomList.get(nvh.getLayoutPosition()));
				}
				favFragment.refresh();
			}
		});
		return nvh;
	}

	public ArrayList<Restroom> getFavRoomList(){
		return FavRoomList;
	}

	@SuppressLint("RestrictedApi")
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		NearbyViewHolder nvh = (NearbyViewHolder) holder;
		Restroom rest = RestroomList.get(position);
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

		nvh.favStar.setChecked(false);
		for(int i = 0; i < FavRoomList.size(); i++){
			if(FavRoomList.get(i).getName().equals(unformatted)){
				nvh.favStar.setChecked(true);
				break;
			}
		}

		nvh.roomRating.setText(rest.getAverageReview() + "/5.0"); //set to show rating on cards
	}

	@Override
	public int getItemCount() {
		return RestroomList.size();
	}

	//a method to refresh data source
	public void refreshDataSource(ArrayList<Restroom> newSource){
		RestroomList = newSource;
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
