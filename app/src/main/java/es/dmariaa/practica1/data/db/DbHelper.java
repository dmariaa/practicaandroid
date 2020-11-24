package es.dmariaa.practica1.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DbHelper extends SQLiteOpenHelper {
    public static DbHelper instance;

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            DbHelper.copyLocalDatabase(context);
            instance = new DbHelper(context);
        }

        return instance;
    }

    private DbHelper(@Nullable Context context) {
        super(context, DbContract.DB_NAME, null, DbContract.DB_VERSION);
    }

    public static void copyLocalDatabase(Context context) {
        File file = context.getDatabasePath(DbContract.DB_NAME);

        if (!file.exists() || true) { // FORCE COPY FOR TESTING
            try {
                InputStream assetsFile = context.getAssets().open(DbContract.DB_NAME);
                OutputStream databaseFile = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int read = assetsFile.read(buffer);
                while (read != -1) {
                    databaseFile.write(buffer, 0, read);
                    read = assetsFile.read(buffer);
                }

                assetsFile.close();
                databaseFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
