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
import android.widget.TextView;

import es.dmariaa.practica1.R;
import es.dmariaa.practica1.models.Answer;
import es.dmariaa.practica1.models.Question;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MultichoiceQuestionFragment extends BaseQuestionFragment implements CompoundButton.OnCheckedChangeListener {

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_multichoice_question, container, false);
    }

    @Override
    public boolean isCorrect() {
        for(int i = 0; i<question.getAnswers().size(); i++){
            CheckBox group = getView().findViewById(R.id.multichoice0 +i);
            if (!group.isChecked()){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.textView_multichoice);
        Question question = this.getQuestion();
        textView.setText(Html.fromHtml(question.getDescription()));

        createOptionButtons(question);
    }

    private void createOptionButtons(Question question) {


        for(int i=0; i < question.getAnswers().size(); i++) {
            CheckBox group = getView().findViewById(R.id.multichoice0 +i);
            group.setVisibility(View.VISIBLE);
            group.setOnCheckedChangeListener(this);
            Answer answer = question.getAnswers().get(i);
            CheckBox btn = new CheckBox(getContext());
            btn.setId(i);
            btn.setText(answer.getDescription());
            btn.setTextSize(18);
            group.setText(btn.getText());
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        userAnswered();
    }
}