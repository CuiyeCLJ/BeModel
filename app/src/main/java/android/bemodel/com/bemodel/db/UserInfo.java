package android.bemodel.com.bemodel.db;

/**
 * Created by Administrator on 2017.07.26.
 */

public class UserInfo {

    private int id;                 //用户ID
    private String screenName;      //用户昵称
    private String userName;        //用户名称
    private String profileImageUrl; //用户头像地址 中图
    private String avatarLarge;     //用户头像地址 大图
    private String avatar_hd;       //用户头像地址 高清

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getAvatarLarge() {
        return avatarLarge;
    }

    public void setAvatarLarge(String avatarLarge) {
        this.avatarLarge = avatarLarge;
    }

    public String getAvatar_hd() {
        return avatar_hd;
    }

    public void setAvatar_hd(String avatar_hd) {
        this.avatar_hd = avatar_hd;
    }
}
