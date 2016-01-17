package tk.samgrogan.q;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_sign_in);
    }



    public void logIn(View view) {
        final EditText mEmail = (EditText) findViewById(R.id.email);
        final EditText mPassword = (EditText) findViewById(R.id.Password);

        Runnable log = new Runnable() {
            @Override
            public void run() {
                Firebase ref = new Firebase("https://crackling-inferno-9592.firebaseio.com");

                Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {

                        //Toast.makeText(SignIn.this, "Logged IN!!!! FUCK YEAH", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignIn.this, TempUser.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {

                        Toast.makeText(SignIn.this, firebaseError.toString(), Toast.LENGTH_LONG).show();


                    }
                };

                ref.authWithPassword(mEmail.getText().toString(), mPassword.getText().toString(), authResultHandler);


            }
        };

        Thread auth = new Thread(log);
        auth.start();




    }

}
