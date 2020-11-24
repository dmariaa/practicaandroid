package es.dmariaa.practica1.ui.questions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.dmariaa.practica1.data.QuestionsRepository;
import es.dmariaa.practica1.data.model.Question;

public class QuestionsViewModel extends ViewModel {
    QuestionsRepository repository;

    public QuestionsViewModel(QuestionsRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Question>> getQuestions() {
        return repository.getQuestions();
    }
}
