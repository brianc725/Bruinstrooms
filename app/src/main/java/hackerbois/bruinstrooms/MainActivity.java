package hackerbois.bruinstrooms;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference restroomsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize database reference
        mDatabase = FirebaseDatabase.getInstance();
        restroomsRef = mDatabase.getReference();

        //first page that user is gonna see
        loadNearbyFragment();

        initializeBottomNavBar();

        initializeRestrooms(); //TODO: Remove this call before publishing app or else data will
                                //overwrite every time someone opens the app

//        AsyncTaskRunner runner = new AsyncTaskRunner();
//        runner.execute(); //TODO: VINCE THIS IS TEMPORARY
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
					case R.id.action_activity:
						loadActivityFragment();
						return true;
					case R.id.action_bookmark:
						loadBookMarkFragment();
						return true;
					case R.id.action_me:
						loadMeFragment();
						return true;
					case R.id.action_nearby:
						loadNearbyFragment();
						return true;
					case R.id.action_search:
						loadSearchFragment();
						return true;
				}
				return false;
			}
		});
	}

	private void loadActivityFragment(){
		TesterFragment nearbyFragment = new TesterFragment();
		nearbyFragment.setText("This is Activity");
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.fl_fragment_frame, nearbyFragment);
		ft.commit();
	}

	private void loadBookMarkFragment(){
		TesterFragment nearbyFragment = new TesterFragment();
		nearbyFragment.setText("This is BookMark");
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.fl_fragment_frame, nearbyFragment);
		ft.commit();
	}

	private void loadNearbyFragment(){
		NearbyFragment nearbyFragment = new NearbyFragment();
		nearbyFragment.passParam(mDatabase);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.fl_fragment_frame, nearbyFragment);
		ft.commit();
	}

	private void loadMeFragment(){
		TesterFragment nearbyFragment = new TesterFragment();
		nearbyFragment.setText("This is Me");
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.fl_fragment_frame, nearbyFragment);
		ft.commit();
	}

	private void loadSearchFragment(){
		TesterFragment nearbyFragment = new TesterFragment();
		nearbyFragment.setText("This is Search");
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.fl_fragment_frame, nearbyFragment);
		ft.commit();
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
        //continue with female 5th floor to 8th floor


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
