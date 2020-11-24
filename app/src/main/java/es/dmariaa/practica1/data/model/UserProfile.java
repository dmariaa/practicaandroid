package es.dmariaa.practica1.data.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.core.graphics.ColorUtils;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private int id;
    private String userId;
    private String displayName;
    private Date birthDate;
    private String photo;

    private List<Result> results;

    public UserProfile() {
        this.id = 0;
        this.userId = null;
        this.displayName = null;
        this.birthDate = null;
        this.photo = null;
        results = new ArrayList<Result>();
    }

    public UserProfile(int id, String userId, String displayName, Date birthDate, String photo) {
        this.id = id;
        this.userId = userId;
        this.displayName = displayName;
        this.birthDate = birthDate;
        this.photo = photo;
        results = new ArrayList<Result>();
    }

    public List<Result> getResults() {
        return results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getInitials() {
        if(displayName==null) return "";

        String[] words = this.displayName.split(" ");
        if(words.length > 1) {
            return words[0].charAt(0) + "" + words[1].charAt(0);
        } else {
            return words[0].substring(0, 1);
        }
    }

    public String getBirthDateFormatted() {
        if(birthDate == null) return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(getBirthDate());
    }

    public void setBirthDateString(String birthDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = new Date(simpleDateFormat.parse(birthDate).getTime());
            setBirthDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getPhotoAsBitmap(Context context) {
        if(photo==null) return null;

        Bitmap avatarBitmap = null;
        try {
            avatarBitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(Uri.parse(photo)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return avatarBitmap;
    }

    /**
     * Inspired from:
     * https://medium.com/@pppped/compute-an-arbitrary-color-for-user-avatar-starting-from-his-username-with-javascript-cd0675943b66
     * @return
     */
    public int getProfileColor() {
        if(displayName==null) return 0;

        int hashCode = getDisplayName().hashCode();
        int color =  ColorUtils.HSLToColor(new float[] { hashCode % 360, 0.3f, 0.8f });
        return color;
    }
}
