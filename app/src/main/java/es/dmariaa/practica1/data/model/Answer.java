package es.dmariaa.practica1.data.model;

public class Answer {
    int id;
    String description;
    int value;
    int valuemin;
    int valuemax;
    String valueformat;
    int step;
    int questionId;

    public Answer(int id, String description, int value, int valuemin, int valuemax, String valueformat, int step, int questionid) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.valuemin = valuemin;
        this.valuemax = valuemax;
        this.valueformat = valueformat;
        this.step = step;
        this.questionId = questionid;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public int getValuemin() {
        return valuemin;
    }

    public int getValuemax() {
        return valuemax;
    }

    public String getValueformat() {
        return valueformat;
    }

    public int getStep() {
        return step;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setValuemin(int valuemin) {
        this.valuemin = valuemin;
    }

    public void setValuemax(int valuemax) {
        this.valuemax = valuemax;
    }

    public void setValueformat(String valueformat) {
        this.valueformat = valueformat;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
