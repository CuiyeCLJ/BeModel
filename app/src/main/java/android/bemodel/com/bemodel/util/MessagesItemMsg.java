package android.bemodel.com.bemodel.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017.07.24.
 */

public class MessagesItemMsg {

    private int iconID;
    private String username;
    private String position;
    private String distance;

    public static List<MessagesItemMsg> messagesItemMsgList = new ArrayList<>();

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
