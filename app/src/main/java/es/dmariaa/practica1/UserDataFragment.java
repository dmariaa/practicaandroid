package es.dmariaa.practica1;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Date;

import es.dmariaa.practica1.dialogs.DatePickerFragment;
import es.dmariaa.practica1.interfaces.OnStartTriviaListener;

public class UserDataFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private OnStartTriviaListener onStartTriviaListener;

    public void setOnStartTriviaListener(OnStartTriviaListener onStartTriviaListener) {
        this.onStartTriviaListener = onStartTriviaListener;
    }

    private EditText txtBirthdate;
    private Button startTrivia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_userdata, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.txtBirthdate = (EditText) view.findViewById(R.id.txtBirthdate);
        this.txtBirthdate.setOnClickListener(this);

        this.startTrivia = view.findViewById(R.id.start_trivia);
        this.startTrivia.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.txtBirthdate:
                this.showDatePickerDialog();
                break;
            case R.id.start_trivia:
                if(this.onStartTriviaListener != null) {
                    this.onStartTriviaListener.onStartTrivia();
                }
                break;

        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment datePicker = DatePickerFragment.newInstance(this);
        datePicker.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        this.txtBirthdate.setText(new Date(year, month, day).toLocaleString());
    }
}