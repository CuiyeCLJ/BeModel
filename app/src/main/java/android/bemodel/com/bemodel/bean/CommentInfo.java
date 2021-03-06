package android.bemodel.com.bemodel.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017.07.28.
 */

public class CommentInfo extends BmobObject {

    private String text;        //评论内容
    private UserInfo reviewer;  //评论者
    private ModelCircleInfo modelCircle;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserInfo getReviewer() {
        return reviewer;
    }

    public void setReviewer(UserInfo reviewer) {
        this.reviewer = reviewer;
    }

    public ModelCircleInfo getModelCircle() {
        return modelCircle;
    }

    public void setModelCircle(ModelCircleInfo modelCircle) {
        this.modelCircle = modelCircle;
    }
}
