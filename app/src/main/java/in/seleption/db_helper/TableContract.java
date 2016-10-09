package in.seleption.db_helper;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;

import in.seleption.chatpatfood.BuildConfig;

/**
 * Created by Lokesh on 27-11-2015.
 */
public final class TableContract {
    private final String TAG = TableContract.class.getName();

    static final String CONTENT_AUTHORITY = BuildConfig.CONTENT_AUTHORITY;
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    static final String PATH_REGISTER_STALL = "register_stall";

    public TableContract() {
    }

    public static abstract class RegisterStall implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REGISTER_STALL).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REGISTER_STALL;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REGISTER_STALL;

        public static String TABLE_NAME = PATH_REGISTER_STALL;
        public static String SERVER_ID = "server_id";
        public static String NAME = "name";
        public static String LATI = "lati";
        public static String LONGI = "longi";
        public static String CATEGORY_ID = "category_id";
        public static String CATEGORY_OTHER = "category_other";
        public static String START_TIME = "start_time";
        public static String END_TIME = "end_time";
        public static String PHONE_NO = "phone_no";
        public static String PHOTO_URL = "photo_url";
        public static String CLOSE_DAY = "close_day";
        public static String REGISTER_DATE = "register_date";
        public static String IS_SYNC = "is_sync";

        public static final String SQL_CREATE = T.CREATE_TABLE + TABLE_NAME
                + T.OPEN_BRACE
                + _ID + T.TYPE_INTEGER + T.PRIMARY_KEY + T.AUTO_INCREMENT + T.SEP_COMMA
                + SERVER_ID + T.TYPE_TEXT + T.SEP_COMMA
                + NAME + T.TYPE_TEXT + T.SEP_COMMA
                + LATI + T.TYPE_TEXT + T.SEP_COMMA
                + LONGI + T.TYPE_TEXT + T.SEP_COMMA
                + CATEGORY_ID + T.TYPE_TEXT + T.SEP_COMMA
                + CATEGORY_OTHER + T.TYPE_TEXT + T.SEP_COMMA
                + START_TIME + T.TYPE_TEXT + T.SEP_COMMA
                + END_TIME + T.TYPE_TEXT + T.SEP_COMMA
                + PHONE_NO + T.TYPE_TEXT + T.SEP_COMMA
                + PHOTO_URL + T.TYPE_TEXT + T.SEP_COMMA
                + CLOSE_DAY + T.TYPE_TEXT + T.SEP_COMMA
                + REGISTER_DATE + T.TYPE_TEXT + T.SEP_COMMA
                + IS_SYNC + T.TYPE_TEXT + T.SEP_COMMA
                + T.UNIQUE + T.OPEN_BRACE + _ID + T.CLOSE_BRACE + T.ON_CONFLICT_REPLACE
                + T.CLOSE_BRACE + T.SEMICOLON;

        public static final String SQL_DROP = T.DROP_TABLE + TABLE_NAME;

        public static Uri buildStallUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
