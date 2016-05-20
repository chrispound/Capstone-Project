package io.poundcode.gitdo.data.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class RepositoryDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gitdo.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TEXT_NOT_NULL = " TEXT NOT NULL,";

    public RepositoryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + RepositoryProvider.Tables.REPOSITORIES + " ("
            + RepositoryContract.RepositoryColumns.REPO_ID + " TEXT NOT NULL PRIMARY KEY, "
            + RepositoryContract.RepositoryColumns.DESCRIPTION + " TEXT,"
            + RepositoryContract.RepositoryColumns.NAME + TEXT_NOT_NULL
            + RepositoryContract.RepositoryColumns.PULL_REQUEST_COUNT + " TEXT,"
            + RepositoryContract.RepositoryColumns.ISSUE_COUNT + " INTEGER,"
            + RepositoryContract.RepositoryColumns.USER + " TEXT"
            + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + RepositoryProvider.Tables.REPOSITORIES);
        onCreate(db);
    }

    public Cursor getRepositories(String id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqliteQueryBuilder = new SQLiteQueryBuilder();
        sqliteQueryBuilder.setTables(RepositoryProvider.Tables.REPOSITORIES);

        Cursor cursor = sqliteQueryBuilder.query(getReadableDatabase(),
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder);
        return cursor;
    }
}
