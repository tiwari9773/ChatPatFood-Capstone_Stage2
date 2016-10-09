package in.seleption.db_helper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Lokesh on 27-11-2015.
 */
public class TableProvider extends ContentProvider {

    /*Create Database*/
    private DBHelper mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    /*Key value for register_stall uri*/
    private static final int REGISTER_STALL = 1;
    private static final int REGISTER_STALL_ID = 2;

    /*First all uri for our application*/
    public static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TableContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, TableContract.PATH_REGISTER_STALL, REGISTER_STALL);
        uriMatcher.addURI(authority, TableContract.PATH_REGISTER_STALL + "/*", REGISTER_STALL_ID);
        return uriMatcher;
    }

    /*register_stall._id =?*/

    @Override
    public boolean onCreate() {
        mOpenHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor = null;

        switch (sUriMatcher.match(uri)) {
            case REGISTER_STALL:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        TableContract.RegisterStall.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case REGISTER_STALL_ID:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        TableContract.RegisterStall.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case REGISTER_STALL:
                return TableContract.RegisterStall.CONTENT_DIR_TYPE;

            case REGISTER_STALL_ID:
                return TableContract.RegisterStall.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case REGISTER_STALL:
                long _id = db.insert(TableContract.RegisterStall.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = TableContract.RegisterStall.buildStallUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted;

        switch (sUriMatcher.match(uri)) {
            case REGISTER_STALL:
                rowsDeleted = db.delete(
                        TableContract.RegisterStall.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsUpdated = 0;

        switch (sUriMatcher.match(uri)) {
            case REGISTER_STALL:
                rowsUpdated = db.update(TableContract.RegisterStall.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case REGISTER_STALL:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(TableContract.RegisterStall.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
