package chatapp.magnus.chatapp;

import android.app.Application;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Magnus on 16-12-2017.
 */

public class SingletonApplications extends Application {

    public static FirebaseUser user;


    @Override
    public void onCreate() {
        super.onCreate();

    }
}
