package hackerbois.bruinstrooms;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Vince Wu on 3/3/2018.
 */

public class ReviewActivity extends AppCompatActivity {
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
		setContentView(R.layout.review_activity);
		setTitle("Reviews");
	}
}
