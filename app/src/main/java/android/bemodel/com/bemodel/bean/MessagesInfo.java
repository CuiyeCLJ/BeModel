package android.bemodel.com.bemodel.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017.07.24.
 */

public class MessagesInfo extends BmobObject {

    private String targetId;    //聊天对方Id

    private UserInfo targetUser;

    private String message;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public UserInfo getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(UserInfo targetUser) {
        this.targetUser = targetUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
