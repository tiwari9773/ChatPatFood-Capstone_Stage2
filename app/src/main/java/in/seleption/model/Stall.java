package in.seleption.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by Lokesh on 16-02-2016.
 */
@IgnoreExtraProperties
public class Stall {

    String stall_name;
    String mobile_no;
    int start_time;
    int end_time;

    List<Menu> menu;
    /*Url of Image of his shop*/
    String url;

    double latittude;
    double longitude;
    String comment;

    /*If any*/
    String address;
    String others;

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getStall_name() {
        return stall_name;
    }

    public void setStall_name(String stall_name) {
        this.stall_name = stall_name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getLatittude() {
        return latittude;
    }

    public void setLatittude(double latittude) {
        this.latittude = latittude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
