package io.poundcode.gitdo.data.repositories;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

public class RepositoryProvider extends ContentProvider {

    private RepositoryDatabase repositoryDatabase;
    private static final String TAG = "RepositoryContentProv";

    interface Tables {
        String REPOSITORIES = "repositories";
    }

    private static final UriMatcher uriMatcher;
    static final int REPOSITORIES = 1;
    static final int REPOSITORY_ID = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(RepositoryContract.AUTHORITY, "repositories", REPOSITORIES);
    }

    @Override
    public boolean onCreate() {
        Log.i(TAG, "onCreate: ");
        repositoryDatabase = new RepositoryDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String id = null;
        qb.setTables(Tables.REPOSITORIES);
        switch (uriMatcher.match(uri)) {
            case REPOSITORIES:
                break;
            case REPOSITORY_ID:
                id = uri.getPathSegments().get(1);
                break;
            default:
                Log.e(TAG, "query: " + uriMatcher.match(uri));
        }
        return repositoryDatabase.getRepositories(id, projection, selection, selectionArgs, sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case REPOSITORIES:
                return RepositoryContract.RepositoryColumns.CONTENT_TYPE;
            case REPOSITORY_ID:
                return RepositoryContract.RepositoryColumns.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = repositoryDatabase.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        switch (match) {
            case REPOSITORIES: {
                final long _id = db.insertOrThrow(Tables.REPOSITORIES, null, contentValues);
                getContext().getContentResolver().notifyChange(uri, null);
                return RepositoryContract.buildItemUri(_id);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        final SQLiteDatabase db = repositoryDatabase.getWritableDatabase();
        String id = null;
        if(uriMatcher.match(uri) == REPOSITORY_ID) {
            //Update is for one single image. Get the ID from the URI.
            id = uri.getPathSegments().get(1);
        }
        if(id == null) {
            return db.delete(Tables.REPOSITORIES, null , null);
        } else {
            return db.delete(Tables.REPOSITORIES, "_id=?", new String[]{id});
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        final SQLiteDatabase db = repositoryDatabase.getWritableDatabase();
        String id = null;
        if(uriMatcher.match(uri) == REPOSITORY_ID) {
            //Update is for one single image. Get the ID from the URI.
            id = uri.getPathSegments().get(1);
        }
        return db.update(Tables.REPOSITORIES, contentValues, id, id == null ? null : new String[]{id});
    }
}
