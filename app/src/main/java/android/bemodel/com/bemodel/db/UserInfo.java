package android.bemodel.com.bemodel.db;

/**
 * Created by Administrator on 2017.07.26.
 */

public class UserInfo {

    int id;
    String userName;
    String location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
