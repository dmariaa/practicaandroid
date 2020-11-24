package es.dmariaa.practica1.ui.users;

import android.app.DatePickerDialog.OnDateSetListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmariaa.practica1.R;
import es.dmariaa.practica1.UserActivity;
import es.dmariaa.practica1.data.model.Result;
import es.dmariaa.practica1.data.model.UserProfile;
import es.dmariaa.practica1.dialogs.DatePickerFragment;

public class UserProfileDetailFragment extends Fragment {
    UserPofileListViewModel viewModel;

    TextView placeHolder;
    CircleImageView avatar;

    TextInputLayout displayName;
    TextInputLayout birthDate;

    TextView lastGameStart;
    TextView lastGameEnd;
    TextView result;
    TextView score;
    Button playButton;
    MaterialCardView lastGameCard;
    String currentPhotoUri;

    FloatingActionButton editButton;
    FloatingActionButton capturePhoto;

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
        birthDate = view.findViewById(R.id.birthdate);
        lastGameStart = view.findViewById(R.id.last_game_start);
        lastGameEnd = view.findViewById(R.id.last_game_end);
        result = view.findViewById(R.id.result);
        score = view.findViewById(R.id.score);
        lastGameCard = view.findViewById(R.id.last_game_card);
        playButton = view.findViewById(R.id.play);
        editButton = view.findViewById(R.id.edit_profile_button);
        capturePhoto = view.findViewById(R.id.capture_photo);
    }

    OnClickListener playOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            UserActivity activity = (UserActivity) getActivity();
            activity.playGame(viewModel.getCurrentProfile().getValue());
        }
    };


    private Drawable getAvatarBackground(UserProfile profile) {
        int color;

        if(profile.getDisplayName()==null) {
            color = getResources().getColor(R.color.colorPrimary);
        } else {
            color = profile.getProfileColor();
        }

        Drawable drawable = getContext().getResources().getDrawable(R.drawable.big_circle);
        drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        return drawable;
    }

    private void setProfile(UserProfile userProfile) {
        displayName.getEditText().setText(userProfile.getDisplayName());
        birthDate.getEditText().setText(userProfile.getBirthDateFormatted());

        Bitmap avatarBitmap = userProfile.getPhotoAsBitmap(getContext());
        currentPhotoUri = userProfile.getPhoto();

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

            SimpleDateFormat gameDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String dateFormatted = gameDateFormat.format(lastGame.getStartTime());
            lastGameStart.setText(dateFormatted);

            dateFormatted = gameDateFormat.format(lastGame.getEndTime());
            lastGameStart.setText(dateFormatted);

            result.setText("0/0");
            score.setText("0%");

            lastGameCard.setVisibility(View.VISIBLE);
        } else {
            lastGameCard.setVisibility(View.GONE);
        }

        if(userProfile.getId() != 0) {
            setReadOnlyMode();
        } else {
            setEditMode();
        }
    }

    private void setReadOnlyMode() {
        displayName.setEnabled(false);
        birthDate.setEnabled(false);
        birthDate.getEditText().setOnClickListener(null);

        editButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_edit_24));
        editButton.setOnClickListener(view1 -> setEditMode());

        capturePhoto.setVisibility(View.GONE);
        capturePhoto.setOnClickListener(null);

        playButton.setVisibility(View.VISIBLE);
        playButton.setOnClickListener(playOnClickListener);
    }

    private void setEditMode() {
        displayName.setEnabled(true);
        birthDate.setEnabled(true);
        birthDate.getEditText().setOnClickListener(birthDateClickListener);

        editButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_save_24));
        editButton.setOnClickListener(editClickListener);

        capturePhoto.setVisibility(View.VISIBLE);
        capturePhoto.setOnClickListener(capturePhotoClickListener);

        playButton.setVisibility(View.GONE);
        playButton.setOnClickListener(null);
    }

    OnClickListener editClickListener = view -> {
        UserProfile userProfile = viewModel.getCurrentProfile().getValue();
        userProfile.setDisplayName(displayName.getEditText().getText().toString());
        userProfile.setBirthDateString(birthDate.getEditText().getText().toString());
        if(currentPhotoUri != null) {
            userProfile.setPhoto(currentPhotoUri);
        }
        viewModel.saveCurrentProfile(userProfile);
        setReadOnlyMode();
    };

    OnClickListener capturePhotoClickListener = view -> {
        UserActivity activity = (UserActivity) getActivity();
        activity.openCameraAndTakePicture(new UserActivity.PhotoListener() {
            @Override
            public void OnPhotoCaptured(Uri photoURI) {
                try {
                    Bitmap avatarBitmap = null;
                    avatarBitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(photoURI));
                    avatar.setImageBitmap(avatarBitmap);
                    avatar.setVisibility(View.VISIBLE);
                    placeHolder.setVisibility(View.INVISIBLE);
                    currentPhotoUri = photoURI.toString();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    };

    OnClickListener birthDateClickListener = view -> showDatePickerDialog();


    private void showDatePickerDialog() {
        DatePickerFragment datePicker = DatePickerFragment.newInstance(dateSetListener);
        datePicker.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    OnDateSetListener dateSetListener = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar calendar = new GregorianCalendar(year, month, day);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateFormatted = simpleDateFormat.format(calendar.getTime());
            birthDate.getEditText().setText(dateFormatted);
        }
    };

    Observer<UserProfile> currentProfileObserver = new Observer<UserProfile>() {
        @Override
        public void onChanged(UserProfile userProfile) {
            setProfile(userProfile);
        }
    };

}