package hackerbois.bruinstrooms;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vince Wu on 3/1/2018.
 */

public class NearbyAdapter extends RecyclerView.Adapter {
	private ArrayList<Restroom> RestroomList;

	NearbyAdapter(ArrayList<Restroom> arg){
		RestroomList = arg;
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
		return nvh;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		NearbyViewHolder nvh = (NearbyViewHolder) holder;
		String unformatted = RestroomList.get(position).getName();
		//String gender = RestroomList.get(position).getGender();
		nvh.roomName.setText(Helper.getLongName(unformatted)); //update room name to proper format
		nvh.roomBuilding.setText(Helper.getBuilding(unformatted)); //update room building to proper format
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
		ImageView roomPhoto;
		TextView roomName;
		TextView roomBuilding;

		NearbyViewHolder(View itemView) {
			super(itemView);
			cv = itemView.findViewById(R.id.cv_room_card);
			roomPhoto = itemView.findViewById(R.id.iv_room_photo);
			roomName = itemView.findViewById(R.id.tv_room_name);
			roomBuilding = itemView.findViewById(R.id.tv_room_building);
		}
	}
}
