package es.dmariaa.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class TriviaEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_end);

        TextView htmlText = findViewById(R.id.html_future);
        String html = getString(R.string.proximas_entregas);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            htmlText.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            htmlText.setText(Html.fromHtml(html));
        }

        this.setTitle(R.string.trivia_end_title);
    }
}