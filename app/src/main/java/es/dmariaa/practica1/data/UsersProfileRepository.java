package es.dmariaa.practica1.data;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import es.dmariaa.practica1.data.db.DbContract;
import es.dmariaa.practica1.data.db.DbManager;
import es.dmariaa.practica1.data.model.Result;
import es.dmariaa.practica1.data.model.ResultQuestions;
import es.dmariaa.practica1.data.model.UserProfile;

public class UsersProfileRepository {
    private static volatile UsersProfileRepository instance;

    private UsersProfileRepository(Context context) {
        dbManager = DbManager.getInstance(context);
    }

    public static UsersProfileRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UsersProfileRepository(context);
        }
        return instance;
    }

    DbManager dbManager;

    private MutableLiveData<List<UserProfile>> usersLiveData;

    public LiveData<List<UserProfile>> getUsersList() {
        if(usersLiveData ==null) {
            usersLiveData = new MutableLiveData<List<UserProfile>>();
            this.loadUsers();
        }
        return usersLiveData;
    }

    private void loadUsers() {
        Handler handler = new Handler();
        handler.post(() -> {
            List<UserProfile> profiles = new ArrayList<>();

            Cursor dbUsers = dbManager.getAllUsers();
            while(dbUsers.moveToNext()) {
                int id = dbUsers.getInt(dbUsers.getColumnIndex(DbContract.DbUserProfiles._ID));
                String userId = dbUsers.getString(dbUsers.getColumnIndex(DbContract.DbUserProfiles.COLUMN_NAME_USER_ID));
                String userName = dbUsers.getString(dbUsers.getColumnIndex(DbContract.DbUserProfiles.COLUMN_NAME_NAME));
                String userPhoto = dbUsers.getString(dbUsers.getColumnIndex(DbContract.DbUserProfiles.COLUMN_NAME_PHOTO));
                Long userBirthDate = dbUsers.getLong(dbUsers.getColumnIndex(DbContract.DbUserProfiles.COLUMN_NAME_BIRTH_DATE));
                UserProfile userProfile = new UserProfile(id, userId, userName, new Date(userBirthDate), userPhoto);
                profiles.add(userProfile);
            }
            dbUsers.close();

            usersLiveData.postValue(profiles);
        });
    }

    private MutableLiveData<List<Result>> userResultsLiveData;

    public LiveData<List<Result>> getUserResults(String usrId) {
        if(userResultsLiveData == null) {
            userResultsLiveData = new MutableLiveData<List<Result>>();
            this.loadResults(usrId);
        }

        return userResultsLiveData;
    }

    private void loadResults(String usrId) {
        Handler handler = new Handler();
        handler.post(() -> {
            List<Result> results = new ArrayList<>();

            Cursor cursor = dbManager.getUserResults(usrId);
            Result currentResult = null;

            while(cursor.moveToNext()) {
                int resultId = cursor.getInt(cursor.getColumnIndex(DbContract.DbResults._ID));
                long startTime = cursor.getLong(cursor.getColumnIndex(DbContract.DbResults.COLUMN_NAME_START_TIME));
                long endTime = cursor.getLong(cursor.getColumnIndex(DbContract.DbResults.COLUMN_NAME_END_TIME));
                int usersProfilesId = cursor.getInt(cursor.getColumnIndex(DbContract.DbResults.COLUMN_NAME_USERS_PROFILES_ID));

                if(currentResult==null || currentResult.getId() != resultId) {
                    if(currentResult != null) {
                        results.add(currentResult);
                    }

                    currentResult = new Result(resultId, new Date(startTime), new Date(endTime), usersProfilesId);
                }

                int resultQuestionId = cursor.getInt(cursor.getColumnIndex(DbContract.DbResultsQuestions._ID));
                int resultQuestionResultsId = cursor.getInt(cursor.getColumnIndex(DbContract.DbResultsQuestions.COLUMN_NAME_RESULTS_ID));
                int resultQuestionQuestionId = cursor.getInt(cursor.getColumnIndex(DbContract.DbResultsQuestions.COLUMN_NAME_QUESTION_ID));
                String resultQuestionAnswer = cursor.getString(cursor.getColumnIndex(DbContract.DbResultsQuestions.COLUMN_NAME_ANSWER));
                float resultQuestionValue = cursor.getFloat(cursor.getColumnIndex(DbContract.DbResultsQuestions.COLUMN_NAME_VALUE));
                long resultQuestionTime = cursor.getLong(cursor.getColumnIndex(DbContract.DbResultsQuestions.COLUMN_NAME_TIME));

                currentResult.addQuestion(new ResultQuestions(resultQuestionId, resultQuestionResultsId,
                        resultQuestionQuestionId, resultQuestionAnswer, resultQuestionValue,
                        new Date(resultQuestionTime)));
            }

            if(currentResult != null) {
                results.add(currentResult);
            }

            userResultsLiveData.postValue(results);
        });
    }

    public UserProfile getUserProfile(String usrId) {
        Cursor cursor = dbManager.getUserByUserId(usrId);
        UserProfile userProfile = null;

        if(cursor.getCount() > 0) {
            int id = cursor.getInt(cursor.getColumnIndex(DbContract.DbUserProfiles._ID));
            String userId = cursor.getString(cursor.getColumnIndex(DbContract.DbUserProfiles.COLUMN_NAME_USER_ID));
            String userName = cursor.getString(cursor.getColumnIndex(DbContract.DbUserProfiles.COLUMN_NAME_NAME));
            String userPhoto = cursor.getString(cursor.getColumnIndex(DbContract.DbUserProfiles.COLUMN_NAME_PHOTO));
            Long userBirthDate = cursor.getLong(cursor.getColumnIndex(DbContract.DbUserProfiles.COLUMN_NAME_BIRTH_DATE));
            userProfile = new UserProfile(id, userId, userName, new Date(userBirthDate), userPhoto);
        }

        return userProfile;
    }

    public UserProfile getUserProfile(int idx) {
        Cursor cursor = dbManager.getUserByUserId(idx);
        UserProfile userProfile = null;

        if(cursor.getCount() > 0) {
            int id = cursor.getInt(cursor.getColumnIndex(DbContract.DbUserProfiles._ID));
            String userId = cursor.getString(cursor.getColumnIndex(DbContract.DbUserProfiles.COLUMN_NAME_USER_ID));
            String userName = cursor.getString(cursor.getColumnIndex(DbContract.DbUserProfiles.COLUMN_NAME_NAME));
            String userPhoto = cursor.getString(cursor.getColumnIndex(DbContract.DbUserProfiles.COLUMN_NAME_PHOTO));
            Long userBirthDate = cursor.getLong(cursor.getColumnIndex(DbContract.DbUserProfiles.COLUMN_NAME_BIRTH_DATE));
            userProfile = new UserProfile(id, userId, userName, new Date(userBirthDate), userPhoto);
        }

        return userProfile;
    }

    public void saveUserProfile(UserProfile profile) {
        dbManager.saveUser(profile);

        usersLiveData = new MutableLiveData<List<UserProfile>>();
        this.loadUsers();
    }
}
