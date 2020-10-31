package es.dmariaa.practica1;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import es.dmariaa.practica1.dialogs.DatePickerFragment;
import es.dmariaa.practica1.interfaces.OnStartTriviaListener;

public class UserDataFragment extends Fragment implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener, TextWatcher {
    private OnStartTriviaListener onStartTriviaListener;

    public void setOnStartTriviaListener(OnStartTriviaListener onStartTriviaListener) {
        this.onStartTriviaListener = onStartTriviaListener;
    }

    private EditText txtName;
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

        this.txtName = view.findViewById(R.id.text_name);
        this.txtName.addTextChangedListener(this);

        this.txtBirthdate = view.findViewById(R.id.text_birthday);
        this.txtBirthdate.setOnClickListener(this);
        this.txtBirthdate.addTextChangedListener(this);

        this.startTrivia = view.findViewById(R.id.start_trivia);
        this.startTrivia.setOnClickListener(this);
        this.startTrivia.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.text_birthday:
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
        Calendar calendar = new GregorianCalendar(year, month, day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateFormatted = simpleDateFormat.format(calendar.getTime());
        this.txtBirthdate.setText(dateFormatted);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String name = this.txtName.getText().toString();
        String birthdate = this.txtBirthdate.getText().toString();
        if(!name.isEmpty() && !birthdate.isEmpty()) {
            this.startTrivia.setEnabled(true);
        } else {
            this.startTrivia.setEnabled(false);
        }
    }
}