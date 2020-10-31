package es.dmariaa.practica1.questiontypes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

import es.dmariaa.practica1.R;
import es.dmariaa.practica1.models.Question;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TrueFalseQuestionFragment extends BaseQuestionFragment implements CompoundButton.OnCheckedChangeListener {

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_true_false_question, container, false);
    }

    @Override
    public boolean isCorrect() {
        SwitchMaterial button = getView().findViewById(R.id.switch1);
        boolean answer = question.getAnswers().get(0).getValue() == 1;
        return button.isChecked() == answer;
    }

    @Override
    protected String getHint() {
        return getResources().getString(R.string.selecciona_verdaderofalso);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.textView_truefalse);
        Question question = this.getQuestion();
        textView.setText(Html.fromHtml(question.getDescription()));

        createOptionButton(question);
    }

    @SuppressLint({"SetTextI18n", "UseSwitchCompatOrMaterialCode"})
    public void createOptionButton(Question question){
        SwitchMaterial button = getView().findViewById(R.id.switch1);

        button.setOnCheckedChangeListener(this);

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        /*
        TextView falseText = getView().findViewById(R.id.falseText);
        TextView trueText = getView().findViewById(R.id.trueText);

        if (isChecked){
            falseText.setAlpha(0);
            trueText.setAlpha(1);
        }else{
            trueText.setAlpha(0);
            falseText.setAlpha(1);
        }
        */

        userAnswered();
    }

}