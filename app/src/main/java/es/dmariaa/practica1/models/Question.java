package es.dmariaa.practica1.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {
    public enum QuestionType {
        @SerializedName("choice") CHOICE,
        @SerializedName("multichoice") MULTICHOICE,
        @SerializedName("truefalse") TRUEFALSE,
        @SerializedName("value") VALUE
    }

    int id;
    QuestionType type;
    String image;
    int minimumAge;
    String description;
    String feedback;
    List<Answer> answers;

    public int getId() {
        return id;
    }

    public String getImage() { return image; }

    public QuestionType getType() {
        return type;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public String getDescription() {
        return description;
    }

    public String getFeedback() {
        return feedback;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}

