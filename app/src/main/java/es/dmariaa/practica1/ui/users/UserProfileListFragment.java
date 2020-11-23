package es.dmariaa.practica1.ui.users;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.dmariaa.practica1.R;
import es.dmariaa.practica1.UserActivity;
import es.dmariaa.practica1.data.model.UserProfile;

/**
 * A fragment representing a list of Items.
 */
public class UserProfileListFragment extends Fragment {
    UserPofileListViewModel viewModel;

    RecyclerView recyclerView;
    UserProfileRecyclerViewAdapter recyclerViewAdapter;
    UserProfileListFragment fragment;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.slide_left));
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragment = this;

        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addOnItemTouchListener(this.itemClickListener);

            UserProfileListViewModelFactory factory = new UserProfileListViewModelFactory(getContext());
            viewModel = new ViewModelProvider(requireActivity(), factory)
                    .get(UserPofileListViewModel.class);

            viewModel.getUsersProfiles().observe(getViewLifecycleOwner(), usersProfilesListObserver);
        }

        return view;
    }

    UserProfileRecyclerClickListener itemClickListener = new UserProfileRecyclerClickListener(getContext(),
        new UserProfileRecyclerClickListener.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                openDetailFragment(position);
            }
    });

    Observer<List<UserProfile>> usersProfilesListObserver = new Observer<List<UserProfile>>() {
        @Override
        public void onChanged(List<UserProfile> userProfiles) {
            recyclerViewAdapter = new UserProfileRecyclerViewAdapter(userProfiles, fragment);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    };

    public void openDetailFragment(int position) {
        Context context = getContext();
        if(context instanceof UserActivity) {
            UserProfile userProfile = viewModel.getUsersProfiles().getValue().get(position);
            viewModel.setCurrentProfile(userProfile);
            ((UserActivity) context).showDetailFragment();
        }
    }
}