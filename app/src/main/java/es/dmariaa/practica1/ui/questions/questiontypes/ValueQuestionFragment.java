package es.dmariaa.practica1.ui.questions.questiontypes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;

import es.dmariaa.practica1.R;
import es.dmariaa.practica1.data.model.Answer;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ValueQuestionFragment extends BaseQuestionFragment {
    Slider slider;
    TextView textValue;
    Answer answer;

    private void setTextValue(float value) {
        if(answer.getValueformat() != null && !answer.getValueformat().isEmpty()) {
            try {
                textValue.setText(String.format(answer.getValueformat(), (int)value));
            } catch (Exception exception) {
                Log.e(this.getClass().getName(), "Invalid answer format", exception);
            }
        }
    }

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

        answer = question.getAnswers().get(0);
        textValue = view.findViewById(R.id.valuequestion_value_text);

        setTextValue(answer.getValuemin());

        slider = view.findViewById(R.id.seekBar);
        slider.setValueFrom(answer.getValuemin());
        slider.setValueTo(answer.getValuemax());
        slider.setStepSize(answer.getStep());
        slider.setValue(answer.getValuemin());
        slider.setLabelBehavior(LabelFormatter.LABEL_GONE);
        slider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                if(answer.getValueformat() != null && !answer.getValueformat().isEmpty()) {
                    return String.format(answer.getValueformat(), (int)value);
                }

                return String.format("%d", (int)value);
            }
        });
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                setTextValue(value);
                userAnswered();
            }
        });
    }
}