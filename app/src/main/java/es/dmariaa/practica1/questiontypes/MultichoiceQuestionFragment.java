package es.dmariaa.practica1.questiontypes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.dmariaa.practica1.R;
import es.dmariaa.practica1.models.Answer;
import es.dmariaa.practica1.models.Question;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MultichoiceQuestionFragment extends BaseQuestionFragment implements CompoundButton.OnCheckedChangeListener {

    List<CheckBox> options;

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_multichoice_question, container, false);
    }

    @Override
    public boolean isCorrect() {
        boolean correct = true;

        for(int i=0; i < question.getAnswers().size(); i++) {
            Answer answer = question.getAnswers().get(i);
            CheckBox checkBox = options.get(i);
            boolean answerValue = answer.getValue()==1;

            if(checkBox.isChecked() != answerValue) {
                correct = false;
                break;
            }
        }

        return correct;
    }

    @Override
    protected String getHint() {
        return getResources().getString(R.string.elige_varias);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.textView_multichoice);
        Question question = this.getQuestion();
        textView.setText(Html.fromHtml(question.getDescription()));

        options = new ArrayList<CheckBox>();
        createOptionButtons(question);
    }

    private void createOptionButtons(Question question) {
        LinearLayout optionsLayout = getView().findViewById(R.id.options_layout);

        for(int i=0; i < question.getAnswers().size(); i++) {
            Answer answer = question.getAnswers().get(i);

            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setId(i);
            checkBox.setText(answer.getDescription());
            checkBox.setTextSize(18);
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            checkBox.setOnCheckedChangeListener(this);
            optionsLayout.addView(checkBox);
            options.add(checkBox);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        userAnswered();
    }
}