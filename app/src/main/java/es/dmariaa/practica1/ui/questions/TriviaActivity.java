package es.dmariaa.practica1.ui.questions;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Predicate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import es.dmariaa.practica1.R;
import es.dmariaa.practica1.TriviaEndActivity;
import es.dmariaa.practica1.data.model.Question;
import es.dmariaa.practica1.data.model.Result;
import es.dmariaa.practica1.data.model.ResultQuestions;
import es.dmariaa.practica1.data.model.UserProfile;
import es.dmariaa.practica1.dialogs.AnswerResultDialogFragment;
import es.dmariaa.practica1.interfaces.OnQuestionAnsweredListener;
import es.dmariaa.practica1.interfaces.OnResultClosedListener;
import es.dmariaa.practica1.ui.questions.questiontypes.BaseQuestionFragment;
import es.dmariaa.practica1.ui.questions.questiontypes.ChoiceQuestionFragment;
import es.dmariaa.practica1.ui.questions.questiontypes.MultichoiceQuestionFragment;
import es.dmariaa.practica1.ui.questions.questiontypes.TrueFalseQuestionFragment;
import es.dmariaa.practica1.ui.questions.questiontypes.ValueQuestionFragment;

public class TriviaActivity extends AppCompatActivity implements View.OnClickListener, OnQuestionAnsweredListener {
    QuestionsViewModel viewModel;
    List<Question> questions;
    UserProfile userProfile;
    Result userResult;
    int currentUser;
    TextView rightAnswers;
    TextView wrongAnswers;

    FloatingActionButton confirmButton;
    int currentQuestion = 0;
    BaseQuestionFragment currentFragment;
    boolean showAnswer = false;

    LinearLayout imageVideoContainer;
    VideoView questionVideo;
    ImageView questionImage;
    TextView questionDescription;

    /**
     * Loads questions from json
     */
//    private void loadQuestions() {
//        try {
//            InputStream inputStream = this.getResources().openRawResource(R.raw.questions);
//            Reader reader = new InputStreamReader(inputStream, "utf-8");
//
//            Type questionListType = new TypeToken<ArrayList<Question>>() {}.getType();
//            List<Question> questions = new Gson().fromJson(reader, questionListType);
//            this.questions = questions;
//
//            // Filtering and shuffling questions
//            // filterQuestions(q -> q.getType() == QuestionType.CHOICE);
//            // filterQuestions(q -> q.getVideo() != null);
//            Collections.shuffle(this.questions);
//
//            Log.println(Log.DEBUG, "TriviaActivity", "Leidas " + questions.size() + " preguntas");
//        } catch (UnsupportedEncodingException e) {
//            // e.printStackTrace();
//            Log.println(Log.ERROR, "TriviaActivity", Log.getStackTraceString(e));
//        }
//    }

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

