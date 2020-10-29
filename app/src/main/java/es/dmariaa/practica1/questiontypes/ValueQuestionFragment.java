package es.dmariaa.practica1.questiontypes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.dmariaa.practica1.R;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.textView_value);
        Question question = this.getQuestion();
        textView.setText(question.getDescription());

        returnAnswer("1");
    }
}