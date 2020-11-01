package es.dmariaa.practica1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Predicate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import es.dmariaa.practica1.dialogs.AnswerResultDialogFragment;
import es.dmariaa.practica1.interfaces.OnResultClosedListener;
import es.dmariaa.practica1.models.Question;
import es.dmariaa.practica1.models.QuestionResult;
import es.dmariaa.practica1.models.Result;
import es.dmariaa.practica1.questiontypes.BaseQuestionFragment;
import es.dmariaa.practica1.questiontypes.ChoiceQuestionFragment;
import es.dmariaa.practica1.questiontypes.MultichoiceQuestionFragment;
import es.dmariaa.practica1.interfaces.OnQuestionAnsweredListener;
import es.dmariaa.practica1.questiontypes.TrueFalseQuestionFragment;
import es.dmariaa.practica1.questiontypes.ValueQuestionFragment;

public class TriviaActivity extends AppCompatActivity implements View.OnClickListener, OnQuestionAnsweredListener {
    List<Question> questions;
    Result result;

    FloatingActionButton confirmButton;
    int currentQuestion = 0;
    BaseQuestionFragment currentFragment;

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

            // Filtering questions
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

            setCurrentQuestionTitle();
        }
    }

    private void setCurrentQuestionTitle() {
        this.setTitle(getString(R.string.trivia_title, currentQuestion + 1));
    }

    /**
     * Shows question result
     * @param action
     */
    private void showAnswer(boolean isCorrect, Runnable action) {
        Context context = getApplicationContext();
        String answerText = (isCorrect) ? "Respuesta correcta" : "Respuesta incorrecta";
        View parent = currentFragment.getView();

        AnswerResultDialogFragment answerResultDialogFragment = new AnswerResultDialogFragment();
        answerResultDialogFragment.setResultRight(isCorrect);
        answerResultDialogFragment.setOnResultClosedListener(new OnResultClosedListener() {
            @Override
            public void onResultClosed() {
                action.run();
            }
        });
        answerResultDialogFragment.show(getSupportFragmentManager(), answerResultDialogFragment.getTag());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        String username = getIntent().getStringExtra("USERNAME");
        String birthdate = getIntent().getStringExtra("BIRTHDATE");

        result = new Result();
        result.setUser(username);
        result.setBirthdate(birthdate);
        result.setResults(new ArrayList<QuestionResult>());

        confirmButton = (FloatingActionButton) findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(this);
        confirmButton.hide();

        loadQuestions();
        loadQuestionFragment(questions.get(currentQuestion));
    }

    @Override
    public void onClick(View view) {
        if(currentFragment != null) {
            confirmButton.hide();
            currentFragment.pauseQuestion(false);

            boolean isCorrect = currentFragment.isCorrect();
            QuestionResult questionResult = new QuestionResult();
            questionResult.setId(currentFragment.getQuestion().getId());
            questionResult.setValue(isCorrect ? 1 : 0);
            result.putQuestionResult(questionResult);

            showAnswer(isCorrect, () -> {
                currentQuestion++;
                if (currentQuestion < questions.size()) {
                    getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
                    loadQuestionFragment(questions.get(currentQuestion));
                } else {
                    Intent intent = new Intent(this, TriviaEndActivity.class);
                    intent.putExtra("RESULT", result);
                    startActivity(intent);
                }
            });
        } else {
            Log.e(getClass().getName(), "Current question fragment is invalid", new Exception("Invalid question fragment"));
        }
    }

    @Override
    public void onQuestionAnswered() {
        confirmButton.show();
    }
}