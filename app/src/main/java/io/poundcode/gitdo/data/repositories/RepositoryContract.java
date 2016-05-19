package io.poundcode.gitdo.data.repositories;

import android.net.Uri;

public final class RepositoryContract {

    private RepositoryContract(){}

    public static final String AUTHORITY = "io.poundcode.gitdo.repositories";
    public static final Uri BASE_URI = Uri.parse("content://"+AUTHORITY+"/repositories");

    /** Matches: /items/ */
    public static Uri buildDirUri() {
        return BASE_URI.buildUpon().appendPath("repositories").build();
    }

    /** Matches: /items/[_id]/ */
    public static Uri buildItemUri(long _id) {
        return BASE_URI.buildUpon().appendPath("repositories").appendPath(Long.toString(_id)).build();
    }
    protected interface RepositoryColumns {

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.io.poundcode.gitdo.repositories";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.io.poundcode.gitdo.repositories";

        /** Type: INTEGER PRIMARY KEY AUTOINCREMENT */
        String _ID = "_id";
        String REPO_ID = "repo_id";
        String NAME = "name";
        String DESCRIPTION = "description";
        String ISSUE_COUNT = "issue_count";
        String PULL_REQUEST_COUNT = "pull_request_count";
    }




}
