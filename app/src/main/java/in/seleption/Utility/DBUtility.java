package in.seleption.Utility;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import in.seleption.db_helper.TableContract;
import in.seleption.model.Stall;

/**
 * Created by Lokesh on 12-10-2016.
 */

public class DBUtility {

    private static final String TAG = DBUtility.class.getName();

    public static void insertSingleStall(Context context, Stall stall) {
        if (context != null && stall != null) {
            ContentValues cv = new ContentValues();
            cv.put(TableContract.RegisterStall.NAME, stall.getStall_name());
            cv.put(TableContract.RegisterStall.PHONE_NO, stall.getMobile_no());
            cv.put(TableContract.RegisterStall.START_TIME, stall.getStart_time());
            cv.put(TableContract.RegisterStall.END_TIME, stall.getEnd_time());
            cv.put(TableContract.RegisterStall.OTHER, stall.getOthers());
            cv.put(TableContract.RegisterStall.MENU, JsonHelper.ConvertToJson(stall.getMenu()));
            cv.put(TableContract.RegisterStall.LATI, stall.getLatittude());
            cv.put(TableContract.RegisterStall.LONGI, stall.getLongitude());
            cv.put(TableContract.RegisterStall.PHOTO_URL, stall.getUrl());

            Uri uri = context.getContentResolver().insert(TableContract.RegisterStall.CONTENT_URI, cv);

            if (ContentUris.parseId(uri) > 0) {
                Log.i(TAG, "insertSingleStall: " + "Success");
            }
        }
    }
}
