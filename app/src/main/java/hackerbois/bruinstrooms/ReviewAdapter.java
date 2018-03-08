package hackerbois.bruinstrooms;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vince Wu on 3/8/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter {
	private ArrayList<Reviews> mReviewsList;


	ReviewAdapter(ArrayList<Reviews> arg){
		mReviewsList = arg;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
		ReviewViewHolder rvh = new ReviewViewHolder(view);
		return rvh;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		ReviewViewHolder rvh = (ReviewViewHolder)holder;
		rvh.username.setText(mReviewsList.get(position).getAuthor());
		rvh.comment.setText(mReviewsList.get(position).getText());
		rvh.timestamp.setText(mReviewsList.get(position).getTimestamp());
	}

	@Override
	public int getItemCount() {
		return mReviewsList.size();
	}

	private class ReviewViewHolder extends RecyclerView.ViewHolder{
		CardView cv;
		TextView username;
		TextView comment;
		TextView timestamp;

		public ReviewViewHolder(View itemView) {
			super(itemView);

			cv = itemView.findViewById(R.id.cv_comment_card);
			username = itemView.findViewById(R.id.tv_username);
			comment = itemView.findViewById(R.id.tv_comment);
			timestamp = itemView.findViewById(R.id.tv_timestamp);
		}
	}
}
