package android.bemodel.com.bemodel.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by Administrator on 2017.07.26.
 */

public class UserInfo extends BmobUser {

    private String nick;                //昵称
    private Boolean sex;                //性别
    private String avatar;              //用户头像地址
//    private String profileImageUrl;     //用户头像地址 中图
//    private String avatarLarge;         //用户头像地址 大图
//    private String avatarHd;            //用户头像地址 高清
    private BmobGeoPoint geo;           //地理信息
    private String address;         //地址信息

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public BmobGeoPoint getGeo() {
        return geo;
    }

    public void setGeo(BmobGeoPoint geo) {
        this.geo = geo;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

//    public String getProfileImageUrl() {
//        return profileImageUrl;
//    }
//
//    public void setProfileImageUrl(String profileImageUrl) {
//        this.profileImageUrl = profileImageUrl;
//    }
//
//    public String getAvatarLarge() {
//        return avatarLarge;
//    }
//
//    public void setAvatarLarge(String avatarLarge) {
//        this.avatarLarge = avatarLarge;
//    }
//
//    public String getAvatarHd() {
//        return avatarHd;
//    }
//
//    public void setAvatarHd(String avatar_hd) {
//        this.avatarHd = avatar_hd;
//    }
}
