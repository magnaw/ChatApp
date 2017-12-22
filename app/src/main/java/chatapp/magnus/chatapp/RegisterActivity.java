package chatapp.magnus.chatapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import chatapp.magnus.chatapp.posts.UserDTO;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mname;
    private EditText memail;
    private EditText mpassword1;
    private EditText mpassword2;
    private Button mregisterButton;

    private View mProgressView;
    private View mRegisterFormView;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mname = (EditText) findViewById(R.id.registerName);
        memail = (EditText) findViewById(R.id.registerEmail);
        mpassword1 = (EditText) findViewById(R.id.registerPassword1);
        mpassword2 = (EditText) findViewById(R.id.registerPassword2);
        mregisterButton = (Button) findViewById(R.id.registerActivityButton);
        mregisterButton.setOnClickListener(this);





        mRegisterFormView = findViewById(R.id.registerActivity_layout);
        mProgressView = findViewById(R.id.login_progress2);

//        SingletonApplications.fbUser = null;


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void attemptRegister() {
        // Reset errors.
        memail.setError(null);
        mpassword1.setError(null);

        // Store values at the time of the login attempt.
        String email = memail.getText().toString();
        String password = mpassword1.getText().toString();
        String password2 = mpassword2.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the fbUser entered one.
        if (!isPasswordValid(password, password2)) {
            mpassword1.setError(getString(R.string.error_invalid_password));
            focusView = mpassword1;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            memail.setError(getString(R.string.error_field_required));
            focusView = memail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            memail.setError(getString(R.string.error_invalid_email));
            focusView = memail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the fbUser login attempt.
            showProgress(true);
            signUp(email, password);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }


    private boolean isPasswordValid(String password, String password2) {
        if (password.length() > 4 && password.equals(password2))
            return true;
        else
            return false;
    }




    public void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                        }
                        else if (task.isSuccessful()) {
                            SingletonApplications.fbUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (SingletonApplications.fbUser != null) {
                                writeNewUserToDB();
                                //showProgress(false);
                            } else {
                                // No user is signed in
                            }
                            //mAuth.signOut();



                        }
                    }
                });
    }


    public void writeNewUserToDB() {
        UserDTO user = new UserDTO(
                "https://firebasestorage.googleapis.com/v0/b/chatapp-e57e5.appspot.com/o/avatars%2FFast-Furious-Actors-As-You-Have-Never-Seen-Them-Before-1.jpg?alt=media&token=b6042ad7-2da0-4a0b-9079-728f0ade12d4",
                SingletonApplications.fbUser.getEmail(),
                mname.getText().toString(),
                0);
        mDatabase.child("users").child(SingletonApplications.fbUser.getUid()).setValue(user);

        login();

    }

    private void login() {
        showProgress(false);
        goToLogin();
    }


    @Override
    public void onBackPressed() {
        goToLogin();
    }

    public void goToLogin() {
        finish();
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public void testAnim() {
        showProgress(true);
    }


    @Override
    public void onClick(View view) {
        if (view == mregisterButton)
            attemptRegister();
            //testAnim();
    }
}
