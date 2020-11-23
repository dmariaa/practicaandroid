package es.dmariaa.practica1.data.db;

import android.provider.BaseColumns;

/**
 * Database settings
 * Inspired by
 */
public class DbContract {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "wanatrivia.db";

    public static final String SQL_CREATE_USER_PROFILES = String.format("CREATE TABLE %s (%s, %s, %s, %s, %s)",
        DbUserProfiles.TABLE_NAME,
        DbUserProfiles._ID + " INTEGER PRIMARY KEY",
        DbUserProfiles.COLUMN_NAME_USER_ID + " TEXT",
        DbUserProfiles.COLUMN_NAME_NAME + " TEXT",
        DbUserProfiles.COLUMN_NAME_PHOTO + " TEXT",
        DbUserProfiles.COLUMN_NAME_BIRTH_DATE + " LONG"
    );

    public static class DbUserProfiles implements BaseColumns {
        public static final String TABLE_NAME = "users_profiles";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_BIRTH_DATE = "birth_date";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PHOTO = "photo";
    }

    public static class DbResults implements BaseColumns {
        public static final String TABLE_NAME = "results";
        public static final String COLUMN_NAME_START_TIME = "start_time";
        public static final String COLUMN_NAME_END_TIME = "end_time";
        public static final String COLUMN_NAME_USERS_PROFILES_ID = "users_profiles_id";
    }

    public static class DbResultsQuestions implements BaseColumns {
        public static final String TABLE_NAME = "results_questions";
        public static final String COLUMN_NAME_RESULTS_ID = "results_id";
        public static final String COLUMN_NAME_QUESTION_ID = "question_id";
        public static final String COLUMN_NAME_ANSWER = "answer";
        public static final String COLUMN_NAME_VALUE = "value";
        public static final String COLUMN_NAME_TIME = "time";
    }
}
