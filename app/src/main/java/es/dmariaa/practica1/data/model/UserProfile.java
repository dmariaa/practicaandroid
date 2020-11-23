package es.dmariaa.practica1.data.model;

import androidx.core.graphics.ColorUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private int id;
    private String userId;
    private String displayName;
    private Date birthDate;
    private String photo;

    private List<Result> results;

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
        String[] words = this.displayName.split(" ");
        if(words.length > 1) {
            return words[0].charAt(0) + "" + words[1].charAt(0);
        } else {
            return words[0].substring(0, 1);
        }
    }

    /**
     * Inspired from:
     * https://medium.com/@pppped/compute-an-arbitrary-color-for-user-avatar-starting-from-his-username-with-javascript-cd0675943b66
     * @return
     */
    public int getProfileColor() {
        int hashCode = getDisplayName().hashCode();
        int color =  ColorUtils.HSLToColor(new float[] { hashCode % 360, 0.3f, 0.8f });
        return color;
    }
}
