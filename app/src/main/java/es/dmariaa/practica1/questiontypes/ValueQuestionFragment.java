package es.dmariaa.practica1.questiontypes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import es.dmariaa.practica1.R;
import es.dmariaa.practica1.models.Answer;
import es.dmariaa.practica1.models.Question;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ValueQuestionFragment extends BaseQuestionFragment {
    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_value_question, container, false);
    }

    @Override
    public boolean isCorrect() {
        Slider slider = getView().findViewById(R.id.seekBar);
        Answer answer = question.getAnswers().get(0);
        return answer.getValue()==(int)slider.getValue();
    }

    @Override
    protected String getHint() {
        return getResources().getString(R.string.elige_un_valor);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.textView_value);
        Question question = this.getQuestion();
        textView.setText(Html.fromHtml(question.getDescription()));

        Answer answer = question.getAnswers().get(0);

        Slider slider = view.findViewById(R.id.seekBar);
        slider.setValueFrom(answer.getValuemin());
        slider.setValueTo(answer.getValuemax());
        slider.setStepSize(answer.getStep());
        slider.setValue(answer.getValuemin());
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                userAnswered();
            }
        });
    }
}