            showQuestionDescription(question);
            setCurrentQuestionTitle();
        }
    }

    private void showQuestionDescription(Question question) {
        if(question.getImage() != null && !question.getImage().isEmpty()) {
            try {
                imageVideoContainer.setVisibility(View.VISIBLE);
                questionImage.setVisibility(View.VISIBLE);
                questionVideo.setVisibility(View.GONE);

                String imageFile = String.format("question-images/%s", question.getImage());
                InputStream questionImageStream = getResources().getAssets().open(imageFile);
                Bitmap questionImageBitmap = BitmapFactory.decodeStream(questionImageStream);
                questionImage.setImageBitmap(questionImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
                questionImage.setVisibility(View.GONE);
            }
        } else if(question.getVideo() != null && !question.getVideo().isEmpty()) {
            imageVideoContainer.setVisibility(View.VISIBLE);
            questionImage.setVisibility(View.GONE);
            questionVideo.setVisibility(View.VISIBLE);

            MediaController mediaController = new MediaController(this);
            questionVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/raw/" + question.getVideo()));
            // questionVideo.setMediaController(mediaController);
            questionVideo.requestFocus();
            questionVideo.start();
        } else {
            imageVideoContainer.setVisibility(View.GONE);
            questionImage.setVisibility(View.GONE);
            questionVideo.setVisibility(View.GONE);
        }

        questionDescription.setText(Html.fromHtml(question.getDescription()));
    }

    private void setCurrentQuestionTitle() {
        this.setTitle(getString(R.string.trivia_title, currentQuestion + 1, questions.size()));
    }

    /**
     * Shows question result
     * @param action
     */
    private void showAnswer(boolean isCorrect, Runnable action) {
        Context context = getApplicationContext();
        String answerText = (isCorrect) ? "Respuesta correcta" : "Respuesta incorrecta";
        View parent = currentFragment.getView();

        if(showAnswer) {
            AnswerResultDialogFragment answerResultDialogFragment = new AnswerResultDialogFragment();
            answerResultDialogFragment.setResultRight(isCorrect);
            answerResultDialogFragment.setOnResultClosedListener(new OnResultClosedListener() {
                @Override
                public void onResultClosed() {
                    action.run();
                }
            });
            answerResultDialogFragment.show(getSupportFragmentManager(), answerResultDialogFragment.getTag());
        } else {
            action.run();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        int userIdx = getIntent().getIntExtra("USERID", 0);

        confirmButton = (FloatingActionButton) findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(this);
        confirmButton.hide();

        imageVideoContainer = findViewById(R.id.question_video_layout);
        questionDescription = findViewById(R.id.question_text);
        questionImage = findViewById(R.id.question_image);
        questionVideo = findViewById(R.id.question_video);

        rightAnswers = findViewById(R.id.right_answers);
        wrongAnswers = findViewById(R.id.wrong_answers);

        QuestionsViewModelFactory factory = new QuestionsViewModelFactory(this);
        viewModel = new ViewModelProvider(this, factory).get(QuestionsViewModel.class);
        viewModel.getQuestions().observe(this, observeQuestions);
        viewModel.getUserProfile().observe(this, observeUserProfile);

        viewModel.setCurrentUser(userIdx);
    }

    Observer<UserProfile> observeUserProfile = (userProfile) -> {
        if(userProfile != null) {
            this.userProfile = userProfile;
        } else {
            this.userProfile = new UserProfile();
        }

        java.sql.Date start = new java.sql.Date(new Date().getTime());
        this.userResult = new Result(0, start, null, this.userProfile.getId());

    };

    Observer<List<Question>> observeQuestions = (List<Question> questions) -> {
        this.questions = questions;

        rightAnswers.setText("0");
        wrongAnswers.setText("0");

        // filterQuestions(q -> q.getType() == QuestionType.TRUEFALSE);
        Collections.shuffle(this.questions);
        loadQuestionFragment(this.questions.get(currentQuestion));
    };


    @Override
    public void onClick(View view) {
        if(currentFragment != null) {
            confirmButton.hide();
            currentFragment.pauseQuestion(false);

            boolean isCorrect = currentFragment.isCorrect();
            Question question = this.questions.get(currentQuestion);
            ResultQuestions resultQuestions = new ResultQuestions(0, this.userResult.getId(), question.getId(), "", isCorrect ? 1 : 0, new java.sql.Date(new Date().getTime()));
            this.userResult.addQuestion(resultQuestions);

            if(isCorrect) {
                int n = Integer.parseInt(this.rightAnswers.getText().toString()) + 1;
                this.rightAnswers.setText(String.valueOf(n));
            } else {
                int n = Integer.parseInt(this.wrongAnswers.getText().toString()) + 1;
                this.wrongAnswers.setText(String.valueOf(n));
            }

            showAnswer(isCorrect, () -> {
                getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
                currentQuestion++;

                if (currentQuestion < questions.size()) {
                    loadQuestionFragment(questions.get(currentQuestion));
                } else {
                    if(userProfile.getId() > 0) {
                        this.userResult.setEndTime(new java.sql.Date(new Date().getTime()));
                        userProfile.addResult(this.userResult);
                        viewModel.saveUser(userProfile);
                    }

                    this.userResult.totalAnswers = questions.size();
                    this.userResult.wrongAnsers = Integer.parseInt(this.wrongAnswers.getText().toString());
                    this.userResult.rightAnswers = Integer.parseInt(this.rightAnswers.getText().toString());

                    Intent intent = new Intent(this, TriviaEndActivity.class);
                    intent.putExtra("RESULT", this.userResult);
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