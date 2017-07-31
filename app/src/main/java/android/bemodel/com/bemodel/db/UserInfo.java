package android.bemodel.com.bemodel.db;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017.07.26.
 */

public class UserInfo extends BmobUser {

    private Integer id;                 //用户ID
    private Boolean sex;                //性别
    private String profileImageUrl;     //用户头像地址 中图
    private String avatarLarge;         //用户头像地址 大图
    private String avatarHd;            //用户头像地址 高清

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAvatarHd() {
        return avatarHd;
    }

    public void setAvatarHd(String avatar_hd) {
        this.avatarHd = avatar_hd;
    }
}
