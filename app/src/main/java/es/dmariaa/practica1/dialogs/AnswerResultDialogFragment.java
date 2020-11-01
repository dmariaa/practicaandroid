package es.dmariaa.practica1.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import es.dmariaa.practica1.R;
import es.dmariaa.practica1.interfaces.OnResultClosedListener;

public class AnswerResultDialogFragment extends BottomSheetDialogFragment {
    boolean resultRight;
    OnResultClosedListener onResultClosedListener;

    public boolean isResultRight() {
        return resultRight;
    }

    public void setResultRight(boolean resultRight) {
        this.resultRight = resultRight;
    }

    public void setOnResultClosedListener(OnResultClosedListener onResultClosedListener) {
        this.onResultClosedListener = onResultClosedListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_answer_result_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView answerText = view.findViewById(R.id.result_text);
        ImageView answerImage = view.findViewById(R.id.result_icon);

        if(resultRight) {
            answerText.setText(getResources().getString(R.string.respuesta_correcta));
            answerImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_right_answer));
        } else {
            answerText.setText(getResources().getString(R.string.respuesta_equivocada));
            answerImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_wrong_answer));
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(onResultClosedListener != null) {
            onResultClosedListener.onResultClosed();
        }
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        /*
        if(onResultClosedListener != null) {
            onResultClosedListener.onResultClosed();
        }
        */
    }
}
