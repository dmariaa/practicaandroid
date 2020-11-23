package es.dmariaa.practica1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import es.dmariaa.practica1.data.model.UserProfile;
import es.dmariaa.practica1.ui.users.UserProfileDetailFragment;
import es.dmariaa.practica1.ui.users.UserProfileListFragment;

public class UserActivity extends AppCompatActivity {
    UserProfileListFragment userListFragment;
    UserProfileDetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        this.setTitle(getString(R.string.userdata_title));
        this.userListFragment = new UserProfileListFragment();
        this.detailFragment = new UserProfileDetailFragment();

        showListFragment();
    }

    public void showListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(detailFragment);
        fragmentTransaction.add(R.id.fragments_frame, this.userListFragment);
        fragmentTransaction.commit();
    }

    public void showDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(userListFragment);
        fragmentTransaction.add(R.id.fragments_frame, detailFragment);
        fragmentTransaction.commit();
    }

    public void playGame(UserProfile user) {
        Intent intent = new Intent(this, TriviaActivity.class);
        intent.putExtra("USERID", user.getUserId());
        intent.putExtra("BIRTHDATE", user.getBirthDate());
        startActivity(intent);
    }
}