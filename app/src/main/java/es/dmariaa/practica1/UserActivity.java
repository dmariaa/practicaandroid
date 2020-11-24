package es.dmariaa.practica1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmariaa.practica1.data.model.UserProfile;
import es.dmariaa.practica1.ui.questions.TriviaActivity;
import es.dmariaa.practica1.ui.users.UserProfileDetailFragment;
import es.dmariaa.practica1.ui.users.UserProfileListFragment;

public class UserActivity extends AppCompatActivity {
    int PERMISSION_REQUEST_CODE_READ_EXTERNAL_STORAGE = 100;
    final int REQUEST_IMAGE_CAPTURE = 1;

    Uri photoURI;
    String currentPhotoPath;

    UserProfileListFragment userListFragment;
    UserProfileDetailFragment detailFragment;

    public interface PhotoListener {
        public void OnPhotoCaptured(Uri photoPaht);
    }

    private PhotoListener photoListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        requestPermission();

        this.setTitle(getString(R.string.userdata_title));
        this.userListFragment = new UserProfileListFragment();
        this.detailFragment = new UserProfileDetailFragment();

        showListFragment();
    }

    public void showListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(detailFragment);
        fragmentTransaction.add(R.id.fragments_frame, this.userListFragment);
        fragmentTransaction.commit();
    }

    public void showDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(userListFragment).addToBackStack("lista");
        fragmentTransaction.add(R.id.fragments_frame, detailFragment);
        fragmentTransaction.commit();
    }

    public void playGame(UserProfile user) {
        Intent intent = new Intent(this, TriviaActivity.class);
        intent.putExtra("USERID", user.getUserId());
        intent.putExtra("BIRTHDATE", user.getBirthDate());
        startActivity(intent);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        storageDir.mkdirs();

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void openCameraAndTakePicture(PhotoListener photoListener) {
        this.photoListener = photoListener;

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "es.dmariaa.practica1.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (this.photoListener != null) {
                photoListener.OnPhotoCaptured(photoURI);
            }
        }
    }
}