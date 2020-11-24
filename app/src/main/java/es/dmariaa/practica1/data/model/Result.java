package es.dmariaa.practica1.data.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Result implements Serializable {
    private int id;
    private Date startTime;
    private Date endTime;
    private int usersProfilesId;
    private List<ResultQuestions> questions;

    public int totalAnswers;
    public int rightAnswers;
    public int wrongAnsers;

    public Result(int id, Date startTime, Date endTime, int usersProfilesId) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.usersProfilesId = usersProfilesId;
        this.questions = new ArrayList<ResultQuestions>();
    }

    public void addQuestion(ResultQuestions question) {
        questions.add(question);
    }

    public ResultQuestions getQuestion(int id) {
        return questions.get(id);
    }

    public List<ResultQuestions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ResultQuestions> questions) {
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getUsersProfilesId() {
        return usersProfilesId;
    }

    public void setUsersProfilesId(int usersProfilesId) {
        this.usersProfilesId = usersProfilesId;
    }
}
