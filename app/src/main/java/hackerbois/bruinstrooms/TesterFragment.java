package hackerbois.bruinstrooms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Vince Wu on 2/24/2018.
 */

public class TesterFragment extends Fragment {
	//member variables
	String text = "default text";

	public void setText(String text){
		//set test fragment text
		this.text = text;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.tester_fragment, container, false);
		TextView testText = fragmentView.findViewById(R.id.tv_tester_text);
		testText.setText(text);
		return fragmentView;
	}
}
