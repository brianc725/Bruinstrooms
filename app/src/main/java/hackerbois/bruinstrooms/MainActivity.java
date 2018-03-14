package hackerbois.bruinstrooms;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private NearbyFragment nearbyFragment;
	private FavFragment favFragment;
    private FirebaseDatabase mDatabase;
    private DatabaseReference restroomsRef;
    private CustomViewPager mViewPager;
    //private GoogleMap mMap;

    public void FABAction(View v){
        Intent intent = new Intent(this, MapsActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize database reference
        mDatabase = FirebaseDatabase.getInstance();
        restroomsRef = mDatabase.getReference();

		nearbyFragment = new NearbyFragment();


		favFragment = new FavFragment();
		favFragment.passParam(nearbyFragment);
        nearbyFragment.passParam(mDatabase, favFragment);



        mViewPager = findViewById(R.id.fl_fragment_frame);
		ViewPagerAdapter adapter = new ViewPagerAdapter (MainActivity.this.getSupportFragmentManager());
		adapter.addFragment(nearbyFragment, "nearby");
		adapter.addFragment(favFragment, "fav");
//		adapter.addFragment(new MapsActivity(), "title");
		mViewPager.setAdapter(adapter);

        //first page that user is gonna see
        loadNearbyFragment();

        initializeBottomNavBar();

       // initializeRestrooms(); //Removed for deployment so db is not wiped every time.


//        AsyncTaskRunner runner = new AsyncTaskRunner();
//        runner.execute();
    }

    private void initializeBottomNavBar(){
		//disable shifting animation in bottom bar
		BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
		BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

		//setup listeners
		bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				switch(item.getItemId()){
					case R.id.action_bookmark:
						loadBookMarkFragment();
						return true;
					case R.id.action_nearby:
						loadNearbyFragment();
						return true;
				}
				return false;
			}
		});
	}

	private void loadBookMarkFragment(){
		mViewPager.setCurrentItem(1);
	}

	private void loadNearbyFragment(){
		mViewPager.setCurrentItem(0);
	}


    private void initializeRestrooms() {
        /*
       // test with passing in an object that we created
        Restroom test = new Restroom("Boelter West Wing", "male");
       restrooms.put("BWW", test.toFirebaseObject());

        //Restroom test2 = new Restroom("Dodd Basement AG", "all-gender");
        // restrooms.put("DBA", test2.toFirebaseObject())

        //Restroom test3 = new Restroom("Boelter Penthouse F", "female");
        //test3.setRating(4.5);
        //test3.setRating(3.0);
        //test3.setRating(3.5);
        // restroomsRef.updateChildren(restrooms);

        using set value will override object, access with child directly to update fields
        restroomsRef.child("restrooms").child(test.getName()).setValue(test);
        restroomsRef.child("restrooms").child(test2.getName()).setValue(test2);
        restroomsRef.child("restrooms").child(test3.getName()).setValue(test3);
        */


        //need to initialze once just to have it in the databaes, after that never call again
        // so when app is published we do not call initializeRestrooms() but instead only update values
        Restroom MS1347M = new Restroom("MS1347M", "male", "3", "no", "3", "no", "3", "1");
        restroomsRef.child("restrooms").child(MS1347M.getName()).setValue(MS1347M);
        Restroom MS2317M = new Restroom("MS2317M", "male", "3", "no", "2", "yes", "3", "1");
        restroomsRef.child("restrooms").child(MS2317M.getName()).setValue(MS2317M);
        Restroom MS3317M = new Restroom("MS3317M", "male", "3", "no", "3", "no", "3", "1");
        restroomsRef.child("restrooms").child(MS3317M.getName()).setValue(MS3317M);
        Restroom MS4317M = new Restroom("MS4317M", "male", "3", "no", "2", "yes", "3", "2");
        restroomsRef.child("restrooms").child(MS4317M.getName()).setValue(MS4317M);
        Restroom MS5317M = new Restroom("MS5317M", "male", "3", "no", "2", "yes", "3", "2");
        restroomsRef.child("restrooms").child(MS5317M.getName()).setValue(MS5317M);
        Restroom MS6209M = new Restroom("MS6209M", "male", "3", "no", "2", "yes", "2", "1");
        restroomsRef.child("restrooms").child(MS6209M.getName()).setValue(MS6209M);
        Restroom MS6317M = new Restroom("MS6317M", "male", "3", "no", "3", "no", "3", "1");
        restroomsRef.child("restrooms").child(MS6317M.getName()).setValue(MS6317M);
        Restroom MS7319M = new Restroom("MS7319M", "male", "3", "no", "3", "no", "3", "1");
        restroomsRef.child("restrooms").child(MS7319M.getName()).setValue(MS7319M);
        Restroom MS7205M = new Restroom("MS7205M", "male", "4", "no", "2", "no", "3", "1");
        restroomsRef.child("restrooms").child(MS7205M.getName()).setValue(MS7205M);
        Restroom MS8207M = new Restroom("MS8207M", "male", "3", "no", "3", "no", "3", "1");
        restroomsRef.child("restrooms").child(MS8207M.getName()).setValue(MS8207M);
        Restroom MS8319M = new Restroom("MS8319M", "male", "1", "yes", "3", "yes", "3", "1");
        restroomsRef.child("restrooms").child(MS8319M.getName()).setValue(MS8319M);

        //note: we do not have access to women's restrooms so these are estimates
        Restroom MS2922F = new Restroom("MS2922F", "female", "0", "no", "6", "yes", "3", "1");
        restroomsRef.child("restrooms").child(MS2922F.getName()).setValue(MS2922F);
        Restroom MS3922F = new Restroom("MS3922F", "female", "0", "no", "6", "no", "3", "1");
        restroomsRef.child("restrooms").child(MS3922F.getName()).setValue(MS3922F);
        Restroom MS4922F = new Restroom("MS4922F", "female", "0", "no", "6", "yes", "3", "1");
        restroomsRef.child("restrooms").child(MS4922F.getName()).setValue(MS4922F);
        Restroom MS4211F = new Restroom("MS4211F", "female", "0", "no", "5", "no", "3", "1");
        restroomsRef.child("restrooms").child(MS4211F.getName()).setValue(MS4211F);
        Restroom MS5209F = new Restroom("MS5209F", "female", "0", "no", "6", "yes", "3", "1");
        restroomsRef.child("restrooms").child(MS5209F.getName()).setValue(MS5209F);
        Restroom MS5922F = new Restroom("MS5922F", "female", "0", "no", "5", "no", "3", "1");
        restroomsRef.child("restrooms").child(MS5922F.getName()).setValue(MS5922F);
        Restroom MS6213F = new Restroom("MS6213F", "female", "0", "no", "6", "yes", "3", "1");
        restroomsRef.child("restrooms").child(MS6213F.getName()).setValue(MS6213F);
        Restroom MS6922F = new Restroom("MS6922F", "female", "0", "no", "5", "no", "3", "1");
        restroomsRef.child("restrooms").child(MS6922F.getName()).setValue(MS6922F);
        Restroom MS7922F = new Restroom("MS7922F", "female", "0", "no", "6", "no", "3", "1");
        restroomsRef.child("restrooms").child(MS7922F.getName()).setValue(MS7922F);
        Restroom MS7211F = new Restroom("MS7211F", "female", "0", "no", "6", "yes", "3", "1");
        restroomsRef.child("restrooms").child(MS7211F.getName()).setValue(MS7211F);
        Restroom MS8922F = new Restroom("MS8922F", "female", "0", "no", "6", "no", "3", "1");
        restroomsRef.child("restrooms").child(MS8922F.getName()).setValue(MS8922F);

        Restroom BH7269M = new Restroom("BH7269M", "male", "3", "yes", "4", "yes", "3", "2");
        restroomsRef.child("restrooms").child(BH7269M.getName()).setValue(BH7269M);
        Restroom BH7754M = new Restroom("BH7754M", "male", "3", "yes", "1", "yes", "3", "1");
        restroomsRef.child("restrooms").child(BH7754M.getName()).setValue(BH7754M);
        Restroom BH7423F = new Restroom("BH7423F", "female", "0", "no", "4", "no", "3", "2");
        restroomsRef.child("restrooms").child(BH7423F.getName()).setValue(BH7423F);
        Restroom BH7559F = new Restroom("BH7559F", "female", "0", "no", "4", "no", "3", "1");
        restroomsRef.child("restrooms").child(BH7559F.getName()).setValue(BH7559F);
        Restroom BH6269M = new Restroom("BH6269M", "male", "3", "no", "4", "yes", "3", "2");
        restroomsRef.child("restrooms").child(BH6269M.getName()).setValue(BH6269M);
        Restroom BH6754M = new Restroom("BH6754M", "male", "3", "no", "4", "yes", "3", "2");
        restroomsRef.child("restrooms").child(BH6754M.getName()).setValue(BH6754M);
        Restroom BH6423F = new Restroom("BH6423F", "female", "0", "no", "5", "yes", "3", "2");
        restroomsRef.child("restrooms").child(BH6423F.getName()).setValue(BH6423F);
        Restroom BH6559F = new Restroom("BH6559F", "female", "0", "no", "4", "yes", "3", "2");
        restroomsRef.child("restrooms").child(BH6559F.getName()).setValue(BH6559F);
        Restroom BH5269M = new Restroom("BH5269M", "male", "3", "no", "3", "yes", "2", "2");
        restroomsRef.child("restrooms").child(BH5269M.getName()).setValue(BH5269M);
        Restroom BH5754M = new Restroom("BH5754M", "male", "2", "yes", "1", "yes", "2", "2");
        restroomsRef.child("restrooms").child(BH5754M.getName()).setValue(BH5754M);
        Restroom BH5423F = new Restroom("BH5423F", "female", "0", "no", "4", "no", "3", "2");
        restroomsRef.child("restrooms").child(BH5423F.getName()).setValue(BH5423F);
        Restroom BH5559F = new Restroom("BH5559F", "female", "0", "no", "5", "no", "3", "2");
        restroomsRef.child("restrooms").child(BH5559F.getName()).setValue(BH5559F);
        Restroom BH4269M = new Restroom("BH4269M", "male", "3", "no", "3", "yes", "2", "2");
        restroomsRef.child("restrooms").child(BH4269M.getName()).setValue(BH4269M);
        Restroom BH4754M = new Restroom("BH4754M", "male", "3", "yes", "2", "no", "3", "1");
        restroomsRef.child("restrooms").child(BH4754M.getName()).setValue(BH4754M);
        Restroom BH4423F = new Restroom("BH4423F", "female", "0", "no", "5", "yes", "2", "2");
        restroomsRef.child("restrooms").child(BH4423F.getName()).setValue(BH4423F);
        Restroom BH4559F = new Restroom("BH4559F", "female", "0", "no", "5", "no", "2", "2");
        restroomsRef.child("restrooms").child(BH4559F.getName()).setValue(BH4559F);
        Restroom BH3269 = new Restroom("BH3269M", "male", "3", "no", "4", "yes", "3", "2");
        restroomsRef.child("restrooms").child(BH3269.getName()).setValue(BH3269);
        Restroom BH3754M = new Restroom("BH3754M", "male", "2", "no", "2", "yes", "3", "1");
        restroomsRef.child("restrooms").child(BH3754M.getName()).setValue(BH3754M);
        Restroom BH3423F = new Restroom("BH3423F", "female", "0", "no", "4", "yes", "2", "2");
        restroomsRef.child("restrooms").child(BH3423F.getName()).setValue(BH3423F);
        Restroom BH3559F = new Restroom("BH3559F", "female", "0", "no", "4", "no", "3", "2");
        restroomsRef.child("restrooms").child(BH3559F.getName()).setValue(BH3559F);
        Restroom BH2553M = new Restroom("BH2553M", "male", "3", "no", "2", "no", "2", "0");
        restroomsRef.child("restrooms").child(BH2553M.getName()).setValue(BH2553M);
        Restroom BH2754F = new Restroom("BH2754F", "female", "0", "no", "4", "no", "3", "3");
        restroomsRef.child("restrooms").child(BH2754F.getName()).setValue(BH2754F);
        Restroom BH1765M = new Restroom("BH1765M", "male", "3", "no", "2", "no", "2", "0");
        restroomsRef.child("restrooms").child(BH1765M.getName()).setValue(BH1765M);
        Restroom BH1553F = new Restroom("BH1553F", "female", "0", "no", "4", "no", "3", "2");
        restroomsRef.child("restrooms").child(BH1553F.getName()).setValue(BH1553F);

        Restroom EIV67118M = new Restroom("EIV67118M", "male", "3", "yes", "3", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV67118M.getName()).setValue(EIV67118M);
        Restroom EIV63157M = new Restroom("EIV63157M", "male", "3", "yes", "3", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV63157M.getName()).setValue(EIV63157M);
        Restroom EIV67114F = new Restroom("EIV67114F", "female", "0", "no", "4", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV67114F.getName()).setValue(EIV67114F);
        Restroom EIV63158F = new Restroom("EIV63158F", "female", "0", "no", "4", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV63158F.getName()).setValue(EIV63158F);
        Restroom EIV57118M = new Restroom("EIV57118M", "male", "3", "yes", "3", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV57118M.getName()).setValue(EIV57118M);
        Restroom EIV53157M = new Restroom("EIV53157M", "male", "3", "yes", "3", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV53157M.getName()).setValue(EIV53157M);
        Restroom EIV57114F = new Restroom("EIV57114F", "female", "0", "no", "4", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV57114F.getName()).setValue(EIV57114F);
        Restroom EIV53158F = new Restroom("EIV53158F", "female", "0", "no", "4", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV53158F.getName()).setValue(EIV53158F);
        Restroom EIV47118M = new Restroom("EIV47118M", "male", "3", "yes", "3", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV47118M.getName()).setValue(EIV47118M);
        Restroom EIV43157M = new Restroom("EIV43157M", "male", "3", "yes", "3", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV43157M.getName()).setValue(EIV43157M);
        Restroom EIV47114F = new Restroom("EIV47114F", "female", "0", "no", "4", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV47114F.getName()).setValue(EIV47114F);
        Restroom EIV43158F = new Restroom("EIV43158F", "female", "0", "no", "4", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV43158F.getName()).setValue(EIV43158F);
        Restroom EIV37118M = new Restroom("EIV37118M", "male", "3", "yes", "3", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV37118M.getName()).setValue(EIV37118M);
        Restroom EIV33157M = new Restroom("EIV33157M", "male", "3", "yes", "3", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV33157M.getName()).setValue(EIV33157M);
        Restroom EIV37114F = new Restroom("EIV37114F", "female", "0", "no", "4", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV37114F.getName()).setValue(EIV37114F);
        Restroom EIV33158F = new Restroom("EIV33158F", "female", "0", "no", "4", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV33158F.getName()).setValue(EIV33158F);
        Restroom EIV17118M = new Restroom("EIV17118M", "male", "4", "yes", "2", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV17118M.getName()).setValue(EIV17118M);
        Restroom EIV13146M = new Restroom("EIV13146M", "male", "3", "yes", "3", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV13146M.getName()).setValue(EIV13146M);
        Restroom EIV17114F = new Restroom("EIV17114F", "female", "0", "no", "4", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV17114F.getName()).setValue(EIV17114F);
        Restroom EIV13144F = new Restroom("EIV13144F", "female", "0", "no", "4", "no", "3", "3");
        restroomsRef.child("restrooms").child(EIV13144F.getName()).setValue(EIV13144F);

        Restroom PAB1707M = new Restroom("PAB1707M", "male", "6", "yes", "6", "yes", "4", "4");
        restroomsRef.child("restrooms").child(PAB1707M.getName()).setValue(PAB1707M);
        Restroom PAB1710F = new Restroom("PAB1710F", "female", "0", "no", "6", "yes", "4", "4");
        restroomsRef.child("restrooms").child(PAB1710F.getName()).setValue(PAB1710F);

        Restroom ACK2011A = new Restroom("ACK2011A", "all-gender", "1", "no", "1", "no", "1", "1");
        restroomsRef.child("restrooms").child(ACK2011A.getName()).setValue(ACK2011A);
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, ArrayList<Restroom>> {
        ArrayList <Restroom> RestroomList;
        ProgressDialog progressDialog;

        @Override
        protected ArrayList<Restroom> doInBackground(String... params) {
            /*
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                int time = Integer.parseInt(params[0])*1000;

                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }*/
            RestroomList = new ArrayList<>();
            RestroomList.clear();

            final DatabaseReference restroomsRef = mDatabase.getReference("restrooms"); //grab all the restrooms
            restroomsRef.orderByChild("name").addChildEventListener(new ChildEventListener() { //sort by name, doesn't really matter though
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    RestroomList.clear();
                    Restroom room = dataSnapshot.getValue(Restroom.class); //get the restroom
                    RestroomList.add(room); //add it to the array list
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    RestroomList.clear();
                    Restroom room = dataSnapshot.getValue(Restroom.class);
                    RestroomList.add(room);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    RestroomList.clear();
                    Restroom room = dataSnapshot.getValue(Restroom.class);
                    RestroomList.add(room);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    RestroomList.clear();
                    Restroom room = dataSnapshot.getValue(Restroom.class);
                    RestroomList.add(room);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return RestroomList;
        }


        //@Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
          /* progressDialog.dismiss();
            finalResult.setText(result);*/
        }


        @Override
        protected void onPreExecute() {
           /*
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "ProgressDialog",
                    "Wait for "+time.getText().toString()+ " seconds"); */
        }


        @Override
        protected void onProgressUpdate(String... text) {
           // finalResult.setText(text[0]);

        }
    }



    /*
    TODO: Firebase resources to delete later
    https://firebase.google.com/docs/database/android/start/
    https://www.raywenderlich.com/139322/firebase-tutorial-getting-started-2
    https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/com/google/firebase/quickstart/database/NewPostActivity.java
    >>> https://shubhank101.github.io/iOSAndroidChaosOverFlow/2016/06/todo-app-with-firebase-part-1-(Android)
    https://firebase.google.com/docs/database/admin/save-data
     */
}
