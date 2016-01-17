package tk.samgrogan.q;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_sign_up);
    }

    public void signUp(View view) {
        EditText mEmail = (EditText) findViewById(R.id.email);
        EditText mPassword = (EditText) findViewById(R.id.Password);
        Firebase ref = new Firebase("https://crackling-inferno-9592.firebaseio.com");
        ref.createUser(mEmail.getText().toString(), mPassword.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);

            }

            @Override
            public void onError(FirebaseError firebaseError) {

            }
        });
    }
}
