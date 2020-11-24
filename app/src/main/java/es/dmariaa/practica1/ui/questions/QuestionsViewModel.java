package es.dmariaa.practica1.ui.questions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.dmariaa.practica1.data.QuestionsRepository;
import es.dmariaa.practica1.data.UsersProfileRepository;
import es.dmariaa.practica1.data.model.Question;
import es.dmariaa.practica1.data.model.UserProfile;

public class QuestionsViewModel extends ViewModel {
    QuestionsRepository repository;
    UsersProfileRepository usersRepository;

    public QuestionsViewModel(QuestionsRepository repository, UsersProfileRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.repository = repository;
        userProfileLiveData = new MutableLiveData<UserProfile>();
    }

    public LiveData<List<Question>> getQuestions() {
        return repository.getQuestions();
    }

    private MutableLiveData<UserProfile> userProfileLiveData;

    public MutableLiveData<UserProfile> getUserProfile() {
        UserProfile userProfile;

        if(currentUser != 0) {
            userProfile = usersRepository.getUserProfile(currentUser);
        } else {
            userProfile = new UserProfile();
        }

        userProfileLiveData.postValue(userProfile);
        return userProfileLiveData;
    }

    public void saveUser(UserProfile userProfile) {
        this.usersRepository.saveUserProfile(userProfile);
    }

    int currentUser = 0;

    public int getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(int currentUser) {
        this.currentUser = currentUser;
    }
}
