package es.dmariaa.practica1.questiontypes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import es.dmariaa.practica1.R;
import es.dmariaa.practica1.models.Answer;
import es.dmariaa.practica1.models.Question;

public abstract class BaseQuestionFragment extends Fragment {
    OnQuestionAnsweredListener listener;

    Question question;
    List<Integer> userResponses;

    public Question getQuestion() {
        return question;
    }
    public List<Integer> getUserResponses(){return userResponses;}

    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * Implement to assign view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View createFragmentView(LayoutInflater inflater, ViewGroup container,
                                            Bundle savedInstanceState);

    /**
     *
     * @return
     */
    public abstract boolean isCorrect();

    /**
     * Returns answer to parent activity
     */
    protected void userAnswered() {
        listener.onQuestionAnswered();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnQuestionAnsweredListener) {
            listener = (OnQuestionAnsweredListener) context;
        } else {
            throw new IllegalArgumentException(getResources().getString(R.string.bad_parent_activiy));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = createFragmentView(inflater, container, savedInstanceState);
        return view;
    }


}
