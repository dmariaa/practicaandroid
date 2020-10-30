package es.dmariaa.practica1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Predicate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import es.dmariaa.practica1.models.Answer;
import es.dmariaa.practica1.models.Question;
import es.dmariaa.practica1.questiontypes.BaseQuestionFragment;
import es.dmariaa.practica1.questiontypes.ChoiceQuestionFragment;
import es.dmariaa.practica1.questiontypes.MultichoiceQuestionFragment;
import es.dmariaa.practica1.questiontypes.OnQuestionAnsweredListener;
import es.dmariaa.practica1.questiontypes.TrueFalseQuestionFragment;
import es.dmariaa.practica1.questiontypes.ValueQuestionFragment;

public class TriviaActivity extends AppCompatActivity implements View.OnClickListener, OnQuestionAnsweredListener {
    List<Question> questions;
    FloatingActionButton confirmButton;
    int currentQuestion = 0;
    BaseQuestionFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        confirmButton = (FloatingActionButton) findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(this);
        confirmButton.hide();

        loadQuestions();
        loadQuestionFragment(questions.get(currentQuestion));
    }

    /**
     * Loads questions from json
     */
    private void loadQuestions() {
        try {
            InputStream inputStream = this.getResources().openRawResource(R.raw.questions);
            Reader reader = new InputStreamReader(inputStream, "utf-8");

            Type questionListType = new TypeToken<ArrayList<Question>>() {}.getType();
            List<Question> questions = new Gson().fromJson(reader, questionListType);
            this.questions = questions;

            filterQuestions(q -> q.getType() == Question.QuestionType.CHOICE);
            Collections.shuffle(this.questions);

            Log.println(Log.DEBUG, "TriviaActivity", "Leidas " + questions.size() + " preguntas");
        } catch (UnsupportedEncodingException e) {
            // e.printStackTrace();
            Log.println(Log.ERROR, "TriviaActivity", Log.getStackTraceString(e));
        }
    }

    /**
     * Filters questions using the given predicate
     * @param filter
     */
    private void filterQuestions(Predicate<Question> filter) {
        ArrayList<Question> filtered = new ArrayList<Question>();

        for(int i=0; i < questions.size(); i++) {
            Question question = questions.get(i);
            if(filter.test(question)) {
                filtered.add(question);
            }
        }

        questions = filtered;
    }


    /**
     * Loads fragment associated to given question
     * @param question Question
     */
    private void loadQuestionFragment(Question question) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        BaseQuestionFragment fragment = null;

        switch(question.getType()) {
            case CHOICE:
                fragment = new ChoiceQuestionFragment();
                break;
            case MULTICHOICE:
                fragment = new MultichoiceQuestionFragment();
                break;
            case TRUEFALSE:
                fragment = new TrueFalseQuestionFragment();
                break;
            case VALUE:
                fragment = new ValueQuestionFragment();
                break;
            default:
                Log.e("TriviaActivity", "Tipo de pregunta no soportado");
        }

        if(fragment != null) {
            fragment.setQuestion(question);
            fragmentTransaction.add(R.id.question_frame, fragment);
            fragmentTransaction.commit();
            currentFragment = fragment;
        }
    }


    private void showAnswer(Answer answer, View.OnClickListener action) {
        Context context = getApplicationContext();
        String answerText = (answer.getValue()==1) ? "Respuesta correcta" : "Respuesta incorrecta";
        View parent = currentFragment.getView();
        int backgroundColor = getResources().getColor((answer.getValue() == 1) ?
                R.color.correct_color :
                R.color.error_color);

        Snackbar.make(parent, answerText, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.next_question, action)
                .setBackgroundTint(backgroundColor)
                .setActionTextColor(getResources().getColor(R.color.answer_text_color))
                .show();
    }

    @Override
    public void onClick(View view) {
        if(currentFragment != null) {
            confirmButton.hide();

            Answer answer = currentFragment.getAnswer();
            showAnswer(answer, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
                    currentQuestion++;
                    if (currentQuestion < questions.size()) {
                        loadQuestionFragment(questions.get(currentQuestion));
                    }
                }
            });
        } else {
            Log.e(getClass().getName(), "Current question fragment is invalid", new Exception("Invalid question fragment"));
        }
    }

    @Override
    public void onQuestionAnswered(Answer answer) {
        confirmButton.show();
    }
}