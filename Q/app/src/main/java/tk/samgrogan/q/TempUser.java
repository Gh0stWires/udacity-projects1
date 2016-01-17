package tk.samgrogan.q;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class TempUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_temp_user);
    }

    public void logOut(View view) {

        final Firebase ref = new Firebase("https://crackling-inferno-9592.firebaseio.com");
        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {

                if (authData != null){
                    ref.unauth();
                    Intent intent = new Intent(TempUser.this, SignIn.class);
                    startActivity(intent);
                }

            }
        });


    }

    public void get_movies(View view) {
        Intent intent = new Intent(this, MovieList.class);
        startActivity(intent);


    }
}
