package es.dmariaa.practica1.data.model;

import java.util.ArrayList;
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

    public Question(int id, QuestionType type, String image, int minimumAge, String description, String feedback, String video) {
        this.id = id;
        this.type = type;
        this.image = image;
        this.minimumAge = minimumAge;
        this.description = description;
        this.feedback = feedback;
        this.video = video;
        this.answers = new ArrayList<Answer>();
    }

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

    public void setId(int id) {
        this.id = id;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }
}

