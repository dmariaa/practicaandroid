package es.dmariaa.practica1.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Result implements Serializable {
    String user;
    long birthdate;
    long startdate;
    long enddate;
    List<QuestionResult> results;

    public int getTotalAnswers() {
        return results.size();
    }

    public int getRightAnswers() {
        int right = 0;

        for(int i=0; i < results.size(); i++) {
            right += results.get(i).getValue();
        }

        return right;
    }

    public float getResultsPct() {
        return getRightAnswers()/getTotalAnswers();
    }

    public void putQuestionResult(QuestionResult questionResult) {
        if(results == null) {
            results = new ArrayList<QuestionResult>();
        }
        results.add(questionResult);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
    }

    public void setBirthdate(String birthdate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = simpleDateFormat.parse(birthdate);
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            this.birthdate = calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public long getStartdate() {
        return startdate;
    }

    public void setStartdate(long startdate) {
        this.startdate = startdate;
    }

    public long getEnddate() {
        return enddate;
    }

    public void setEnddate(long enddate) {
        this.enddate = enddate;
    }

    public List<QuestionResult> getResults() {
        return results;
    }

    public void setResults(List<QuestionResult> results) {
        this.results = results;
    }
}
