package es.dmariaa.practica1.ui.users;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    FloatingActionButton addProfilebutton;

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

        RecyclerView rv = view.findViewById(R.id.list);

        // Set the adapter
        if (rv instanceof RecyclerView) {
            Context context = getContext();
            recyclerView = (RecyclerView) rv;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addOnItemTouchListener(this.itemClickListener);

            UserProfileListViewModelFactory factory = new UserProfileListViewModelFactory(getContext());
            viewModel = new ViewModelProvider(requireActivity(), factory)
                    .get(UserPofileListViewModel.class);

            viewModel.getUsersProfiles().observe(getViewLifecycleOwner(), usersProfilesListObserver);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addProfilebutton = view.findViewById(R.id.add_profile_button);
        addProfilebutton.setOnClickListener(addProfileClickListener);
    }

    View.OnClickListener addProfileClickListener = (view) -> {
        openDetailFragment(-1);
    };

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
            if(position==-1) {
                viewModel.addNewProfile();
            } else {
                UserProfile userProfile = viewModel.getUsersProfiles().getValue().get(position);
                viewModel.saveCurrentProfile(userProfile);
            }

            ((UserActivity) context).showDetailFragment();
        }
    }
}