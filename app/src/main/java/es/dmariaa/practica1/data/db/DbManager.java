package es.dmariaa.practica1.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
}
