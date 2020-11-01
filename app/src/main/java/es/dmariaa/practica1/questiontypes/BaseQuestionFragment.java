package es.dmariaa.practica1.questiontypes;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import es.dmariaa.practica1.R;
import es.dmariaa.practica1.interfaces.OnQuestionAnsweredListener;
import es.dmariaa.practica1.models.Question;

public abstract class BaseQuestionFragment extends Fragment {
    OnQuestionAnsweredListener listener;

    Question question;

    Toast hintToast;

    /**
     * Get this fragment question
     * @return
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * Set this fragment question
     * @param question
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * Assign this fragment view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View createFragmentView(LayoutInflater inflater, ViewGroup container,
                                            Bundle savedInstanceState);

    /**
     * Is this question correct
     * @return
     */
    public abstract boolean isCorrect();

    /**
     * Get hint for this question
     * @return
     */
    protected abstract String getHint();

    /**
     * Returns answer to parent activity
     */
    protected void userAnswered() {
        if(hintToast != null) hintToast.cancel();
        listener.onQuestionAnswered();
    }

    public void pauseQuestion(boolean pause) {
        getView().setEnabled(false);
        getView().setClickable(false);
        getView().setFocusable(false);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hintToast = Toast.makeText(getContext(), getHint(), Toast.LENGTH_LONG);
        hintToast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 24);
        hintToast.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hintToast.cancel();
    }
}
