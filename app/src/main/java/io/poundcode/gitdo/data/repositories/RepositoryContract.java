package io.poundcode.gitdo.data.repositories;

import android.content.ContentValues;
import android.net.Uri;

import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;

public final class RepositoryContract {

    private RepositoryContract() {
    }

    public static final String AUTHORITY = "io.poundcode.gitdo.repositories";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY + "/repositories");

    public static final int COL_NAME = 2;
    public static final int COL_REPO_ID = 1;
    public static final int COL_DESCRIPTION = 3;
    public static final int COL_ISSUE_COUNT = 4;
    public static final int COL_USER = 5;

    /**
     * Matches: /items/
     */
    public static Uri buildDirUri() {
        return BASE_URI.buildUpon().appendPath("repositories").build();
    }

    /**
     * Matches: /items/[_id]/
     */
    public static Uri buildItemUri(long _id) {
        return BASE_URI.buildUpon().appendPath("repositories").appendPath(Long.toString(_id)).build();
    }

    public static ContentValues getContentValueForRepository(GitHubRepository repository) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RepositoryColumns.REPO_ID, repository.getId());
        contentValues.put(RepositoryColumns.NAME, repository.getName());
        contentValues.put(RepositoryColumns.DESCRIPTION, repository.getDescription());
        contentValues.put(RepositoryColumns.ISSUE_COUNT, repository.getOpenIssuesCount());
        contentValues.put(RepositoryColumns.USER , repository.getOwner().getLogin());
        return contentValues;
    }

    public interface RepositoryColumns {

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.io.poundcode.gitdo.repositories";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.io.poundcode.gitdo.repositories";

        /**
         * Type: INTEGER PRIMARY KEY AUTOINCREMENT
         */
        String REPO_ID = "repo_id";
        String NAME = "name";
        String DESCRIPTION = "description";
        String ISSUE_COUNT = "issue_count";
        String PULL_REQUEST_COUNT = "pull_request_count";
        String USER = "user";
    }


}
