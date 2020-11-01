package es.dmariaa.practica1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;

import es.dmariaa.practica1.interfaces.OnStartTriviaListener;

public class UserActivity extends AppCompatActivity implements OnStartTriviaListener {
    UserDataFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment = new UserDataFragment();
        fragment.setOnStartTriviaListener(this);
        fragmentTransaction.add(R.id.fragments_frame, fragment);
        fragmentTransaction.commit();

        this.setTitle(getString(R.string.userdata_title));
    }

    @Override
    public void onStartTrivia() {
        String user = fragment.getName();
        String birthdate = fragment.getBirthDate();

        Intent intent = new Intent(this, TriviaActivity.class);
        intent.putExtra("USERNAME", user);
        intent.putExtra("BIRTHDATE", birthdate);
        startActivity(intent);
    }
}