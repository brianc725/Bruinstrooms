package hackerbois.bruinstrooms;

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
	ArrayList<Restroom> RestroomList;

	NearbyAdapter(ArrayList<Restroom> arg){
		RestroomList = arg;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_item, parent, false);
		NearbyViewHolder nvh = new NearbyViewHolder(view);
		return nvh;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		NearbyViewHolder nvh = (NearbyViewHolder) holder;
		String unformatted = RestroomList.get(position).getName();
		String gender = RestroomList.get(position).getGender();
		nvh.roomName.setText(Helper.getLongName(unformatted, gender)); //update room name to proper format
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

		public NearbyViewHolder(View itemView) {
			super(itemView);
			cv = itemView.findViewById(R.id.cv_room_card);
			roomPhoto = itemView.findViewById(R.id.iv_room_photo);
			roomName = itemView.findViewById(R.id.tv_room_name);
			roomBuilding = itemView.findViewById(R.id.tv_room_building);
		}
	}
}
