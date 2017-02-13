package in.seleption.Utility;

import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import in.seleption.model.Menu;
import in.seleption.model.Stall;


/**
 * Created by Lokesh Tiwari on 15/06/2015.
 */
public class JsonHelper {

    public static String ConvertToJson(Object object) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(object);
    }

    public static ArrayList<Menu> ConvertToMenuObject(String json) {

        Type listType = new TypeToken<ArrayList<Menu>>() {
        }.getType();
        return new Gson().fromJson(json, listType);
    }

//    public static List<Job> ConvertToJobListObject(String json) {
//        Type type = new TypeToken<List<Job>>() {
//        }.getType();
//        return new Gson().fromJson(json, type);
//    }

//    public static List<ReceiveEmployee> ConvertToReceiveEmployeeListObject(String json) {
//        Type type = new TypeToken<List<ReceiveEmployee>>() {
//        }.getType();
//        return new Gson().fromJson(json, type);
//    }
}
