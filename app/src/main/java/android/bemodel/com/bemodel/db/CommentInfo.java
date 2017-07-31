package android.bemodel.com.bemodel.db;

import java.lang.ref.SoftReference;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017.07.28.
 */

public class CommentInfo extends BmobObject {

    private Integer id;
    private String text;
    private UserInfo reviewer;
    private ModelCircleInfo status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public ModelCircleInfo getStatus() {
        return status;
    }

    public void setStatus(ModelCircleInfo status) {
        this.status = status;
    }
}
