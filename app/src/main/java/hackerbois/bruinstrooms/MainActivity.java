package hackerbois.bruinstrooms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference restroomsRef;
    private Map<String, Object> restrooms = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize database reference
        mDatabase = FirebaseDatabase.getInstance();
        restroomsRef = mDatabase.getReference();

        initializeRestrooms();
    }

    private void initializeRestrooms() {
        //test with passing in an object that we created
        Restroom test = new Restroom("Boelter West Wing", "male");
       // restrooms.put("BWW", test.toFirebaseObject());

        Restroom test2 = new Restroom("Dodd Basement AG", "all-gender");
        // restrooms.put("DBA", test2.toFirebaseObject())

        Restroom test3 = new Restroom("Boelter Penthouse F", "female");
        test3.setRating(4.5);
        test3.setRating(3.0);
        test3.setRating(3.5);
        // restroomsRef.updateChildren(restrooms);

        //using set value will override object, access with child directly to update fields
        restroomsRef.child("restrooms").child(test.getName()).setValue(test);
        restroomsRef.child("restrooms").child(test2.getName()).setValue(test2);
        restroomsRef.child("restrooms").child(test3.getName()).setValue(test3);
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
