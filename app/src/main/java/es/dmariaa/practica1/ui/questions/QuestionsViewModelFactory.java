package es.dmariaa.practica1.ui.questions;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.dmariaa.practica1.data.QuestionsRepository;

public class QuestionsViewModelFactory implements ViewModelProvider.Factory {
    Context context;

    public QuestionsViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(QuestionsViewModel.class)) {
            return (T) new QuestionsViewModel(QuestionsRepository.getInstance(this.context));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
