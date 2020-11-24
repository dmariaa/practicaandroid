package es.dmariaa.practica1.data.model;

import com.google.gson.annotations.SerializedName;

public enum QuestionType {
    @SerializedName("choice") CHOICE,
    @SerializedName("multichoice") MULTICHOICE,
    @SerializedName("truefalse") TRUEFALSE,
    @SerializedName("value") VALUE;


    public static QuestionType fromString(String strQuestionType) {
        switch(strQuestionType) {
            case "choice": return CHOICE;
            case "multichoice": return MULTICHOICE;
            case "truefalse": return TRUEFALSE;
            case "value": return VALUE;
        }
        return null;
    }
}
