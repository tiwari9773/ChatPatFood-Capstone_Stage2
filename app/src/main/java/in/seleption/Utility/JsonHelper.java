package in.seleption.Utility;

import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import in.seleption.model.Stall;


/**
 * Created by Lokesh Tiwari on 15/06/2015.
 */
public class JsonHelper {

    public static String ConvertToJson(Object object) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(object);
    }

    public static Stall ConvertToEmployeeObject(String json) {
        return new Gson().fromJson(json, Stall.class);
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
