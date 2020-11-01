package es.dmariaa.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.NumberFormat;

import es.dmariaa.practica1.models.Result;

public class TriviaEndActivity extends AppCompatActivity implements View.OnClickListener {
    Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_end);

        Log.d(TriviaActivity.class.getName(), "Trivia end loaded");

        result = (Result) getIntent().getSerializableExtra("RESULT");

        int totalAnswers = result.getTotalAnswers();
        int rightAnswers = result.getRightAnswers();
        float pct = (float)rightAnswers / (float) totalAnswers;

        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setProgress((int)(pct * 100));

        TextView textPCT = findViewById(R.id.text_pct);
        NumberFormat format = NumberFormat.getPercentInstance();
        textPCT.setText(format.format(pct));

        TextView textAnswers = findViewById(R.id.text_answers);
        textAnswers.setText(String.format("%d/%d", rightAnswers, totalAnswers));

        TextView htmlText = findViewById(R.id.html_future);
        String html = getString(R.string.proximas_entregas);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            htmlText.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            htmlText.setText(Html.fromHtml(html));
        }

        this.setTitle(R.string.trivia_end_title);

        Button playAgain = findViewById(R.id.button_playagain);
        playAgain.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }
}