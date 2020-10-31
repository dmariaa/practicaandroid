package es.dmariaa.practica1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import es.dmariaa.practica1.interfaces.OnStartTriviaListener;

public class UserActivity extends AppCompatActivity implements OnStartTriviaListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        UserDataFragment fragment = new UserDataFragment();
        fragment.setOnStartTriviaListener(this);
        fragmentTransaction.add(R.id.fragments_frame, fragment);
        fragmentTransaction.commit();

        this.setTitle(getString(R.string.userdata_title));
    }

    @Override
    public void onStartTrivia() {
        Intent intent = new Intent(this, TriviaActivity.class);
        startActivity(intent);
    }
}