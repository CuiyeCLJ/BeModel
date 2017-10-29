package android.bemodel.com.bemodel.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Administrator on 2017.10.12.
 */

public class ChatUser extends BmobUser {

    private static final long serialVersionUID = 1L;

    private String nick;    //昵称
    private String avatar;  //头像信息
    private BmobRelation contacts;  //好友联系人
    private String installId;       //设备Id
    private String deviceType;      //设备类型

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

    public BmobRelation getContacts() {
        return contacts;
    }

    public void setContacts(BmobRelation contacts) {
        this.contacts = contacts;
    }

    public String getInstallId() {
        return installId;
    }

    public void setInstallId(String installId) {
        this.installId = installId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
