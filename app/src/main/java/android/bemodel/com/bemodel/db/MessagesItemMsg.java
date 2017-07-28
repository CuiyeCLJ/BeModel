package android.bemodel.com.bemodel.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017.07.24.
 */

public class MessagesItemMsg {

    private int iconID;
    private UserInfo user;
    private String position;
    private String distance;
    private String time;

    public static List<MessagesItemMsg> messagesItemMsgList = new ArrayList<>();

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
