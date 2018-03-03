package hackerbois.bruinstrooms;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by brianchan on 2/16/18.
 */

@IgnoreExtraProperties
public class Reviews {

    private String author;
    private String text;
    private String restroomID; //which restroom this review is for

    public Reviews() {
        //Default constructor required for calls to DataSnapshot.getValue(Reviews.class)
    }

    /**
     * Constructor for the reviews
     * @param restroomID which restroom this review is for
     * @param author the name of the person posting the review
     * @param text the review message
     */
    public Reviews(String restroomID, String author, String text) {
        this.restroomID = restroomID;
        this.author = author;
        this.text = text;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getText() {
        return this.text;
    }

    public String getRestroomID() {
        return this.restroomID;
    }
}

// Guide for integration into our thing: https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/com/google/firebase/quickstart/database/PostDetailActivity.java
