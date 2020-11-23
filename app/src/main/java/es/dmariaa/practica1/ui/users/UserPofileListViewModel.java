package es.dmariaa.practica1.ui.users;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.dmariaa.practica1.R;
import es.dmariaa.practica1.data.UsersProfileRepository;
import es.dmariaa.practica1.data.model.Result;
import es.dmariaa.practica1.data.model.UserProfile;

public class UserPofileListViewModel extends ViewModel {
    private UsersProfileRepository repository;

    private MutableLiveData<UserProfile> currentProfile;

    public UserPofileListViewModel(UsersProfileRepository repository) {
        this.repository = repository;
        this.currentProfile = new MutableLiveData<UserProfile>();
    }

    public LiveData<List<Result>> getUserResults(String userId) {
        return repository.getUserResults(userId);
    }

    public LiveData<List<UserProfile>> getUsersProfiles() {
        return repository.getUsersList();
    }

    public LiveData<UserProfile> getCurrentProfile() { return currentProfile; }

    public void setCurrentProfile(UserProfile currentProfile) {
        this.currentProfile.setValue(currentProfile);
    }

    public void setCurrentProfile(String currentProfileId) {
        UserProfile profile = repository.getUserProfile(currentProfileId);
        this.currentProfile.setValue(profile);
    }
}
