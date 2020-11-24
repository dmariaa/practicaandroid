package es.dmariaa.practica1.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import es.dmariaa.practica1.data.model.UserProfile;

public class DbManager {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    private DbManager(Context context) {
        this.dbHelper = DbHelper.getInstance(context);
        this.db = dbHelper.getWritableDatabase();
    }

    private static DbManager instance;

    public static DbManager getInstance(Context context) {
        if(instance == null) {
            instance = new DbManager(context);
        }
        return instance;
    }

    public Cursor getAllUsers() {
        String[] columns = new String[] {
                DbContract.DbUserProfiles._ID,
                DbContract.DbUserProfiles.COLUMN_NAME_USER_ID,
                DbContract.DbUserProfiles.COLUMN_NAME_NAME,
                DbContract.DbUserProfiles.COLUMN_NAME_PHOTO,
                DbContract.DbUserProfiles.COLUMN_NAME_BIRTH_DATE
        };

        return db.query(DbContract.DbUserProfiles.TABLE_NAME, columns, null,
                null, null, null, null);
    }

    public Cursor getUserByUserId(String userId) {
        String[] columns = new String[] {
                DbContract.DbUserProfiles._ID,
                DbContract.DbUserProfiles.COLUMN_NAME_USER_ID,
                DbContract.DbUserProfiles.COLUMN_NAME_NAME,
                DbContract.DbUserProfiles.COLUMN_NAME_PHOTO,
                DbContract.DbUserProfiles.COLUMN_NAME_BIRTH_DATE
        };

        String where = DbContract.DbUserProfiles.COLUMN_NAME_USER_ID + "=?";

        String[] whereValues = new String[] {
                userId
        };

        return db.query(DbContract.DbUserProfiles.TABLE_NAME, columns, where, whereValues,
                null, null, null);
    }

    public Cursor getUserResults(String userid) {
        String query = "select results._id,\n" +
                "       results.start_time,\n" +
                "       results.end_time,\n" +
                "       results.users_profiles_id,\n" +
                "       results_questions._id,\n" +
                "       results_questions.results_id,\n" +
                "       results_questions.question_id,\n" +
                "       results_questions.answer,\n" +
                "       results_questions.value,\n" +
                "       results_questions.time\n" +
                "from users_profiles\n" +
                "left join results ON users_profiles._id = results.users_profiles_id\n" +
                "left join results_questions ON results._id = results_questions.results_id\n" +
                "WHERE users_profiles.user_id = ? \n" +
                "ORDER BY users_profiles._id, results._id, results_questions._id";

        return db.rawQuery(query, new String[] {
           userid
        });
    }

    public void saveUser(UserProfile userProfile) {
        if(userProfile.getId()==0) {
            this.db.insert(DbContract.DbUserProfiles.TABLE_NAME, null,
                generateUserProfileContentValues(userProfile));
        } else {
            this.db.update(DbContract.DbUserProfiles.TABLE_NAME,
                generateUserProfileContentValues(userProfile), "_id=?",
                new String[] { String.valueOf(userProfile.getId()) });

        }
    }

    private ContentValues generateUserProfileContentValues(UserProfile userProfile) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.DbUserProfiles.COLUMN_NAME_USER_ID, userProfile.getUserId());
        contentValues.put(DbContract.DbUserProfiles.COLUMN_NAME_NAME, userProfile.getDisplayName());
        contentValues.put(DbContract.DbUserProfiles.COLUMN_NAME_BIRTH_DATE, userProfile.getBirthDate().getTime());
        contentValues.put(DbContract.DbUserProfiles.COLUMN_NAME_PHOTO, userProfile.getPhoto());
        return contentValues;
    }

    public Cursor getAllQuestions() {
        String query = "select questions._id as questions__id,\n" +
                "  questions.description as questions_description,\n" +
                "  questions.feedback as questions_feedback,\n" +
                "  questions.image as questions_image,\n" +
                "  questions.minimum_age as questions_minimum_age,\n" +
                "  questions.type as questions_type,\n" +
                "  questions.video as questions_video,\n" +
                "  answers._id as answers__id,\n" +
                "  answers.description as answers_description,\n" +
                "  answers.question_id as answers_question_id,\n" +
                "  answers.step as answers_step,\n" +
                "  answers.value as answers_value,\n" +
                "  answers.valueformat as answers_valueformat,\n" +
                "  answers.valuemax as answers_valuemax,\n" +
                "  answers.valuemin as answers_valuemin\n" +
                "from questions\n" +
                "left join answers on questions._id=answers.question_id;\n";

        return db.rawQuery(query, null);
    }
}
