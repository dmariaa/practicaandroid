package es.dmariaa.practica1.data.model;


import java.io.Serializable;
import java.sql.Date;

public class ResultQuestions implements Serializable {
    private int id;
    private int resultsId;
    private int questionId;

    private String answer;
    private float value;
    private Date time;

    public ResultQuestions(int id, int resultsId, int questionId, String answer, float value, Date time) {
        this.id = id;
        this.resultsId = resultsId;
        this.questionId = questionId;
        this.answer = answer;
        this.value = value;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResultsId() {
        return resultsId;
    }

    public void setResultsId(int resultsId) {
        this.resultsId = resultsId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
