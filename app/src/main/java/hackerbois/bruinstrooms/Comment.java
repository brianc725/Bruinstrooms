package hackerbois.bruinstrooms;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by brianchan on 2/16/18.
 */

@IgnoreExtraProperties
public class Comment {

    private String author;
    private String text;
    private String restroomID; //which restroom this comment is for

    public Comment() {
        //Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    /**
     * Constructor for the comments
     * @param restroomID which restroom this comment is for
     * @param author the name of the person posting the comment
     * @param text the comment message
     */
    public Comment(String restroomID, String author, String text) {
        this.restroomID = restroomID;
        this.author = author;
        this.text = text;
    }
}

// Guide for integration into our thing: https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/com/google/firebase/quickstart/database/PostDetailActivity.java
