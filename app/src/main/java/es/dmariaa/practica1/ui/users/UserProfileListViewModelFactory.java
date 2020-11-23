package es.dmariaa.practica1.ui.users;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.dmariaa.practica1.data.UsersProfileRepository;

public class UserProfileListViewModelFactory implements ViewModelProvider.Factory {
    public UserProfileListViewModelFactory(Context context) {
        this.context = context;
    }

    Context context;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(UserPofileListViewModel.class)) {
            return (T) new UserPofileListViewModel(UsersProfileRepository.getInstance(this.context));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
