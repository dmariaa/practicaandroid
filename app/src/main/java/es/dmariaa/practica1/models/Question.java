package es.dmariaa.practica1.models;

import java.util.List;

public class Question {
    int id;
    QuestionType type;
    String image;
    int minimumAge;
    String description;
    String feedback;
    List<Answer> answers;
    String video;

    public String getVideo() {
        return video;
    }

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

