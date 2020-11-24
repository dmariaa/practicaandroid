package es.dmariaa.practica1.data;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import es.dmariaa.practica1.data.db.DbContract;
import es.dmariaa.practica1.data.db.DbManager;
import es.dmariaa.practica1.data.model.Answer;
import es.dmariaa.practica1.data.model.Question;
import es.dmariaa.practica1.data.model.QuestionType;

public class QuestionsRepository {
    private static volatile QuestionsRepository instance;

    private QuestionsRepository(Context context) {
        dbManager = DbManager.getInstance(context);
    }

    public static QuestionsRepository getInstance(Context context) {
        if(instance == null) {
            instance = new QuestionsRepository(context);
        }
        return instance;
    }


    DbManager dbManager;

    private MutableLiveData<List<Question>> questionsLiveData;

    public MutableLiveData<List<Question>> getQuestions() {
        if(questionsLiveData==null) {
            questionsLiveData = new MutableLiveData<List<Question>>();
            loadQuestions();
        }
        return questionsLiveData;
    }

    private void loadQuestions() {
        Handler handler = new Handler();

        handler.post(() -> {
            List<Question> questions = new ArrayList<Question>();

            Cursor cursor = dbManager.getAllQuestions();
            Question currentQuestion = null;

            while(cursor.moveToNext()) {
                int questionId = cursor.getInt(cursor.getColumnIndex(DbContract.DbQuestions.TABLE_NAME + "_" + DbContract.DbQuestions._ID));

                if(currentQuestion==null || currentQuestion.getId() != questionId) {
                    if(currentQuestion != null) {
                        questions.add(currentQuestion);
                    }
                    currentQuestion = generateQuestion(cursor);
                }

                currentQuestion.addAnswer(generateAnswer(cursor));
            }

            if(currentQuestion != null) {
                questions.add(currentQuestion);
            }

            questionsLiveData.postValue(questions);
        });
    }

    private Question generateQuestion(Cursor cursor) {
        int questionId = cursor.getInt(cursor.getColumnIndex(DbContract.DbQuestions.TABLE_NAME + "_" + DbContract.DbQuestions._ID));
        String questionType = cursor.getString(cursor.getColumnIndex(DbContract.DbQuestions.TABLE_NAME + "_" + DbContract.DbQuestions.COLUMN_NAME_TYPE));
        int questionMinimumAge = cursor.getInt(cursor.getColumnIndex(DbContract.DbQuestions.TABLE_NAME + "_" + DbContract.DbQuestions.COLUMN_NAME_MINIMUM_AGE));
        String questionDescription = cursor.getString(cursor.getColumnIndex(DbContract.DbQuestions.TABLE_NAME + "_" + DbContract.DbQuestions.COLUMN_NAME_DESCRIPTION));
        String questionImage = cursor.getString(cursor.getColumnIndex(DbContract.DbQuestions.TABLE_NAME + "_" + DbContract.DbQuestions.COLUMN_NAME_IMAGE));
        String questionVideo = cursor.getString(cursor.getColumnIndex(DbContract.DbQuestions.TABLE_NAME + "_" + DbContract.DbQuestions.COLUMN_NAME_VIDEO));
        String questionFeedback = cursor.getString(cursor.getColumnIndex(DbContract.DbQuestions.TABLE_NAME + "_" + DbContract.DbQuestions.COLUMN_NAME_FEEDBACK));

        Question question = new Question(
            questionId,
            QuestionType.fromString(questionType),
            questionImage,
            questionMinimumAge,
            questionDescription,
            questionFeedback,
            questionVideo);

        return question;
    }


    private Answer generateAnswer(Cursor cursor) {
        int answerId = cursor.getInt(cursor.getColumnIndex(DbContract.DbAnswers.TABLE_NAME + "_" + DbContract.DbAnswers._ID));
        String answerDescription = cursor.getString(cursor.getColumnIndex(DbContract.DbAnswers.TABLE_NAME + "_" + DbContract.DbAnswers.COLUMN_NAME_DESCRIPTION));
        int answerValue =  cursor.getInt(cursor.getColumnIndex(DbContract.DbAnswers.TABLE_NAME + "_" + DbContract.DbAnswers.COLUMN_NAME_VALUE));
        int answerValueMin = cursor.getInt(cursor.getColumnIndex(DbContract.DbAnswers.TABLE_NAME + "_" + DbContract.DbAnswers.COLUMN_NAME_VALUE_MIN));
        int answerValueMax = cursor.getInt(cursor.getColumnIndex(DbContract.DbAnswers.TABLE_NAME + "_" + DbContract.DbAnswers.COLUMN_NAME_VALUE_MAX));
        String answerValueFormat = cursor.getString(cursor.getColumnIndex(DbContract.DbAnswers.TABLE_NAME + "_" + DbContract.DbAnswers.COLUMN_NAME_VALUE_FORMAT));
        int answerStep = cursor.getInt(cursor.getColumnIndex(DbContract.DbAnswers.TABLE_NAME + "_" + DbContract.DbAnswers.COLUMN_NAME_STEP));
        int answerQuestionId = cursor.getInt(cursor.getColumnIndex(DbContract.DbAnswers.TABLE_NAME + "_" + DbContract.DbAnswers.COLUMN_NAME_QUESTION_ID));

        Answer answer = new Answer(answerId,
                answerDescription,
                answerValue,
                answerValueMin,
                answerValueMax,
                answerValueFormat,
                answerStep, answerQuestionId);

        return answer;
    }
}
