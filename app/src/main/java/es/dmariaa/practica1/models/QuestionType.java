package es.dmariaa.practica1.models;

import com.google.gson.annotations.SerializedName;

public enum QuestionType {
    @SerializedName("choice") CHOICE,
    @SerializedName("multichoice") MULTICHOICE,
    @SerializedName("truefalse") TRUEFALSE,
    @SerializedName("value") VALUE
}
