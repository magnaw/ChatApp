package chatapp.magnus.chatapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password1;
    private EditText password2;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.registerName);
        email = (EditText) findViewById(R.id.registerEmail);
        password1 = (EditText) findViewById(R.id.registerPassword1);
        password2 = (EditText) findViewById(R.id.registerPassword2);


    }

}
