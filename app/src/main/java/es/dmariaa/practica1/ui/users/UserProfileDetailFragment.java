package es.dmariaa.practica1.ui.users;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmariaa.practica1.R;
import es.dmariaa.practica1.UserActivity;
import es.dmariaa.practica1.data.model.Result;
import es.dmariaa.practica1.data.model.UserProfile;

public class UserProfileDetailFragment extends Fragment {
    UserPofileListViewModel viewModel;

    TextView placeHolder;
    CircleImageView avatar;
    TextView displayName;
    TextView lastGameStart;
    TextView lastGameEnd;
    TextView result;
    TextView score;
    Button playButton;
    MaterialCardView lastGameCard;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
        setExitTransition(inflater.inflateTransition(R.transition.slide_left));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        UserProfileListViewModelFactory factory = new UserProfileListViewModelFactory(getContext());
        viewModel = new ViewModelProvider(requireActivity(), factory)
                .get(UserPofileListViewModel.class);

        viewModel.getCurrentProfile().observe(getViewLifecycleOwner(),this.currentProfileObserver);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        placeHolder = view.findViewById(R.id.placeholder);
        avatar = view.findViewById(R.id.avatar);
        displayName = view.findViewById(R.id.displayname);

        lastGameStart = view.findViewById(R.id.last_game_start);
        lastGameEnd = view.findViewById(R.id.last_game_end);
        result = view.findViewById(R.id.result);
        score = view.findViewById(R.id.score);

        playButton = view.findViewById(R.id.play);

        lastGameCard = view.findViewById(R.id.last_game_card);

        playButton.setOnClickListener(playOnClickListener);
    }

    View.OnClickListener playOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            UserActivity activity = (UserActivity) getActivity();
            activity.playGame(viewModel.getCurrentProfile().getValue());
        }
    };


    private Drawable getAvatarBackground(UserProfile profile) {
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.big_circle);
        drawable.setColorFilter(profile.getProfileColor(), PorterDuff.Mode.MULTIPLY);
        return drawable;
    }

    /**
     * Inspired from:
     * https://android.jlelse.eu/avatarview-custom-implementation-of-imageview-4bcf0714d09d
     * @param avatar
     * @return
     */
    private Bitmap getAvatarBitmap(String avatar) {
        try {
            String imageFile = String.format("question-images/%s", avatar);
            InputStream questionImage = getContext().getResources().getAssets().open(imageFile);
            Bitmap questionImageBitmap = BitmapFactory.decodeStream(questionImage);
            return questionImageBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void setProfile(UserProfile userProfile) {
        displayName.setText(userProfile.getDisplayName());

        Bitmap avatarBitmap = this.getAvatarBitmap(userProfile.getPhoto());

        if(avatarBitmap != null) {
            avatar.setImageBitmap(avatarBitmap);
            avatar.setVisibility(View.VISIBLE);
            placeHolder.setVisibility(View.INVISIBLE);
        } else {
            placeHolder.setText(userProfile.getInitials());
            placeHolder.setBackground(getAvatarBackground(userProfile));
            placeHolder.setVisibility(View.VISIBLE);
            avatar.setVisibility(View.INVISIBLE);
        }

        if(userProfile.getResults().size() > 0) {
            Result lastGame = userProfile.getResults().get(userProfile.getResults().size() - 1);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String dateFormatted = simpleDateFormat.format(lastGame.getStartTime());
            lastGameStart.setText(dateFormatted);

            dateFormatted = simpleDateFormat.format(lastGame.getEndTime());
            lastGameStart.setText(dateFormatted);

            result.setText("0/0");
            score.setText("0%");

            lastGameCard.setVisibility(View.VISIBLE);
        } else {
            lastGameCard.setVisibility(View.GONE);
        }
    }

    Observer<UserProfile> currentProfileObserver = new Observer<UserProfile>() {
        @Override
        public void onChanged(UserProfile userProfile) {
            setProfile(userProfile);
        }
    };
}