package chatapp.magnus.chatapp;

import android.app.Application;

import com.google.firebase.auth.FirebaseUser;

import chatapp.magnus.chatapp.posts.UserDTO;

/**
 * Created by Magnus on 16-12-2017.
 */

public class SingletonApplications extends Application {

    public static FirebaseUser fbUser;
    public static UserDTO localUser;
    public static String commentID;


    @Override
    public void onCreate() {
        super.onCreate();

    }
}
