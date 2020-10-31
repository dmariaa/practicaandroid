package es.dmariaa.practica1.questiontypes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import es.dmariaa.practica1.R;
import es.dmariaa.practica1.models.Answer;
import es.dmariaa.practica1.models.Question;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ChoiceQuestionFragment extends BaseQuestionFragment implements RadioGroup.OnCheckedChangeListener {
    private void createOptionButtons(Question question) {
        RadioGroup group = getView().findViewById(R.id.choices_group);
        group.setOnCheckedChangeListener(this);

        for(int i=0; i < question.getAnswers().size(); i++) {
            Answer answer = question.getAnswers().get(i);

            RadioButton btn = new RadioButton(getContext());
            btn.setId(i);
            btn.setText(answer.getDescription());
            btn.setTextSize(18);
            group.addView(btn);
        }
    }

    @Override
    public void pauseQuestion(boolean pause) {

    }

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choice_question, container, false);
    }

    @Override
    public boolean isCorrect() {
        RadioGroup group = getView().findViewById(R.id.choices_group);
        Answer answer = question.getAnswers().get(group.getCheckedRadioButtonId());
        return answer.getValue()==1;
    }

    @Override
    protected String getHint() {
        return getResources().getString(R.string.elige_una);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.textView_choice);
        Question question = this.getQuestion();
        textView.setText(Html.fromHtml(question.getDescription()));

        createOptionButtons(question);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        userAnswered();
    }
}