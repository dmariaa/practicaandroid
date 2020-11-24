package es.dmariaa.practica1.ui.menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import es.dmariaa.practica1.R;
import es.dmariaa.practica1.UserActivity;
import es.dmariaa.practica1.ui.questions.TriviaActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        MaterialButton btnAnon = findViewById(R.id.anon_play);
        MaterialButton btnProfileList = findViewById(R.id.view_profiles);

        btnAnon.setOnClickListener((p1) -> {
            Intent intent = new Intent(this, TriviaActivity.class);
            intent.putExtra("USERID", 0);
            startActivity(intent);
        });

        btnProfileList.setOnClickListener((p1) -> {
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        });
    }


}